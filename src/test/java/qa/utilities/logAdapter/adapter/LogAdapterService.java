// 

// 

package qa.utilities.logAdapter.adapter;

import org.slf4j.LoggerFactory;
import java.util.HashMap;
import qa.utilities.logAdapter.adapter.impl.PG2LogAdapterImpl;
import java.time.Duration;
import java.util.function.Predicate;
import qa.utilities.logAdapter.constants.Namespace;
import qa.utilities.logAdapter.constants.QueriedIndexes;
import qa.utilities.logAdapter.adapter.impl.AdapterConfig;
import org.slf4j.Logger;
import qa.utilities.logAdapter.dto.response.LogResultSummary;
import java.util.Map;

public final class LogAdapterService
{
    private static final Map<String, ILogAdapter<LogResultSummary>> LOG_ADAPTER_MAP;
    private static Logger logger;
    
    private LogAdapterService() {
    }
    
    public static LogResultSummary search_withAndCondition(final AdapterConfig adapterConfig, final QueriedIndexes queriedIndexes, final Namespace namespace, final String query, final String... queries) {
        return getInstance(adapterConfig).search_withAndCondition(queriedIndexes, namespace, query, queries);
    }
    
    public static LogResultSummary searchPolling_withAndCondition(final AdapterConfig adapterConfig, final QueriedIndexes queriedIndexes, final Namespace namespace, final Predicate<LogResultSummary> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME, final String query, final String... queries) {
        return getInstance(adapterConfig).searchPolling_withAndCondition(queriedIndexes, namespace, tPredicate, MAX_TIME, POLL_TIME, query, queries);
    }
    
    public static LogResultSummary search_withOrCondition(final AdapterConfig adapterConfig, final QueriedIndexes queriedIndexes, final Namespace namespace, final String query, final String... queries) {
        return getInstance(adapterConfig).search_withOrCondition(queriedIndexes, namespace, query, queries);
    }
    
    public static LogResultSummary searchPolling_withOrCondition(final AdapterConfig adapterConfig, final QueriedIndexes queriedIndexes, final Namespace namespace, final Predicate<LogResultSummary> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME, final String query, final String... queries) {
        return getInstance(adapterConfig).searchPolling_withOrCondition(queriedIndexes, namespace, tPredicate, MAX_TIME, POLL_TIME, query, queries);
    }
    
    public static LogResultSummary search(final AdapterConfig adapterConfig, final QueriedIndexes queriedIndexes, final Namespace namespace, final String query) {
        return getInstance(adapterConfig).search(queriedIndexes, namespace, query);
    }
    
    public static LogResultSummary searchPolling(final AdapterConfig adapterConfig, final QueriedIndexes queriedIndexes, final Namespace namespace, final Predicate<LogResultSummary> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME, final String query) {
        return getInstance(adapterConfig).searchPolling(queriedIndexes, namespace, tPredicate, MAX_TIME, POLL_TIME, query);
    }
    
    private static ILogAdapter<LogResultSummary> getInstance(final AdapterConfig adapterConfig) {
        final String key = getKey(adapterConfig);
        ILogAdapter<LogResultSummary> iLogAdapter = null;
        if (LogAdapterService.LOG_ADAPTER_MAP.containsKey(key)) {
            iLogAdapter = LogAdapterService.LOG_ADAPTER_MAP.get(key);
        }
        else {
            LogAdapterService.logger.info("generating adapter config: " + adapterConfig.toString());
            iLogAdapter = new PG2LogAdapterImpl(adapterConfig.getHost(), adapterConfig.getPort()).setFromPoint(adapterConfig.getFromPoint()).setRecordSize(adapterConfig.getRecordSize()).setTimestampFieldName(adapterConfig.getTimeStampFieldName());
            LogAdapterService.LOG_ADAPTER_MAP.put(key, iLogAdapter);
        }
        return iLogAdapter;
    }
    
    private static String getKey(final AdapterConfig adapterConfig) {
        return adapterConfig.getHost() + "_" + adapterConfig.getPort() + "_" + adapterConfig.getFromPoint() + "_" + adapterConfig.getRecordSize();
    }
    
    static {
        LOG_ADAPTER_MAP = new HashMap<String, ILogAdapter<LogResultSummary>>();
        LogAdapterService.logger = LoggerFactory.getLogger((Class)LogAdapterService.class);
    }
}
