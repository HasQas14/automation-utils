// 

// 

package qa.mock;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.generic.ClientUtility;

public class MockClientUtility implements ClientUtility
{
    private static MockClientUtility mockClient;
    private Logger logger;
    
    public static MockClientUtility getInstance() {
        return MockClientUtility.mockClient;
    }
    
    private MockClientUtility() {
        this.logger = LoggerFactory.getLogger((Class)MockClientUtility.class);
        MockClientConfig.getInstance().lookUpForConfiguration();
        this.logger.info("mock client initialized");
    }
    
    @Override
    public boolean initialize() {
        if (MockClientConfig.getInstance().configurationStatus()) {
            this.logger.info("mock client configuration SUCCESS");
            return true;
        }
        this.logger.error("mock client configuration FAILURE");
        return false;
    }
    
    static {
        MockClientUtility.mockClient = null;
        MockClientUtility.mockClient = new MockClientUtility();
    }
}
