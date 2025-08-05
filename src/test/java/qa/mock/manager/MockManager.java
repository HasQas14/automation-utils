// 

// 

package qa.mock.manager;

import org.slf4j.LoggerFactory;
import qa.mock.MockClientConfig;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.slf4j.Logger;

public final class MockManager
{
    private static Logger logger;
    private static MockManager mockManager;
    private WireMock wireMock;
    
    private MockManager() {
        this.wireMock = null;
        this.initializeWireMock();
    }
    
    public static MockManager getInstance() {
        if (MockManager.mockManager == null) {
            MockManager.mockManager = new MockManager();
        }
        return MockManager.mockManager;
    }
    
    public WireMock getWireMock() {
        return this.wireMock;
    }
    
    private void initializeWireMock() {
        this.wireMock = new WireMock(MockClientConfig.QA_MOCK_SERVICE_IP_PROP_VALUE, (int)Integer.valueOf(MockClientConfig.QA_MOCK_SERVICE_PORT_PROP_VALUE));
        MockManager.logger.debug("wiremock client initialized successfully");
    }
    
    static {
        MockManager.logger = LoggerFactory.getLogger((Class)MockManager.class);
    }
}
