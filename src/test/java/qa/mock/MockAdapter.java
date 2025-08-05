

// 

package qa.mock;

import org.slf4j.LoggerFactory;
import qa.mock.dto.GrpcResponse;
import qa.mock.dto.GrpcRequest;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.function.Predicate;
import qa.mock.dto.ProxyResponse;
import java.util.List;
import qa.mock.manager.GrpcRequestImpl;
import qa.mock.manager.MockRequestImpl;
import qa.mock.manager.IGrpcRequest;
import qa.mock.manager.IMockRequest;
import org.slf4j.Logger;
import qa.generic.Adapter;

public class MockAdapter implements Adapter
{
    private static final Logger logger;
    private static MockAdapter mockAdapter;
    private static IMockRequest iMockRequest;
    private static IGrpcRequest iGrpcRequest;
    
    private MockAdapter() {
        MockAdapter.iMockRequest = MockRequestImpl.getInstance();
        MockAdapter.iGrpcRequest = GrpcRequestImpl.getInstance();
    }
    
    private static MockAdapter getInstance() {
        if (MockAdapter.mockAdapter == null) {
            MockAdapter.mockAdapter = new MockAdapter();
        }
        return MockAdapter.mockAdapter;
    }
    
    public static List<ProxyResponse> findAll() {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequests();
    }
    
    public static List<ProxyResponse> findBy(final String url) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByUrl(url);
    }
    
    public static List<ProxyResponse> findBy(final String url, final Predicate<List<ProxyResponse>> listPredicate) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByUrl(url, listPredicate);
    }
    
    public static List<ProxyResponse> findBy(final String url, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByUrl(url, listPredicate, MAX_TIME, POLL_DURATION);
    }
    
    public static List<ProxyResponse> findByUrlContains(final String url) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByUrlContains(url);
    }
    
    public static List<ProxyResponse> findByUrlContains(final String url, final Predicate<List<ProxyResponse>> listPredicate) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByUrlContains(url, listPredicate);
    }
    
    public static List<ProxyResponse> findByUrlContains(final String url, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByUrlContains(url, listPredicate, MAX_TIME, POLL_DURATION);
    }
    
    public static List<ProxyResponse> findAll_ByRequestJsonPath(final String jsonPath, final String Value) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByRequestJsonPath(jsonPath, Value);
    }
    
    public static List<ProxyResponse> findAll_ByRequestJsonPath(final String jsonPath, final String Value, final Predicate<List<ProxyResponse>> listPredicate) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByRequestJsonPath(jsonPath, Value, listPredicate);
    }
    
    public static List<ProxyResponse> findAll_ByRequestJsonPath(final String jsonPath, final String Value, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByRequestJsonPath(jsonPath, Value, listPredicate, MAX_TIME, POLL_DURATION);
    }
    
    public static List<ProxyResponse> findAll_ByHeader(final String headerKey, final String headerValue) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByReqHeaderKeyValue(headerKey, headerValue);
    }
    
    public static List<ProxyResponse> findAll_ByHeader(final String headerKey, final String headerValue, final Predicate<List<ProxyResponse>> listPredicate) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByReqHeaderKeyValue(headerKey, headerValue, listPredicate);
    }
    
    public static List<ProxyResponse> findAll_ByHeader(final String headerKey, final String headerValue, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        getInstance().checkAdapterStatus();
        return MockAdapter.iMockRequest.getAllRequestByReqHeaderKeyValue(headerKey, headerValue, listPredicate, MAX_TIME, POLL_DURATION);
    }
    
    public static GrpcResponse execute_grpcRequest(final GrpcRequest grpcRequest) throws URISyntaxException {
        getInstance().checkAdapterStatus();
        return MockAdapter.iGrpcRequest.do_grpcRequest(grpcRequest);
    }
    
    @Override
    public void checkAdapterStatus() {
        assert MockClientConfig.getInstance().configurationStatus() : "MockClient not configured successfully";
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)MockAdapter.class);
        MockAdapter.mockAdapter = null;
    }
}
