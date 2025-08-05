// 

// 

package qa.mock.dto;

import com.github.tomakehurst.wiremock.http.HttpHeader;
import net.minidev.json.parser.JSONParser;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import net.minidev.json.JSONObject;
import java.util.Map;
import com.github.tomakehurst.wiremock.http.QueryParameter;
import java.util.HashMap;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;

public class MockResponseBuilder
{
    public static ProxyResponse map(final LoggedRequest event) {
        final ProxyResponse proxyResponse = new ProxyResponse();
        final Map<String, String> reqHeader = new HashMap<String, String>();
        if (null != event.getHeaders()) {
            event.getHeaders().all().parallelStream().forEach(item -> reqHeader.put(item.key(), item.values().toString()));
        }
        final Map<String, String> queryParam = new HashMap<String, String>();
        if (null != event.getQueryParams()) {
            event.getQueryParams().entrySet().parallelStream().forEach(item -> queryParam.put(item.getKey(), item.getValue().toString()));
        }
        final JSONObject reqJson = stringTojson(event.getBodyAsString());
        final Request.RequestBuilder requestBuilder = Request.builder();
        if (null == reqJson) {
            requestBuilder.requestBody("");
        }
        else {
            requestBuilder.requestBody(reqJson);
        }
        proxyResponse.setRequest(requestBuilder.url(event.getUrl()).headers(reqHeader).queryParameter(queryParam).loggedDate(event.getLoggedDate()).build());
        return proxyResponse;
    }
    
    public static ProxyResponse map(final ServeEvent event) {
        final ProxyResponse proxyResponse = new ProxyResponse();
        final Map<String, String> reqHeader = new HashMap<String, String>();
        if (null != event.getRequest().getHeaders()) {
            event.getRequest().getHeaders().all().parallelStream().forEach(item -> reqHeader.put(item.key(), item.values().toString()));
        }
        final Map<String, String> queryParam = new HashMap<String, String>();
        if (null != event.getRequest().getQueryParams()) {
            event.getRequest().getQueryParams().entrySet().parallelStream().forEach(item -> queryParam.put(item.getKey(), item.getValue().toString()));
        }
        final JSONObject reqJson = stringTojson(event.getRequest().getBodyAsString());
        final Request.RequestBuilder requestBuilder = Request.builder();
        if (null == reqJson) {
            requestBuilder.requestBody("");
        }
        else {
            requestBuilder.requestBody(reqJson);
        }
        proxyResponse.setRequest(requestBuilder.url(event.getRequest().getUrl()).headers(reqHeader).queryParameter(queryParam).build());
        final Map<String, String> resHeader = new HashMap<String, String>();
        if (null != event.getResponse().getHeaders()) {
            event.getResponse().getHeaders().all().parallelStream().forEach(item -> resHeader.put(item.key(), item.values().toString()));
        }
        final JSONObject respJson = stringTojson(event.getResponse().getBodyAsString());
        final Response.ResponseBuilder respBuilder = Response.builder();
        if (null == respJson) {
            respBuilder.responseBody(event.getResponse().getBodyAsString());
        }
        else {
            respBuilder.responseBody(respJson);
        }
        proxyResponse.setResponse(respBuilder.statusCode(event.getResponse().getStatus()).headers(resHeader).build());
        proxyResponse.setTimestamp(event.getRequest().getLoggedDate().getTime());
        return proxyResponse;
    }
    
    private static JSONObject stringTojson(final String jsonString) {
        final JSONParser parser = new JSONParser();
        try {
            final JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            return jsonObject;
        }
        catch (final Exception e) {
            return null;
        }
    }
}
