package qa.generic.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.jsoup.nodes.Document;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jsoup.Jsoup;
import org.apache.http.client.methods.CloseableHttpResponse;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import java.net.URISyntaxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.StringEntity;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;

import java.util.List;
import java.util.Map;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;

public class HttpExec<T>
{
    private static final Logger logger;
    private CloseableHttpClient closeableHttpClient;
    private MethodType methodType;
    private HttpEntity requestEntity;
    private HttpUriRequest httpUriRequest;
    private URIBuilder uriBuilder;
    private Class<T> tClass;

    public HttpExec(final MethodType methodType, final String baseUri, final String basePath, final Class<T> clazz) throws URISyntaxException {
        this.methodType = methodType;
        this.tClass = clazz;
        this.closeableHttpClient = HttpClients.createDefault();
        final URIBuilder uriBuilder = new URIBuilder(baseUri);
        uriBuilder.setPath(uriBuilder.getPath() + basePath);
        this.uriBuilder = uriBuilder;

        switch (methodType) {
            case POST:
                this.httpUriRequest = new HttpPost(uriBuilder.build());
                break;
            case GET:
                this.httpUriRequest = new HttpGet(uriBuilder.build());
                break;
            default:
                throw new IllegalArgumentException("Unsupported method type: " + methodType);
        }
    }
    
    public HttpExec setQueryParam(final String key, final Object value) {
        this.uriBuilder.addParameter(key, value.toString());
        return this;
    }
    
    public HttpExec setQueryParam(final Map<String, String> queryParams) {
        queryParams.forEach((key, value) -> this.uriBuilder.addParameter(key, value));
        return this;
    }
    
    public HttpExec setContentType(final ContentType contentType) {
        this.httpUriRequest.setHeader("Content-type", contentType.toString());
        return this;
    }
    
    public HttpExec setHeader(final String headerName, final String headerValue) {
        this.httpUriRequest.setHeader(headerName, headerValue);
        return this;
    }
    
    public HttpExec setHeader(final Header[] headers) {
        this.httpUriRequest.setHeaders(headers);
        return this;
    }
    
    public HttpExec setNewBody(final String body) throws UnsupportedEncodingException {
        final StringEntity e = new StringEntity(body);
        this.requestEntity = (HttpEntity)e;
        return this;
    }

    public HttpExec setNewBody(final Map map) {
        try {
            final String json = new ObjectMapper().writeValueAsString(map);
            this.requestEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            return this;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body to JSON", e);
        }
    }
    
    public CustomHttpResponse<T> execute() {
        final CustomHttpResponse.CustomHttpResponseBuilder customHttpResponseBuilder = CustomHttpResponse.builder();
        try {
            CloseableHttpResponse httpResponse = null;
            switch (this.methodType) {
                case GET: {
                    ((HttpGet)this.httpUriRequest).setURI(this.uriBuilder.build());
                    httpResponse = this.closeableHttpClient.execute(this.httpUriRequest);
                    break;
                }
                case POST: {
                    ((HttpPost)this.httpUriRequest).setURI(this.uriBuilder.build());
                    ((HttpPost)this.httpUriRequest).setEntity(this.requestEntity);
                    httpResponse = this.closeableHttpClient.execute(this.httpUriRequest);
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + this.methodType);
                }
            }
            return customHttpResponseBuilder.status(httpResponse.getStatusLine().getStatusCode()).message("SUCCESS").body(this.converter(httpResponse)).build();
        }
        catch (final URISyntaxException uriSyntaxException) {
            return customHttpResponseBuilder.status(400).message("ERROR_BUILD_URI" + uriSyntaxException).build();
        }
        catch (final ClientProtocolException e) {
            e.printStackTrace();
            return customHttpResponseBuilder.status(500).message("CLIENT_PROTOCOL_ERROR" + e).build();
        }
        catch (final IOException e2) {
            e2.printStackTrace();
            return customHttpResponseBuilder.status(500).message("ERROR_EXECUTING_API" + e2).build();
        }
    }

    private T converter(final CloseableHttpResponse closeableHttpResponse) {
        try {
            if (closeableHttpResponse.getEntity() == null) {
                return null;
            }

            String contentType = this.getContentType(closeableHttpResponse);
            if (contentType.contains("text/html")) {
                Document document = Jsoup.parse(closeableHttpResponse.getEntity().getContent(), "UTF-8", "http://test.com");
                return (T) document;
            }

            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
                return (T) mapper.readValue(closeableHttpResponse.getEntity().getContent(), (Class<?>) this.tClass);
            } catch (Exception e) {
                HttpExec.logger.error("Error occurred in converting response body", e);
                return null;
            }
        } catch (IOException ex) {
            // IOException can occur on getContent() calls
            throw new RuntimeException("Error reading HTTP response content", ex);
        }
    }
    
    private String getContentType(final CloseableHttpResponse closeableHttpResponse) {
        final String header_name = "Content-Type";
        if (null != closeableHttpResponse.getEntity().getContentType()) {
            final Header ct = closeableHttpResponse.getEntity().getContentType();
            return ct.getValue();
        }
        final Header ct = closeableHttpResponse.getFirstHeader(header_name);
        return ct.getValue();
    }
    
    static {
        logger = LoggerFactory.getLogger(HttpExec.class);
    }
}
