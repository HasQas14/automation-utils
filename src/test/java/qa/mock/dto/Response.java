// 

// 

package qa.mock.dto;

import java.util.Map;

public class Response
{
    private int statusCode;
    private Object responseBody;
    private Map headers;
    
    Response(final int statusCode, final Object responseBody, final Map headers) {
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.headers = headers;
    }
    
    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }
    
    public int getStatusCode() {
        return this.statusCode;
    }
    
    public Object getResponseBody() {
        return this.responseBody;
    }
    
    public Map getHeaders() {
        return this.headers;
    }
    
    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }
    
    public void setResponseBody(final Object responseBody) {
        this.responseBody = responseBody;
    }
    
    public void setHeaders(final Map headers) {
        this.headers = headers;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Response)) {
            return false;
        }
        final Response other = (Response)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getStatusCode() != other.getStatusCode()) {
            return false;
        }
        final Object this$responseBody = this.getResponseBody();
        final Object other$responseBody = other.getResponseBody();
        Label_0078: {
            if (this$responseBody == null) {
                if (other$responseBody == null) {
                    break Label_0078;
                }
            }
            else if (this$responseBody.equals(other$responseBody)) {
                break Label_0078;
            }
            return false;
        }
        final Object this$headers = this.getHeaders();
        final Object other$headers = other.getHeaders();
        if (this$headers == null) {
            if (other$headers == null) {
                return true;
            }
        }
        else if (this$headers.equals(other$headers)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof Response;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStatusCode();
        final Object $responseBody = this.getResponseBody();
        result = result * 59 + (($responseBody == null) ? 43 : $responseBody.hashCode());
        final Object $headers = this.getHeaders();
        result = result * 59 + (($headers == null) ? 43 : $headers.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "Response(statusCode=" + this.getStatusCode() + ", responseBody=" + this.getResponseBody() + ", headers=" + this.getHeaders() + ")";
    }
    
    public static class ResponseBuilder
    {
        private int statusCode;
        private Object responseBody;
        private Map headers;
        
        ResponseBuilder() {
        }
        
        public ResponseBuilder statusCode(final int statusCode) {
            this.statusCode = statusCode;
            return this;
        }
        
        public ResponseBuilder responseBody(final Object responseBody) {
            this.responseBody = responseBody;
            return this;
        }
        
        public ResponseBuilder headers(final Map headers) {
            this.headers = headers;
            return this;
        }
        
        public Response build() {
            return new Response(this.statusCode, this.responseBody, this.headers);
        }
        
        @Override
        public String toString() {
            return "Response.ResponseBuilder(statusCode=" + this.statusCode + ", responseBody=" + this.responseBody + ", headers=" + this.headers + ")";
        }
    }
}
