// 

// 

package qa.logging;

import qa.logging.dto.LogAppResponse;

import java.net.URISyntaxException;
import java.util.List;
import qa.generic.Adapter;

public class LoggingAdapter implements Adapter
{
    private static LoggingAdapter loggingAdapter;
    
    private LoggingAdapter() {
    }
    
    private static LoggingAdapter getInstance() {
        if (LoggingAdapter.loggingAdapter == null) {
            LoggingAdapter.loggingAdapter = new LoggingAdapter();
        }
        return LoggingAdapter.loggingAdapter;
    }
    
    public static List<LogAppResponse> getLogs(final String traceId, final String appName) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        assert null != traceId && !traceId.isEmpty() : "traceId should not be null or empty";
        assert null != appName && !appName.isEmpty() : "appName should not be null or empty";
        return LoggingClientUtility.I_LOGGING_REQUESTS.getLogs(traceId, appName);
    }
    
    @Override
    public void checkAdapterStatus() {
        assert LoggingClientUtility.ADAPTER_STATUS : "LoggingClient not configured successfully";
    }
    
    static {
        LoggingAdapter.loggingAdapter = null;
    }
}
