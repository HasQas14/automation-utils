// 

// 

package qa.logging.manager;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import qa.logging.dto.LogAppResponse;
import java.util.List;

public class MultiLoggingRequestImpl implements ILoggingRequests
{
    private final ILoggingRequests[] iLoggingRequests;
    
    public MultiLoggingRequestImpl(final ILoggingRequests... iLoggingRequests) {
        assert iLoggingRequests.length != 0 : "Single implementation is mandatory for this type of request";
        this.iLoggingRequests = iLoggingRequests;
    }
    
    public ILoggingRequests[] getiLoggingRequests() {
        return this.iLoggingRequests;
    }
    
    @Override
    public List<LogAppResponse> getLogs(final String traceId) {
        final List<LogAppResponse> logAppResponses = new LinkedList<LogAppResponse>();
        Arrays.stream(this.iLoggingRequests).forEach(i -> logAppResponses.addAll(i.getLogs(traceId)));
        return logAppResponses;
    }
    
    @Override
    public List<LogAppResponse> getLogs(final String traceId, final String appName) {
        final List<LogAppResponse> logAppResponses = new LinkedList<LogAppResponse>();
        Arrays.stream(this.iLoggingRequests).forEach(i -> {
            try {
                logAppResponses.addAll(i.getLogs(traceId, appName));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        return logAppResponses;
    }
}
