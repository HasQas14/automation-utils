// 

// 

package qa.msgBroker.adapter;

import qa.msgBroker.client.MsgBrokerClientConfig;

import java.net.URISyntaxException;
import java.util.List;
import qa.msgBroker.response.MessageBrokerResponse;
import qa.delete.adapter.KafkaAdapterRequests;
import qa.generic.Adapter;

public class MsgBrokerAdapter implements Adapter
{
    private static MsgBrokerAdapter msgBrokerAdapter;
    private static KafkaAdapterRequests<MessageBrokerResponse> adapterRequests;
    
    private MsgBrokerAdapter() {
        MsgBrokerAdapter.adapterRequests = MsgBrokerRequestsImpl.getInstance();
    }
    
    private static MsgBrokerAdapter getInstance() {
        if (MsgBrokerAdapter.msgBrokerAdapter == null) {
            MsgBrokerAdapter.msgBrokerAdapter = new MsgBrokerAdapter();
        }
        return MsgBrokerAdapter.msgBrokerAdapter;
    }
    
    public static List<MessageBrokerResponse> queryBy_topicName(final String topicName) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return MsgBrokerAdapter.adapterRequests.findBy(topicName);
    }
    
    public static MessageBrokerResponse queryBy_rowId(final long row_id) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return MsgBrokerAdapter.adapterRequests.findBy(row_id);
    }
    
    public static List<MessageBrokerResponse> queryBy_RecordPathInDTO(final String record_json_path, final String record_json_path_value) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return MsgBrokerAdapter.adapterRequests.findBy(record_json_path, record_json_path_value);
    }
    
    public static List<MessageBrokerResponse> queryBy_topicName_recordPath(final String topic_name, final String record_json_path, final String record_json_path_value) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return MsgBrokerAdapter.adapterRequests.findBy(topic_name, record_json_path, record_json_path_value);
    }
    
    public static List<MessageBrokerResponse> queryBy_topicName_timeInterval(final String topic_name, final int time_interval) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return MsgBrokerAdapter.adapterRequests.findBy(topic_name, time_interval);
    }
    
    @Override
    public void checkAdapterStatus() {
        assert MsgBrokerClientConfig.getInstance().configurationStatus() : "MsgBrokerClient not configured successfully";
    }
}
