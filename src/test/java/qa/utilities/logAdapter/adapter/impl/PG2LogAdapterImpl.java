// 

// 

package qa.utilities.logAdapter.adapter.impl;

import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;
import qa.utilities.logAdapter.constants.PPUtil;
import java.time.Duration;
import java.util.function.Predicate;
import qa.utilities.logAdapter.initializer.LogAdapterConfig;
import qa.utilities.logAdapter.constants.ImplType;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.mapping.FieldType;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import java.util.stream.Stream;
import java.util.Objects;
import java.util.Arrays;
import qa.utilities.logAdapter.constants.QueriedIndexes;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import qa.utilities.logAdapter.constants.Namespace;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import qa.utilities.logAdapter.dto.response.LogsResponseGroup;
import java.util.concurrent.ConcurrentHashMap;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import qa.utilities.logAdapter.initializer.EsConnectionInitializer;
import qa.utilities.logAdapter.exception.LogAdapterException;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import qa.utilities.logAdapter.dto.response.MessageWrapper;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.utilities.logAdapter.dto.response.LogResultSummary;
import qa.utilities.logAdapter.adapter.ILogAdapter;

public class PG2LogAdapterImpl implements ILogAdapter<LogResultSummary>
{
    private String host;
    private int port;
    private String timestampField;
    private int recordSize;
    private int fromPoint;
    private Logger logger;
    
    public PG2LogAdapterImpl setFromPoint(final int fromPoint) {
        this.fromPoint = fromPoint;
        return this;
    }
    
    public PG2LogAdapterImpl setRecordSize(final int size) {
        this.recordSize = size;
        return this;
    }
    
    public PG2LogAdapterImpl setTimestampFieldName(final String fieldName) {
        this.timestampField = fieldName;
        return this;
    }
    
    public PG2LogAdapterImpl(final String host, final int port) {
        this.timestampField = "@timestamp";
        this.recordSize = 100;
        this.fromPoint = 0;
        this.logger = LoggerFactory.getLogger((Class)PG2LogAdapterImpl.class);
        this.host = host;
        this.port = port;
    }

    private synchronized LogResultSummary execute(final SearchRequest request) {
        final EsConnectionInitializer connectionInitializer = this.checkAdapterStatus();
        try {
            final SearchResponse<MessageWrapper> searchResponse =
                    connectionInitializer.getElasticsearchClient().search(request, MessageWrapper.class);
            return this.mapResponse(searchResponse);
        } catch (final IOException e) {
            Assertions.fail("Error while executing request to ES: ", e);
        } finally {
            try {
                connectionInitializer.closeConnection();
            } catch (final LogAdapterException ignored) {
            }
        }
        return null;
    }

    
    private LogResultSummary mapResponse(final SearchResponse<MessageWrapper> searchResponse) {
        final LogResultSummary logResultSummary = new LogResultSummary();
        final Map<String, LogsResponseGroup> groupMap = new ConcurrentHashMap<String, LogsResponseGroup>();
        if (!searchResponse.hits().hits().isEmpty()) {
            this.logger.info("generating LogResultSummary for size: {}", (Object)searchResponse.hits().hits().size());
            searchResponse.hits().hits().forEach(i -> {
                final String index = i.index();
                final MessageWrapper message = (MessageWrapper)i.source();
                if (groupMap.containsKey(index)) {
                    groupMap.get(index).getMessageWrappers().add(message);
                }
                else {
                    this.logger.info("LogResultSummary found for index: {}", (Object)index);
                    final LogsResponseGroup g = new LogsResponseGroup();
                    final List<MessageWrapper> m = new ArrayList<MessageWrapper>();
                    m.add(message);
                    g.setMessageWrappers(m);
                    groupMap.put(index, g);
                }
                return;
            });
        }
        else {
            this.logger.info("LogResultSummary is empty");
        }
        logResultSummary.setLogsGroup(groupMap);
        return logResultSummary;
    }
    
    private SearchRequest.Builder prepareSearchRequestWithFilter(final SearchRequest.Builder searchRequestBuild, final Namespace namespace, final Query finalConditionalQuery) {
        searchRequestBuild.size(Integer.valueOf(this.recordSize)).from(Integer.valueOf(this.fromPoint)).sort(this.prepareSortOptions(), new SortOptions[0]).query(q -> q.bool(b -> b.filter(this.prepareNameSpaceRequest(namespace), new Query[] { this.prepareRangeRequest(), finalConditionalQuery })));
        return searchRequestBuild;
    }
    
    private SearchRequest.Builder prepareSearchRequestWithOutFilter(final SearchRequest.Builder searchRequestBuild, final Query finalConditionalQuery) {
        searchRequestBuild.size(Integer.valueOf(this.recordSize)).from(Integer.valueOf(this.fromPoint)).sort(this.prepareSortOptions(), new SortOptions[0]).query(q -> q.bool(b -> b.filter(this.prepareRangeRequest(), new Query[] { finalConditionalQuery })));
        return searchRequestBuild;
    }
    
    private SearchRequest prepare_SearchRequest(final QueriedIndexes queriedIndexes, final List<String> queries, final Namespace namespace, final QUERY_TYPE queryType) {
        Query conditionalQuery = null;
        switch (queryType) {
            case OR: {
                conditionalQuery = this.prepareBoolShould_orFilterQueries(queries);
                break;
            }
            case AND: {
                conditionalQuery = this.prepareBool_AndFilterQueries(queries);
                break;
            }
            case SINGLE: {
                conditionalQuery = this.prepareMultiMatchQuery(queries.get(0));
                break;
            }
        }
        final SearchRequest.Builder searchRequestBuilder = new SearchRequest.Builder();
        final String[] indices = queriedIndexes.getIndices();
        Objects.requireNonNull(searchRequestBuilder, "searchRequestBuilder cannot be null");

        searchRequestBuilder.index(Arrays.asList(indices));

        final Query finalConditionalQuery = conditionalQuery;

        if (!namespace.getNamespaceValue().isEmpty()) {
            return this.prepareSearchRequestWithFilter(searchRequestBuilder, namespace, finalConditionalQuery).build();
        }
        return this.prepareSearchRequestWithOutFilter(searchRequestBuilder, finalConditionalQuery).build();

    }
    
    private Query prepareBool_AndFilterQueries(final List<String> queries) {
        final BoolQuery.Builder builder = new BoolQuery.Builder();
        final List<Query> mmQuery = queries.stream().map(this::prepareMultiMatchQuery).collect(Collectors.toList());
        return (Query)new Query.Builder().bool(builder.filter((List)mmQuery).build()).build();
    }
    
    private Query prepareBoolShould_orFilterQueries(final List<String> queries) {
        final BoolQuery.Builder builder = new BoolQuery.Builder();
        final List<Query> mmQuery = queries.stream().map(this::prepareMultiMatchQuery).collect(Collectors.toList());
        return (Query)new Query.Builder().bool(builder.should((List)mmQuery).build()).build();
    }
    
    private Query prepareMultiMatchQuery(final String query) {
        return (Query)new Query.Builder().multiMatch(new MultiMatchQuery.Builder().type(query.contains(" ") ? TextQueryType.Phrase : TextQueryType.BestFields).query(query).lenient(Boolean.valueOf(true)).build()).build();
    }
    
    private SortOptions prepareSortOptions() {
        return (SortOptions)new SortOptions.Builder().field(new FieldSort.Builder().field(this.timestampField).unmappedType(FieldType.Boolean).order(SortOrder.Desc).build()).build();
    }
    
    private Query prepareRangeRequest() {
        return (Query)new Query.Builder().range(new RangeQuery.Builder().field(this.timestampField).gte(JsonData.of((Object)"now/d")).lte(JsonData.of((Object)"now/d")).format("strict_date_optional_time").build()).build();
    }
    
    private Query prepareNameSpaceRequest(final Namespace namespace) {
        return (Query)new Query.Builder().matchPhrase(new MatchPhraseQuery.Builder().field(namespace.getNamespaceKeyName()).query(namespace.getNamespaceValue()).build()).build();
    }
    
    private EsConnectionInitializer checkAdapterStatus() {
        final EsConnectionInitializer connectionInitializer = LogAdapterConfig.getAdapterInfo(this.host, this.port, ImplType.PG2_ES_LOGS);
        if (null == connectionInitializer) {
            Assertions.fail(String.format("connection not exist for %s and %s and %s", this.host, this.port, ImplType.PG2_ES_LOGS));
        }
        return connectionInitializer;
    }
    
    @Override
    public LogResultSummary search_withAndCondition(final QueriedIndexes queriedIndexes, final Namespace namespace, final String query, final String... queries) {
        final List<String> queryL = Arrays.stream(queries).collect(Collectors.toList());
        queryL.add(query);
        final SearchRequest searchRequest = this.prepare_SearchRequest(queriedIndexes, queryL, namespace, QUERY_TYPE.AND);
        return this.execute(searchRequest);
    }
    
    @Override
    public LogResultSummary searchPolling_withAndCondition(final QueriedIndexes queriedIndexes, final Namespace namespace, final Predicate<LogResultSummary> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME, final String query, final String... queries) {
        final PPUtil<LogResultSummary> ppUtil = new PPUtil<LogResultSummary>(() -> this.search_withAndCondition(queriedIndexes, namespace, query, queries), tPredicate, MAX_TIME, POLL_TIME);
        final LogResultSummary resultSummary = ppUtil.evaluate();
        return resultSummary;
    }
    
    @Override
    public LogResultSummary search_withOrCondition(final QueriedIndexes queriedIndexes, final Namespace namespace, final String query, final String... queries) {
        final List<String> queryL = Arrays.stream(queries).collect(Collectors.toList());
        queryL.add(query);
        final SearchRequest searchRequest = this.prepare_SearchRequest(queriedIndexes, queryL, namespace, QUERY_TYPE.OR);
        return this.execute(searchRequest);
    }
    
    @Override
    public LogResultSummary searchPolling_withOrCondition(final QueriedIndexes queriedIndexes, final Namespace namespace, final Predicate<LogResultSummary> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME, final String query, final String... queries) {
        final PPUtil<LogResultSummary> ppUtil = new PPUtil<LogResultSummary>(() -> this.search_withOrCondition(queriedIndexes, namespace, query, queries), tPredicate, MAX_TIME, POLL_TIME);
        final LogResultSummary resultSummary = ppUtil.evaluate();
        return resultSummary;
    }
    
    @Override
    public LogResultSummary search(final QueriedIndexes queriedIndexes, final Namespace namespace, final String query) {
        final List<String> queryL = new ArrayList<String>();
        queryL.add(query);
        final SearchRequest searchRequest = this.prepare_SearchRequest(queriedIndexes, queryL, namespace, QUERY_TYPE.SINGLE);
        return this.execute(searchRequest);
    }
    
    @Override
    public LogResultSummary searchPolling(final QueriedIndexes queriedIndexes, final Namespace namespace, final Predicate<LogResultSummary> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME, final String query) {
        final PPUtil<LogResultSummary> ppUtil = new PPUtil<LogResultSummary>(() -> this.search(queriedIndexes, namespace, query), tPredicate, MAX_TIME, POLL_TIME);
        final LogResultSummary resultSummary = ppUtil.evaluate();
        return resultSummary;
    }
    
    private enum QUERY_TYPE
    {
        AND, 
        OR, 
        SINGLE;
    }
}
