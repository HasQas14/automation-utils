// 

// 

package qa.mock.manager;

import qa.generic.PollingPredicate;

import java.util.stream.Collectors;

import qa.mock.dto.MockResponseBuilder;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.MatchesJsonPathPattern;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import qa.mock.dto.ProxyResponse;
import java.util.List;
import com.github.tomakehurst.wiremock.client.WireMock;
import qa.mock.MockClientConfig;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import org.slf4j.Logger;

public class MockRequestImpl implements IMockRequest
{
    private final Logger logger;
    private static MockRequestImpl iMockRequest;
    private static Duration MAX_TIME;
    private static Duration POLL_DURATION;
    
    private MockRequestImpl() {
        this.logger = LoggerFactory.getLogger((Class)MockRequestImpl.class);
        WireMock.configureFor(MockClientConfig.QA_MOCK_SERVICE_IP_PROP_VALUE, Integer.parseInt(MockClientConfig.QA_MOCK_SERVICE_PORT_PROP_VALUE));
        this.logger.info("wiremock client initialized successfully");
    }
    
    public static MockRequestImpl getInstance() {
        if (MockRequestImpl.iMockRequest == null) {
            MockRequestImpl.iMockRequest = new MockRequestImpl();
        }
        return MockRequestImpl.iMockRequest;
    }
    
    @Override
    public List<ProxyResponse> getAllRequests() {
        return this.findRequest(RequestPatternBuilder.allRequests());
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByUrl(final String url) {
        final RequestPatternBuilder requestPatternBuilder = RequestPatternBuilder.newRequestPattern();
        return this.findRequest(requestPatternBuilder.withUrl(url));
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByUrl(final String url, final Predicate<List<ProxyResponse>> listPredicate) {
        return this.getAllRequestByUrl(url, listPredicate, MockRequestImpl.MAX_TIME, MockRequestImpl.POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByUrl(final String url, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final Callable<List<ProxyResponse>> listCallable = this.listCallable(IMPL.getAllRequestByUrl, null, null, null, null, url);
        return this.evaluate(listCallable, MAX_TIME, POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByUrlContains(final String url) {
        final RequestPatternBuilder requestPatternBuilder = RequestPatternBuilder.newRequestPattern();
        final StringValuePattern urlPtrn = (StringValuePattern)new ContainsPattern(url);
        requestPatternBuilder.withUrl(urlPtrn.getExpected());
        return this.findRequest(requestPatternBuilder);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByUrlContains(final String url, final Predicate<List<ProxyResponse>> listPredicate) {
        return this.getAllRequestByUrlContains(url, listPredicate, MockRequestImpl.MAX_TIME, MockRequestImpl.POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByUrlContains(final String url, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final Callable<List<ProxyResponse>> listCallable = this.listCallable(IMPL.getAllRequestByUrlContains, null, null, null, null, url);
        return this.evaluate(listCallable, MAX_TIME, POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByRequestJsonPath(final String jsonKey, final String Value) {
        assert null != jsonKey : "jsonKey should not be null";
        assert null != Value : "Value should not be null";
        final RequestPatternBuilder requestPatternBuilder = RequestPatternBuilder.newRequestPattern();
        final ContentPattern contentPattern = (ContentPattern)new MatchesJsonPathPattern(jsonKey, (StringValuePattern)new ContainsPattern(Value));
        requestPatternBuilder.withRequestBody(contentPattern);
        return this.findRequest(requestPatternBuilder);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByRequestJsonPath(final String jsonKey, final String Value, final Predicate<List<ProxyResponse>> listPredicate) {
        return this.getAllRequestByRequestJsonPath(jsonKey, Value, listPredicate, MockRequestImpl.MAX_TIME, MockRequestImpl.POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByRequestJsonPath(final String jsonKey, final String Value, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final Callable<List<ProxyResponse>> listCallable = this.listCallable(IMPL.getAllRequestByRequestJsonPath, jsonKey, Value, null, null, null);
        return this.evaluate(listCallable, MAX_TIME, POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String headerKey, final String headerValue) {
        assert null != headerKey : "headerKey should not be null";
        assert null != headerValue : "headerValue should not be null";
        final RequestPatternBuilder requestPatternBuilder = RequestPatternBuilder.newRequestPattern();
        requestPatternBuilder.withHeader(headerKey, (StringValuePattern)new ContainsPattern(headerValue));
        return this.findRequest(requestPatternBuilder);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String headerKey, final String headerValue, final Predicate<List<ProxyResponse>> listPredicate) {
        return this.getAllRequestByReqHeaderKeyValue(headerKey, headerValue, listPredicate, MockRequestImpl.MAX_TIME, MockRequestImpl.POLL_DURATION);
    }
    
    @Override
    public List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String headerKey, final String headerValue, final Predicate<List<ProxyResponse>> listPredicate, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final Callable<List<ProxyResponse>> listCallable = this.listCallable(IMPL.getAllRequestByReqHeaderKeyValue, null, null, headerKey, headerValue, null);
        return this.evaluate(listCallable, MAX_TIME, POLL_DURATION);
    }
    
    private Callable<List<ProxyResponse>> listCallable(final IMPL impl, final String jsonKey, final String Value, final String headerKey, final String headerValue, final String url) {
        return new Callable<List<ProxyResponse>>() {
            @Override
            public List<ProxyResponse> call() throws Exception {
                switch (impl) {
                    case getAllRequestByReqHeaderKeyValue: {
                        return MockRequestImpl.this.getAllRequestByReqHeaderKeyValue(headerKey, headerValue);
                    }
                    case getAllRequestByRequestJsonPath: {
                        return MockRequestImpl.this.getAllRequestByRequestJsonPath(jsonKey, Value);
                    }
                    case getAllRequestByUrl: {
                        return MockRequestImpl.this.getAllRequestByUrl(url);
                    }
                    case getAllRequestByUrlContains: {
                        return MockRequestImpl.this.getAllRequestByUrlContains(url);
                    }
                    default: {
                        assert false : "impl cannot be null";
                        return null;
                    }
                }
            }
        };
    }
    
    private List<ProxyResponse> findRequest(final RequestPatternBuilder requestPatternBuilder) {
        assert null != requestPatternBuilder : "requestPattern cannot be null";
        this.logger.info("[QUERY] request to mock: " + requestPatternBuilder.build().toString());
        final List<ProxyResponse> responseList = (List<ProxyResponse>)WireMock.findAll(requestPatternBuilder).stream().map(MockResponseBuilder::map).collect(Collectors.toList());
        this.logger.info("[RESPONSE] from mock service: " + responseList);
        return responseList;
    }
    
    private List<ProxyResponse> evaluate(final Callable<List<ProxyResponse>> prListCallable, final Duration MAX_TIME, final Duration POLL_DURATION) {
        final PollingPredicate<List<ProxyResponse>> predicate = new PollingPredicate<List<ProxyResponse>>(prListCallable, MAX_TIME, POLL_DURATION);
        return predicate.evaluate();
    }
    
    static {
        MockRequestImpl.MAX_TIME = Duration.ofMinutes(2L);
        MockRequestImpl.POLL_DURATION = Duration.ofSeconds(2L);
    }
    
    private enum IMPL
    {
        getAllRequestByUrl, 
        getAllRequestByUrlContains, 
        getAllRequestByRequestJsonPath, 
        getAllRequestByReqHeaderKeyValue;
    }
}
