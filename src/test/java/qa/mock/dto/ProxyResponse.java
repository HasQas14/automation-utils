// 

// 

package qa.mock.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProxyResponse
{
    private Request request;
    @Deprecated
    private Response response;
    private long timestamp;
    
    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        try {
            return mapper.writeValueAsString((Object)this);
        }
        catch (final JsonProcessingException ex) {
            return "ProxyResponse{request=" + this.request + ", response=" + this.response + ", timestamp=" + this.timestamp + '}';
        }
    }
    
    public Request getRequest() {
        return this.request;
    }
    
    @Deprecated
    public Response getResponse() {
        return this.response;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public void setRequest(final Request request) {
        this.request = request;
    }
    
    @Deprecated
    public void setResponse(final Response response) {
        this.response = response;
    }
    
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ProxyResponse)) {
            return false;
        }
        final ProxyResponse other = (ProxyResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getTimestamp() != other.getTimestamp()) {
            return false;
        }
        final Object this$request = this.getRequest();
        final Object other$request = other.getRequest();
        Label_0079: {
            if (this$request == null) {
                if (other$request == null) {
                    break Label_0079;
                }
            }
            else if (this$request.equals(other$request)) {
                break Label_0079;
            }
            return false;
        }
        final Object this$response = this.getResponse();
        final Object other$response = other.getResponse();
        if (this$response == null) {
            if (other$response == null) {
                return true;
            }
        }
        else if (this$response.equals(other$response)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ProxyResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $timestamp = this.getTimestamp();
        result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
        final Object $request = this.getRequest();
        result = result * 59 + (($request == null) ? 43 : $request.hashCode());
        final Object $response = this.getResponse();
        result = result * 59 + (($response == null) ? 43 : $response.hashCode());
        return result;
    }
}
