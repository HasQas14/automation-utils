// 

// 

package qa.msgBroker.client;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.generic.ClientUtility;

public class MsgBrokerClientUtility implements ClientUtility
{
    private static MsgBrokerClientUtility msgBrokerClientUtility;
    private Logger logger;
    
    private MsgBrokerClientUtility() {
        this.logger = LoggerFactory.getLogger((Class)MsgBrokerClientUtility.class);
        MsgBrokerClientConfig.getInstance().lookUpForConfiguration();
        this.logger.info("Msg broker client initialized");
    }
    
    public static MsgBrokerClientUtility getInstance() {
        if (MsgBrokerClientUtility.msgBrokerClientUtility == null) {
            MsgBrokerClientUtility.msgBrokerClientUtility = new MsgBrokerClientUtility();
        }
        return MsgBrokerClientUtility.msgBrokerClientUtility;
    }
    
    @Override
    public boolean initialize() {
        if (MsgBrokerClientConfig.getInstance().configurationStatus()) {
            this.logger.info("MsgBroker client configuration SUCCESS");
            return true;
        }
        this.logger.error("MsgBroker client configuration FAILURE");
        return false;
    }
}
