// 

// 

package qa.mock.manager;

import java.time.Duration;
import java.util.function.Predicate;
import qa.mock.dto.ProxyResponse;
import java.util.List;

public interface IMockRequest
{
    List<ProxyResponse> getAllRequests();
    
    List<ProxyResponse> getAllRequestByUrl(final String p0);
    
    List<ProxyResponse> getAllRequestByUrl(final String p0, final Predicate<List<ProxyResponse>> p1);
    
    List<ProxyResponse> getAllRequestByUrl(final String p0, final Predicate<List<ProxyResponse>> p1, final Duration p2, final Duration p3);
    
    List<ProxyResponse> getAllRequestByUrlContains(final String p0);
    
    List<ProxyResponse> getAllRequestByUrlContains(final String p0, final Predicate<List<ProxyResponse>> p1);
    
    List<ProxyResponse> getAllRequestByUrlContains(final String p0, final Predicate<List<ProxyResponse>> p1, final Duration p2, final Duration p3);
    
    List<ProxyResponse> getAllRequestByRequestJsonPath(final String p0, final String p1);
    
    List<ProxyResponse> getAllRequestByRequestJsonPath(final String p0, final String p1, final Predicate<List<ProxyResponse>> p2);
    
    List<ProxyResponse> getAllRequestByRequestJsonPath(final String p0, final String p1, final Predicate<List<ProxyResponse>> p2, final Duration p3, final Duration p4);
    
    List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String p0, final String p1);
    
    List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String p0, final String p1, final Predicate<List<ProxyResponse>> p2);
    
    List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String p0, final String p1, final Predicate<List<ProxyResponse>> p2, final Duration p3, final Duration p4);
}
