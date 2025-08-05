package qa.mock.manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import qa.mock.dto.MockResponseBuilder;
import qa.mock.dto.ProxyResponse;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Deprecated
public class GetAllRequests implements MockRequestFunctions {
    private static WireMock wireMock;
    private static GetAllRequests getAllRequests;
    private int DEFAULT_RESULT_SIZE;
    private Duration DEFAULT_DURATION;

    private GetAllRequests() {
        this.DEFAULT_RESULT_SIZE = 50;
        this.DEFAULT_DURATION = Duration.LAST_THIRTY_MINUTES;
        GetAllRequests.wireMock = MockManager.getInstance().getWireMock();
    }

    public static GetAllRequests getInstance() {
        if (GetAllRequests.getAllRequests == null) {
            GetAllRequests.getAllRequests = new GetAllRequests();
        }
        return GetAllRequests.getAllRequests;
    }

    @Override
    public List<ProxyResponse> getAllRequests() {
        final List<ServeEvent> serveEventList = GetAllRequests.wireMock.getServeEvents();
        return serveEventList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequests(final int max_size, final Duration duration) {
        return getAllRequests().parallelStream()
                .filter(item -> item.getTimestamp() > duration.getTimeStamp())
                .limit(max_size)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByUrl(final String url) {
        assert url != null && !url.isEmpty() : "url should not be null or empty parameter";
        return getInstance().getAllRequests(DEFAULT_RESULT_SIZE, DEFAULT_DURATION).parallelStream()
                .filter(item -> item.getRequest().getUrl().equalsIgnoreCase(url))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByUrl(final String url, final int max_size, final Duration duration) {
        assert url != null && !url.isEmpty() : "url should not be null or empty parameter";
        return getInstance().getAllRequests(max_size, duration).parallelStream()
                .filter(item -> item.getRequest().getUrl().equalsIgnoreCase(url))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByUrlContains(final String url) {
        assert url != null && !url.isEmpty() : "url should not be null or empty parameter";
        return getInstance().getAllRequests(DEFAULT_RESULT_SIZE, DEFAULT_DURATION).parallelStream()
                .filter(item -> item.getRequest().getUrl().contains(url))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByUrlContains(final String url, final int max_size, final Duration duration) {
        assert url != null && !url.isEmpty() : "url should not be null or empty parameter";
        return getInstance().getAllRequests(max_size, duration).parallelStream()
                .filter(item -> item.getRequest().getUrl().contains(url))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByRequestJsonPath(final String jsonPathPattern) {
        assert jsonPathPattern != null && !jsonPathPattern.isEmpty() : "jsonPathPattern should not be empty";
        assert !jsonPathPattern.startsWith("\\$") : "jsonPathPattern should not start with \\$";
        return getInstance().getAllRequests().parallelStream()
                .filter(item -> {
                    try {
                        JsonPath j = new JsonPath(item.getRequest().getRequestBody().toString());
                        return this.jsonEvaluator(j, jsonPathPattern);
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByRequestJsonPath(final String jsonPathPattern, final int max_size, final Duration duration) {
        assert jsonPathPattern != null && !jsonPathPattern.isEmpty() : "jsonPathPattern should not be empty";
        return getInstance().getAllRequests(max_size, duration).parallelStream()
                .filter(item -> {
                    try {
                        JsonPath j = new JsonPath(item.getRequest().getRequestBody().toString());
                        return this.jsonEvaluator(j, jsonPathPattern);
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByResponseJsonPath(final String jsonPathPattern) {
        assert jsonPathPattern != null && !jsonPathPattern.isEmpty() : "jsonPathPattern should not be empty";
        return getInstance().getAllRequests(DEFAULT_RESULT_SIZE, DEFAULT_DURATION).parallelStream()
                .filter(item -> {
                    try {
                        JsonPath j = new JsonPath(item.getResponse().getResponseBody().toString());
                        return this.jsonEvaluator(j, jsonPathPattern);
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByResponseJsonPath(final String jsonPathPattern, final int max_size, final Duration duration) {
        assert jsonPathPattern != null && !jsonPathPattern.isEmpty() : "jsonPathPattern should not be empty";
        return getInstance().getAllRequests(max_size, duration).parallelStream()
                .filter(item -> {
                    try {
                        JsonPath j = new JsonPath(item.getResponse().getResponseBody().toString());
                        return this.jsonEvaluator(j, jsonPathPattern);
                    } catch (Exception e) {
                        return false;
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String headerKey, final String headerValue) {
        assert headerKey != null && !headerKey.isEmpty() : "headerKey should not be null or empty";
        assert headerValue != null && !headerValue.isEmpty() : "headerValue should not be null or empty";
        return getInstance().getAllRequests(DEFAULT_RESULT_SIZE, DEFAULT_DURATION).parallelStream()
                .filter(item -> {
                    Map headers = item.getRequest().getHeaders();
                    return headers.containsKey(headerKey)
                            && headerValue.equalsIgnoreCase(headers.get(headerKey).toString());
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByReqHeaderKeyValue(final String headerKey, final String headerValue, final int max_size, final Duration duration) {
        assert headerKey != null && !headerKey.isEmpty() : "headerKey should not be null or empty";
        assert headerValue != null && !headerValue.isEmpty() : "headerValue should not be null or empty";
        return getInstance().getAllRequests(max_size, duration).parallelStream()
                .filter(item -> {
                    Map headers = item.getRequest().getHeaders();
                    return headers.containsKey(headerKey)
                            && headerValue.equalsIgnoreCase(headers.get(headerKey).toString());
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByResHeaderKeyValue(final String headerKey, final String headerValue) {
        assert headerKey != null && !headerKey.isEmpty() : "headerKey should not be null or empty";
        assert headerValue != null && !headerValue.isEmpty() : "headerValue should not be null or empty";
        return getInstance().getAllRequests(DEFAULT_RESULT_SIZE, DEFAULT_DURATION).parallelStream()
                .filter(item -> {
                    Map headers = item.getResponse().getHeaders();
                    return headers.containsKey(headerKey)
                            && headerValue.equalsIgnoreCase(headers.get(headerKey).toString());
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProxyResponse> getAllRequestByResHeaderKeyValue(final String headerKey, final String headerValue, final int max_size, final Duration duration) {
        assert headerKey != null && !headerKey.isEmpty() : "headerKey should not be null or empty";
        assert headerValue != null && !headerValue.isEmpty() : "headerValue should not be null or empty";
        return getInstance().getAllRequests(max_size, duration).parallelStream()
                .filter(item -> {
                    Map headers = item.getResponse().getHeaders();
                    return headers.containsKey(headerKey)
                            && headerValue.equalsIgnoreCase(headers.get(headerKey).toString());
                }).collect(Collectors.toList());
    }

    private ProxyResponse map(final ServeEvent event) {
        return MockResponseBuilder.map(event);
    }

    private boolean jsonEvaluator(final JsonPath jsonPath, final String jsonExpression) {
        try {
            Boolean bool = jsonPath.getBoolean(jsonExpression);
            return Boolean.TRUE.equals(bool);
        } catch (Exception ex) {
            return false;
        }
    }

    // Deprecated, used nowhere — consider removing
    @Deprecated
    private String generateJsonPathPattern(final String pattern, final PATTERN_TYPE pattern_type) {
        switch (pattern_type) {
            case REQUEST:
                return "root.findAll { it.request.requestBody." + pattern + " }";
            case RESPONSE:
                return "root.findAll { it.response.responseBody." + pattern + " }";
            default:
                return pattern;
        }
    }

    @Deprecated
    private enum PATTERN_TYPE {
        REQUEST,
        RESPONSE
    }
}
