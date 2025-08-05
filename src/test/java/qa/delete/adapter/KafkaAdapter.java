package qa.delete.adapter;

import org.slf4j.LoggerFactory;
import qa.delete.client.KafkaClientConfig;
import qa.delete.response.GenerateTopicDataRequest;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.function.Predicate;
import java.util.List;
import qa.delete.response.KafkaResponse;
import org.slf4j.Logger;
import qa.generic.Adapter;

public class KafkaAdapter implements Adapter
{
    private static final Logger logger;
    private static KafkaAdapter kafkaAdapter;
    private static KafkaAdapterRequests<KafkaResponse> kafkaAdapterRequests;
    
    private KafkaAdapter() {
        KafkaAdapter.kafkaAdapterRequests = KafkaAdapterRequestsImpl.getInstance();
    }
    
    private static KafkaAdapter getInstance() {
        if (KafkaAdapter.kafkaAdapter == null) {
            KafkaAdapter.kafkaAdapter = new KafkaAdapter();
        }
        return KafkaAdapter.kafkaAdapter;
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topicName);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_size(topicName, size);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topicName, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final String environment_name, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env_size(topicName, environment_name, size);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final Predicate<List<KafkaResponse>> predicate) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topicName, predicate);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topicName, predicate, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topicName, predicate, MAX_TIME, POLL_TIME);
    }
    
    public static List<KafkaResponse> queryBy_topicName(final String topicName, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topicName, predicate, MAX_TIME, POLL_TIME, environment_name);
    }
    
    @Deprecated
    public static List<KafkaResponse> queryBy_RecordPathInDTO(final String record_json_path, final String record_json_path_value) {
        getInstance().checkAdapterStatus();
        throw new RuntimeException("NOT_SUPPORTED");
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topic_name, record_json_path, record_json_path_value);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_size(topic_name, record_json_path, record_json_path_value, size);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topic_name, record_json_path, record_json_path_value, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final String environment_name, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env_size(topic_name, record_json_path, record_json_path_value, environment_name, size);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topic_name, record_json_path, record_json_path_value, predicate);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topic_name, record_json_path, record_json_path_value, predicate, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topic_name, record_json_path, record_json_path_value, predicate, MAX_TIME, POLL_TIME);
    }
    
    public static List<KafkaResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topic_name, record_json_path, record_json_path_value, predicate, MAX_TIME, POLL_TIME, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topic_name, time_interval);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_size(topic_name, time_interval, size);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topic_name, time_interval, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final String environment_name, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env_size(topic_name, time_interval, environment_name, size);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topic_name, time_interval, predicate);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topic_name, time_interval, predicate, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy(topic_name, time_interval, predicate, MAX_TIME, POLL_TIME);
    }
    
    public static List<KafkaResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_env(topic_name, time_interval, predicate, MAX_TIME, POLL_TIME, environment_name);
    }
    
    @Deprecated
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topicRecord_containValue) {
        getInstance().checkAdapterStatus();
        throw new RuntimeException("NOT_SUPPORTED");
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topic_name, final String topicRecord_containValue) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains(topic_name, topicRecord_containValue);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topic_name, final String topicRecord_containValue, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains_size(topic_name, topicRecord_containValue, size);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains_env(final String topic_name, final String topicRecord_containValue, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains_env(topic_name, topicRecord_containValue, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains_env(final String topic_name, final String topicRecord_containValue, final String environment_name, final int size) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains_env_size(topic_name, topicRecord_containValue, environment_name, size);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topic_name, final String topicRecord_containValue, final Predicate<List<KafkaResponse>> predicate) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains(topic_name, topicRecord_containValue, predicate);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topic_name, final String topicRecord_containValue, final Predicate<List<KafkaResponse>> predicate, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains_env(topic_name, topicRecord_containValue, predicate, environment_name);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topic_name, final String topicRecord_containValue, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains(topic_name, topicRecord_containValue, predicate, MAX_TIME, POLL_TIME);
    }
    
    public static List<KafkaResponse> queryBy_topicValue_contains(final String topic_name, final String topicRecord_containValue, final Predicate<List<KafkaResponse>> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String environment_name) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.findBy_topicValue_contains_env(topic_name, topicRecord_containValue, predicate, MAX_TIME, POLL_TIME, environment_name);
    }
    
    public static boolean generate_topicData(final GenerateTopicDataRequest generateTopicDataRequest) {
        getInstance().checkAdapterStatus();
        return KafkaAdapter.kafkaAdapterRequests.createTopicData(generateTopicDataRequest);
    }
    
    @Override
    public void checkAdapterStatus() {
        assert KafkaClientConfig.getInstance().configurationStatus() : "KafkaConsumerClient not configured successfully";
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)KafkaAdapter.class);
        KafkaAdapter.kafkaAdapter = null;
    }
}
