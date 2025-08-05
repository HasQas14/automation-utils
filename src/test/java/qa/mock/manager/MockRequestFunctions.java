// 

// 

package qa.mock.manager;

import qa.mock.dto.ProxyResponse;
import java.util.List;

public interface MockRequestFunctions
{
    List<ProxyResponse> getAllRequests();
    
    List<ProxyResponse> getAllRequests(final int p0, final Duration p1);
    
    List<ProxyResponse> getAllRequestByUrl(final String p0);
    
    List<ProxyResponse> getAllRequestByUrl(final String p0, final int p1, final Duration p2);
    
    List<ProxyResponse> getAllRequestByUrlContains(final String p0);
    
    List<ProxyResponse> getAllRequestByUrlContains(final String p0, final int p1, final Duration p2);
    
    List<ProxyResponse> getAllRequestByRequestJsonPath(final String p0);
    
    List<ProxyResponse> getAllRequestByRequestJsonPath(final String p0, final int p1, final Duration p2);
    
    List<ProxyResponse> getAllRequestByResponseJsonPath(final String p0);
    
    List<ProxyResponse> getAllRequestByResponseJsonPath(final String p0, final int p1, final Duration p2);
    
    List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String p0, final String p1);
    
    List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String p0, final String p1, final int p2, final Duration p3);
    
    List<ProxyResponse> getAllRequestByResHeaderKeyValue(final String p0, final String p1);
    
    List<ProxyResponse> getAllRequestByResHeaderKeyValue(final String p0, final String p1, final int p2, final Duration p3);
}
