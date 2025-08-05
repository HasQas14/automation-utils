// 

// 

package qa.logging.manager;

import qa.logging.dto.LogAppResponse;

import java.net.URISyntaxException;
import java.util.List;

public interface ILoggingRequests
{
    List<LogAppResponse> getLogs(final String p0);
    
    List<LogAppResponse> getLogs(final String p0, final String p1) throws URISyntaxException;
}
