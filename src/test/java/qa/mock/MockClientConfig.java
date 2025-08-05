// 

// 

package qa.mock;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.generic.ClientConfigBase;

public class MockClientConfig extends ClientConfigBase
{
    private static boolean configStatus;
    private static MockClientConfig mockClientConfig;
    public static final String QA_MOCK_SERVICE_IP_PROP_NAME = "qa.mock.service.ip";
    public static final String QA_MOCK_SERVICE_PORT_PROP_NAME = "qa.mock.service.port";
    public static final String QA_GRPC_MOCK_SERVICE_URL_PROP_NAME = "qa.grpc.mock.service.url";
    public static String QA_MOCK_SERVICE_IP_PROP_VALUE;
    public static String QA_MOCK_SERVICE_PORT_PROP_VALUE;
    public static String QA_GRPC_MOCK_SERVICE_URL_PROP_VALUE;
    private Logger logger;
    
    private MockClientConfig() {
        this.logger = LoggerFactory.getLogger((Class)MockClientConfig.class);
    }
    
    public static MockClientConfig getInstance() {
        if (MockClientConfig.mockClientConfig == null) {
            MockClientConfig.mockClientConfig = new MockClientConfig();
        }
        return MockClientConfig.mockClientConfig;
    }
    
    @Override
    public void lookUpForConfiguration() {
        this.logger.info(String.format("Configuring MockClient", new Object[0]));
        try {
            this.load("qa_utility_client.properties");
        }
        catch (final RuntimeException e) {
            this.logger.info("Configuring MockClient through environment variables");
        }
        MockClientConfig.QA_MOCK_SERVICE_IP_PROP_VALUE = this.getProperty("qa.mock.service.ip");
        MockClientConfig.QA_MOCK_SERVICE_PORT_PROP_VALUE = this.getProperty("qa.mock.service.port");
        MockClientConfig.QA_GRPC_MOCK_SERVICE_URL_PROP_VALUE = this.getProperty("qa.grpc.mock.service.url");
        this.setConfigStatus();
    }
    
    private void setConfigStatus() {
        MockClientConfig.configStatus = ((null != MockClientConfig.QA_MOCK_SERVICE_IP_PROP_VALUE && !MockClientConfig.QA_MOCK_SERVICE_IP_PROP_VALUE.isEmpty()) || (null != MockClientConfig.QA_MOCK_SERVICE_PORT_PROP_VALUE && !MockClientConfig.QA_MOCK_SERVICE_PORT_PROP_VALUE.isEmpty()) || (null != MockClientConfig.QA_GRPC_MOCK_SERVICE_URL_PROP_VALUE && !MockClientConfig.QA_GRPC_MOCK_SERVICE_URL_PROP_VALUE.isEmpty()));
    }
    
    @Override
    public boolean configurationStatus() {
        return MockClientConfig.configStatus;
    }
    
    static {
        MockClientConfig.configStatus = false;
        MockClientConfig.mockClientConfig = null;
    }
}
