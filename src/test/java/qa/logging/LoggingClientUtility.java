// 

// 

package qa.logging;

import qa.logging.manager.ALoggingRequestsImpl;
import qa.logging.manager.PLoggingRequestsImpl;
import java.util.Arrays;
import qa.logging.manager.MultiLoggingRequestImpl;
import qa.generic.ClientConfigBase;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.logging.manager.ILoggingRequests;
import qa.generic.ClientUtility;

public class LoggingClientUtility implements ClientUtility
{
    public static ILoggingRequests I_LOGGING_REQUESTS;
    public static boolean ADAPTER_STATUS;
    private static LoggingClientUtility logClient;
    private final Logger logger;
    private final _LoggingClientConfig _loggingClientConfig;
    
    private LoggingClientUtility(final ILoggingRequests iLoggingRequests) {
        this.logger = LoggerFactory.getLogger((Class)LoggingClientUtility.class);
        LoggingClientUtility.I_LOGGING_REQUESTS = iLoggingRequests;
        this._loggingClientConfig = new _LoggingClientConfig();
    }
    
    public static LoggingClientUtility getInstance(final ILoggingRequests iLoggingRequests) {
        if (null == LoggingClientUtility.logClient) {
            LoggingClientUtility.logClient = new LoggingClientUtility(iLoggingRequests);
        }
        return LoggingClientUtility.logClient;
    }
    
    @Override
    public boolean initialize() {
        this._loggingClientConfig.lookUpForConfiguration();
        if (this._loggingClientConfig.configurationStatus()) {
            this.logger.info("logging client configuration SUCCESS");
            return LoggingClientUtility.ADAPTER_STATUS = true;
        }
        LoggingClientUtility.ADAPTER_STATUS = false;
        this.logger.error("logging client configuration FAILURE");
        return false;
    }
    
    static {
        LoggingClientUtility.logClient = null;
    }
    
    private class _LoggingClientConfig extends ClientConfigBase
    {
        private boolean _config_status;
        
        private _LoggingClientConfig() {
            this._config_status = false;
        }
        
        @Override
        public void lookUpForConfiguration() {
            try {
                this.load("qa_utility_client.properties");
            }
            catch (final RuntimeException e) {
                LoggingClientUtility.this.logger.info("Configuring LoggingClient through environment variables");
            }
            if (LoggingClientUtility.I_LOGGING_REQUESTS instanceof MultiLoggingRequestImpl) {
                this._config_status = Arrays.stream(((MultiLoggingRequestImpl)LoggingClientUtility.I_LOGGING_REQUESTS).getiLoggingRequests()).allMatch(i -> this.checkLoggingConfigStatus(i).configurationStatus());
                return;
            }
            this.checkLoggingConfigStatus(LoggingClientUtility.I_LOGGING_REQUESTS);
        }
        
        private _LoggingClientConfig checkLoggingConfigStatus(final ILoggingRequests iLoggingRequests) {
            if (iLoggingRequests instanceof PLoggingRequestsImpl) {
                LoggingConfigHolders.P_QA_LOGGING_SERVICE_URL_VALUE = this.getProperty("p.qa.logging.service.url");
                this._config_status = (null != LoggingConfigHolders.P_QA_LOGGING_SERVICE_URL_VALUE && !LoggingConfigHolders.P_QA_LOGGING_SERVICE_URL_VALUE.isEmpty());
                return this;
            }
            if (iLoggingRequests instanceof ALoggingRequestsImpl) {
                LoggingConfigHolders.A_QA_LOGGING_SERVICE_URL_VALUE = this.getProperty("a.qa.logging.service.url");
                this._config_status = (null != LoggingConfigHolders.A_QA_LOGGING_SERVICE_URL_VALUE && !LoggingConfigHolders.A_QA_LOGGING_SERVICE_URL_VALUE.isEmpty());
            }
            return this;
        }
        
        @Override
        public boolean configurationStatus() {
            return this._config_status;
        }
    }
}
