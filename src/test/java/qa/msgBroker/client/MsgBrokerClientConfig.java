// 

// 

package qa.msgBroker.client;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.generic.ClientConfigBase;

public class MsgBrokerClientConfig extends ClientConfigBase
{
    private Logger logger;
    private static MsgBrokerClientConfig msgBrokerClientConfig;
    public static final String QA_MSG_BROKER_CLIENT_URL_PROP_NAME = "qa.msgBroker.consumer.service.url";
    public static String QA_MSG_BROKER_CLIENT_URL_PROP_VALUE;
    private static boolean configStatus;
    
    private MsgBrokerClientConfig() {
        this.logger = LoggerFactory.getLogger((Class)MsgBrokerClientConfig.class);
    }
    
    public static MsgBrokerClientConfig getInstance() {
        if (MsgBrokerClientConfig.msgBrokerClientConfig == null) {
            MsgBrokerClientConfig.msgBrokerClientConfig = new MsgBrokerClientConfig();
        }
        return MsgBrokerClientConfig.msgBrokerClientConfig;
    }
    
    @Override
    public void lookUpForConfiguration() {
        this.logger.info(String.format("Configuring MsgBrokerClient", new Object[0]));
        try {
            this.load("qa_utility_client.properties");
        }
        catch (final RuntimeException e) {
            this.logger.info("Configuring MsgBroker Client through environment variables");
        }
        MsgBrokerClientConfig.QA_MSG_BROKER_CLIENT_URL_PROP_VALUE = this.getProperty("qa.msgBroker.consumer.service.url");
        this.setConfigStatus();
    }
    
    private void setConfigStatus() {
        MsgBrokerClientConfig.configStatus = (null != MsgBrokerClientConfig.QA_MSG_BROKER_CLIENT_URL_PROP_VALUE && !MsgBrokerClientConfig.QA_MSG_BROKER_CLIENT_URL_PROP_VALUE.isEmpty());
    }
    
    @Override
    public boolean configurationStatus() {
        return MsgBrokerClientConfig.configStatus;
    }
    
    static {
        MsgBrokerClientConfig.msgBrokerClientConfig = null;
        MsgBrokerClientConfig.configStatus = false;
    }
}
