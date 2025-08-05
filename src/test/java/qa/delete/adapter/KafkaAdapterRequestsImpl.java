package qa.delete.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.filter.FilterContext;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.filter.log.RequestLoggingFilter;
import org.slf4j.LoggerFactory;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Comparator;
import org.assertj.core.api.Assertions;
import qa.generic.api.CustomHttpResponse;
import qa.generic.api.HttpExec;
import qa.generic.api.MethodType;
import qa.delete.response.GenerateTopicDataRequest;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.List;
import qa.delete.response.KafkaResponseBuilder;
import qa.generic.PollingPredicate;
import java.util.Objects;
import io.restassured.response.Response;
import java.util.concurrent.Callable;
import qa.delete.client.KafkaClientConfig;
import io.restassured.http.ContentType;
import io.restassured.filter.Filter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.RestAssured;
import qa.delete.client.KafkaClientUtility;
import java.time.Duration;
import org.slf4j.Logger;
import io.restassured.specification.RequestSpecification;
import io.restassured.config.RestAssuredConfig;
import qa.delete.response.KafkaResponse;

public class KafkaAdapterRequestsImpl implements KafkaAdapterRequests<KafkaResponse>
{
    private static KafkaAdapterRequestsImpl kafkaAdapterRequests;
    private RestAssuredConfig config;
    private RequestSpecification reqSpec;
    private final int LIMIT_SIZE = 50;
    private static Logger logger;
    private Duration MAX_TIME;
    private Duration POLL_TIME;
    
    private KafkaAdapterRequestsImpl() {
        this.config = null;
        this.MAX_TIME = ((KafkaClientUtility.getInstance().getMAX_TIME() == null) ? Duration.ofSeconds(15L) : KafkaClientUtility.getInstance().getMAX_TIME());
        this.POLL_TIME = ((KafkaClientUtility.getInstance().getPOLL_DELAY() == null) ? Duration.ofMillis(500L) : KafkaClientUtility.getInstance().getPOLL_DELAY());
        this.config = RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().setParam("http.connection.timeout", (Object)90000).setParam("http.socket.timeout", (Object)90000));
        this.reqSpec = new RequestSpecBuilder().setConfig(this.config).addFilter((Filter)new AcsReqFilter()).setContentType(ContentType.JSON).setBaseUri(KafkaClientConfig.QA_KAFKA_CLIENT_URL_PROP_VALUE).build();
    }
    
    public static KafkaAdapterRequestsImpl getInstance() {
        if (null == KafkaAdapterRequestsImpl.kafkaAdapterRequests) {
            KafkaAdapterRequestsImpl.kafkaAdapterRequests = new KafkaAdapterRequestsImpl();
        }
        return KafkaAdapterRequestsImpl.kafkaAdapterRequests;
    }
    
    @Override
    public KafkaResponse findBy(final long row_id) {
        KafkaAdapterRequestsImpl.logger.info(String.format("fetching record by rowId: %s", row_id));
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topic/row_id").queryParam("row_id", new Object[] { row_id }).get();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        KafkaAdapterRequestsImpl.logger.info(String.format("response from ACS: %s", response.getBody()));
        assert response.statusCode() != 503 : "response is not success";
        return (response.statusCode() == 204) ? KafkaResponse.builder().build() : KafkaResponseBuilder.map(response.getBody());
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topicName) {
        return this.findBy_size(topicName, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topicName, final int size) {
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topic_name").queryParam("topic_name", new Object[] { topicName }).queryParam("size", new Object[] { size }).get();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topicName, final String environment_name) {
        return this.findBy_env_size(topicName, environment_name, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topicName, final String environment_name, final int size) {
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topic_name/environment_name").queryParam("topic_name", new Object[] { topicName }).queryParam("environment_name", new Object[] { environment_name }).queryParam("size", new Object[] { size }).get();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topicName, final Predicate<List<KafkaResponse>> predicate) {
        return this.findBy(topicName, predicate, this.MAX_TIME, this.POLL_TIME);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topicName, final int size, final Predicate<List<KafkaResponse>> predicate) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topicName, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        return this.findBy_env(topicName, predicate, this.MAX_TIME, this.POLL_TIME, environment_name);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topicName, final Predicate<List<KafkaResponse>> predicate, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topicName, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable(using_impl.by_topicname, topicName, "", "", 0), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responseList = new ArrayList<KafkaResponse>();
        responseList = listPollingPredicate.evaluate();
        if (null == responseList) {
            return Collections.EMPTY_LIST;
        }
        return responseList;
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topicName, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topicName, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable_env(using_impl.by_topicname, topicName, "", "", 0, environment_name), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responseList = new ArrayList<KafkaResponse>();
        responseList = listPollingPredicate.evaluate();
        if (null == responseList) {
            return Collections.EMPTY_LIST;
        }
        return responseList;
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topicName, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String record_json_path, final String record_json_path_value) {
        return this.findBy_size(record_json_path, record_json_path_value, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String record_json_path, final String record_json_path_value, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path", record_json_path);
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("size", size);
        final Response response = (Response)RestAssured.given().spec(this.reqSpec).basePath("query/topics/by_record_path").body((Object)reqMap).post();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String record_json_path, final String record_json_path_value, final String environment_name) {
        return this.findBy_env_size(record_json_path, record_json_path_value, environment_name, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String record_json_path, final String record_json_path_value, final String environment_name, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path", record_json_path);
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("environment_name", environment_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/by_record_path").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate) {
        return this.findBy(record_json_path, record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        return this.findBy_env(record_json_path, record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME, environment_name);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable(using_impl.by_record_path, "", record_json_path, record_json_path_value, 0), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable_env(using_impl.by_record_path, "", record_json_path, record_json_path_value, 0, environment_name), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topic_name, final String record_json_path, final String record_json_path_value) {
        return this.findBy_size(topic_name, record_json_path, record_json_path_value, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topic_name, final String record_json_path, final String record_json_path_value, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path", record_json_path);
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("topic_name", topic_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topicName/recordPath").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topic_name, final String record_json_path, final String record_json_path_value, final String environment_name) {
        return this.findBy_env_size(topic_name, record_json_path, record_json_path_value, environment_name, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topic_name, final String record_json_path, final String record_json_path_value, final String environment_name, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path", record_json_path);
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("topic_name", topic_name);
        reqMap.put("environment_name", environment_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topicName/recordPath").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate) {
        return this.findBy(topic_name, record_json_path, record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        return this.findBy_env(topic_name, record_json_path, record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME, environment_name);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable(using_impl.by_topicName_recordPath, topic_name, record_json_path, record_json_path_value, 0), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable_env(using_impl.by_topicName_recordPath, topic_name, record_json_path, record_json_path_value, 0, environment_name), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topic_name, final int time_interval) {
        return this.findBy_size(topic_name, time_interval, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topic_name, final int time_interval, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("topic_name", topic_name);
        reqMap.put("time_interval", time_interval);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topicName/timeInterval").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topic_name, final int time_interval, final String environment_name) {
        return this.findBy_env_size(topic_name, time_interval, environment_name, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topic_name, final int time_interval, final String environment_name, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("topic_name", topic_name);
        reqMap.put("time_interval", time_interval);
        reqMap.put("environment_name", environment_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topicName/timeInterval").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate) {
        return this.findBy(topic_name, time_interval, predicate, this.MAX_TIME, this.POLL_TIME);
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        return this.findBy_env(topic_name, time_interval, predicate, this.MAX_TIME, this.POLL_TIME, environment_name);
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable(using_impl.by_topicName_timeInterval, topic_name, "", "", time_interval), predicate, MAX_TIME, this.POLL_TIME);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_size(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_env(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable_env(using_impl.by_topicName_timeInterval, topic_name, "", "", time_interval, environment_name), predicate, MAX_TIME, this.POLL_TIME);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_env_size(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains(final String record_json_path_value) {
        return this.findBy_topicValue_contains_size(record_json_path_value, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_size(final String record_json_path_value, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topic_value/contains").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env(final String record_json_path_value, final String environment_name) {
        return this.findBy_topicValue_contains_env_size(record_json_path_value, environment_name, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env_size(final String record_json_path_value, final String environment_name, final int size) {
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("environment_name", environment_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topic_value/contains").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate) {
        return this.findBy_topicValue_contains(record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_size(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        return this.findBy_topicValue_contains_env(record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME, environment_name);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env_size(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable(using_impl.by_topicValue_contains, "", "", record_json_path_value, 0), predicate, MAX_TIME, this.POLL_TIME);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_size(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable_env(using_impl.by_topicValue_contains, "", "", record_json_path_value, 0, environment_name), predicate, MAX_TIME, this.POLL_TIME);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env_size(final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains(final String topic_name, final String record_json_path_value) {
        return this.findBy_topicValue_contains_size(topic_name, record_json_path_value, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_size(final String topic_name, final String record_json_path_value, final int size) {
        assert null != topic_name && !topic_name.isEmpty() : "topic_name cannot be empty/null";
        assert null != record_json_path_value && !record_json_path_value.isEmpty() : "record_json_path_value cannot be empty/null";
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("topic_name", topic_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topic_namevalue/contains").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env(final String topic_name, final String record_json_path_value, final String environment_name) {
        return this.findBy_topicValue_contains_env_size(topic_name, record_json_path_value, environment_name, 50);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env_size(final String topic_name, final String record_json_path_value, final String environment_name, final int size) {
        assert null != topic_name && !topic_name.isEmpty() : "topic_name cannot be empty/null";
        assert null != record_json_path_value && !record_json_path_value.isEmpty() : "record_json_path_value cannot be empty/null";
        final Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("record_json_path_value", record_json_path_value);
        reqMap.put("topic_name", topic_name);
        reqMap.put("environment_name", environment_name);
        reqMap.put("size", size);
        final Callable<Response> responseCallable = new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return (Response)RestAssured.given().spec(KafkaAdapterRequestsImpl.this.reqSpec).basePath("query/topics/topic_namevalue/contains").body((Object)reqMap).post();
            }
        };
        final Response response = new PollingPredicate<Response>(responseCallable, Objects::nonNull, this.MAX_TIME, this.POLL_TIME).evaluate();
        return this.prepareResponse(response);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate) {
        return this.findBy_topicValue_contains(topic_name, record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_size(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        return this.findBy_topicValue_contains_env(topic_name, record_json_path_value, predicate, this.MAX_TIME, this.POLL_TIME, environment_name);
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env_size(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable(using_impl.by_topicNameValue_contains, topic_name, "", record_json_path_value, 0), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_size(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final int size) {
        return null;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name) {
        final PollingPredicate<List<KafkaResponse>> listPollingPredicate = new PollingPredicate<List<KafkaResponse>>(this.listCallable_env(using_impl.by_topicNameValue_contains, topic_name, "", record_json_path_value, 0, environment_name), predicate, MAX_TIME, POLL_DURATION);
        List<KafkaResponse> responses = new ArrayList<KafkaResponse>();
        responses = listPollingPredicate.evaluate();
        if (null == responses) {
            return Collections.EMPTY_LIST;
        }
        return responses;
    }
    
    @Override
    public List<KafkaResponse> findBy_topicValue_contains_env_size(final String topic_name, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_DURATION, final String environment_name, final int size) {
        return null;
    }
    
    private Callable<List<KafkaResponse>> listCallable(final using_impl impl, final String topic_name, final String record_json_path, final String record_json_path_value, final int time_interval) {
        KafkaAdapterRequestsImpl.logger.info("Starting DTO fetch in query in polling manner ............");
        final int[] counter = { 0 };
        return new Callable<List<KafkaResponse>>() {
            @Override
            public List<KafkaResponse> call() throws Exception {
                KafkaAdapterRequestsImpl.logger.info("DTO polling value ......... " + counter[0]++);
                switch (impl) {
                    case by_topicname: {
                        return KafkaAdapterRequestsImpl.this.findBy(topic_name);
                    }
                    case by_record_path: {
                        return KafkaAdapterRequestsImpl.this.findBy(record_json_path, record_json_path_value);
                    }
                    case by_topicName_recordPath: {
                        return KafkaAdapterRequestsImpl.this.findBy(topic_name, record_json_path, record_json_path_value);
                    }
                    case by_topicName_timeInterval: {
                        return KafkaAdapterRequestsImpl.this.findBy(topic_name, time_interval);
                    }
                    case by_topicValue_contains: {
                        return KafkaAdapterRequestsImpl.this.findBy_topicValue_contains(record_json_path_value);
                    }
                    case by_topicNameValue_contains: {
                        return KafkaAdapterRequestsImpl.this.findBy_topicValue_contains(topic_name, record_json_path_value);
                    }
                    default: {
                        return Collections.EMPTY_LIST;
                    }
                }
            }
        };
    }
    
    private Callable<List<KafkaResponse>> listCallable_env(final using_impl impl, final String topic_name, final String record_json_path, final String record_json_path_value, final int time_interval, final String environment_name) {
        return new Callable<List<KafkaResponse>>() {
            @Override
            public List<KafkaResponse> call() throws Exception {
                switch (impl) {
                    case by_topicname: {
                        return KafkaAdapterRequestsImpl.this.findBy_env(topic_name, environment_name);
                    }
                    case by_record_path: {
                        return KafkaAdapterRequestsImpl.this.findBy_env(record_json_path, record_json_path_value, environment_name);
                    }
                    case by_topicName_recordPath: {
                        return KafkaAdapterRequestsImpl.this.findBy_env(topic_name, record_json_path, record_json_path_value, environment_name);
                    }
                    case by_topicName_timeInterval: {
                        return KafkaAdapterRequestsImpl.this.findBy_env(topic_name, time_interval, environment_name);
                    }
                    case by_topicValue_contains: {
                        return KafkaAdapterRequestsImpl.this.findBy_topicValue_contains_env(record_json_path_value, environment_name);
                    }
                    case by_topicNameValue_contains: {
                        return KafkaAdapterRequestsImpl.this.findBy_topicValue_contains_env(topic_name, record_json_path_value, environment_name);
                    }
                    default: {
                        return Collections.EMPTY_LIST;
                    }
                }
            }
        };
    }

    @Override
    public boolean createTopicData(final GenerateTopicDataRequest generateTopicDataRequest) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String requestBody = objectMapper.writeValueAsString(generateTopicDataRequest);

            final HttpExec<Map> httpExecutor = new HttpExec<>(MethodType.POST, KafkaClientConfig.QA_KAFKA_CLIENT_URL_PROP_VALUE, "generate/topic/data", Map.class);
            final CustomHttpResponse<Map> resp = httpExecutor.setContentType(org.apache.http.entity.ContentType.APPLICATION_JSON).setNewBody(requestBody).execute();

            Map body = resp.getBody();
            if (body == null || body.isEmpty()) {
                throw new RuntimeException("API response body is null or empty: " + resp);
            }
            if (!"RECORD_PUSHED_SUCCESSFULLY".equalsIgnoreCase(String.valueOf(body.get("STATUS")))) {
                throw new RuntimeException("Unexpected STATUS in response: " + resp);
            }
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create topic data", ex);
        }
    }
    
    private List<KafkaResponse> prepareResponse(final Response response) {
        if (response == null || response.statusCode() == 500 || response.statusCode() == 503) {
            Assertions.fail("ACS response is not success");
        }
        if (response.statusCode() == 204) {
            KafkaAdapterRequestsImpl.logger.info("response from ACS: {}", (Object)"");
            return Collections.emptyList();
        }
        List<KafkaResponse> resp = KafkaResponseBuilder.map((List<Object>)response.as((Class)List.class));
        resp = resp.stream()
                .sorted(Comparator.comparing(i -> i.getCreatedTime().getTime()))
                .collect(Collectors.toList());
        KafkaAdapterRequestsImpl.logger.info(String.format("response from ACS: \n %s", resp));
        return resp;
    }
    
    static {
        KafkaAdapterRequestsImpl.kafkaAdapterRequests = null;
        KafkaAdapterRequestsImpl.logger = LoggerFactory.getLogger((Class)KafkaAdapterRequestsImpl.class);
    }
    
    private enum using_impl
    {
        by_topicname, 
        by_record_path, 
        by_topicName_recordPath, 
        by_topicName_timeInterval, 
        by_topicValue_contains, 
        by_topicNameValue_contains;
    }
    
    private class AcsReqFilter extends RequestLoggingFilter
    {
        public Response filter(final FilterableRequestSpecification requestSpec, final FilterableResponseSpecification responseSpec, final FilterContext ctx) {
            final String method = requestSpec.getMethod();
            final StringBuilder builder = new StringBuilder();
            final String basePath = requestSpec.getBasePath();
            builder.append("Requesting acs for path: " + basePath);
            builder.append(System.lineSeparator());
            if (method.equalsIgnoreCase("get")) {
                final Map<String, String> map = requestSpec.getQueryParams();
                map.forEach((k, v) -> builder.append(String.format("queryParam key: '%s' value: '%s'", k, v)));
            }
            if (method.equalsIgnoreCase("post")) {
                final String body = (String)requestSpec.getBody();
                builder.append("Request Body: " + body);
            }
            KafkaAdapterRequestsImpl.logger.info(builder.toString());
            return ctx.next(requestSpec, responseSpec);
        }
    }
}
