

package qa.generic.api;

import java.util.HashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class CustomHttpResponse<T>
{
    int status;
    Map<String, String> headers;
    String message;
    T body;
    
    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        try {
            return mapper.writeValueAsString((Object)this);
        }
        catch (final JsonProcessingException ex) {
            return "CustomHttpResponse{status=" + this.status + ", headers=" + this.headers + ", message='" + this.message + '\'' + ", body=" + this.body + '}';
        }
    }
    
    CustomHttpResponse(final int status, final Map<String, String> headers, final String message, final T body) {
        this.headers = new HashMap<String, String>();
        this.status = status;
        this.headers = headers;
        this.message = message;
        this.body = body;
    }
    
    public static <T> CustomHttpResponseBuilder<T> builder() {
        return new CustomHttpResponseBuilder<T>();
    }

    public int getStatus() {
        return this.status;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getMessage() {
        return this.message;
    }

    public T getBody() {
        return this.body;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setBody(final T body) {
        this.body = body;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CustomHttpResponse)) {
            return false;
        }
        final CustomHttpResponse<?> other = (CustomHttpResponse<?>)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getStatus() != other.getStatus()) {
            return false;
        }
        final Object this$headers = this.getHeaders();
        final Object other$headers = other.getHeaders();
        Label_0078: {
            if (this$headers == null) {
                if (other$headers == null) {
                    break Label_0078;
                }
            }
            else if (this$headers.equals(other$headers)) {
                break Label_0078;
            }
            return false;
        }
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        Label_0115: {
            if (this$message == null) {
                if (other$message == null) {
                    break Label_0115;
                }
            }
            else if (this$message.equals(other$message)) {
                break Label_0115;
            }
            return false;
        }
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null) {
            if (other$body == null) {
                return true;
            }
        }
        else if (this$body.equals(other$body)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof CustomHttpResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getStatus();
        final Object $headers = this.getHeaders();
        result = result * 59 + (($headers == null) ? 43 : $headers.hashCode());
        final Object $message = this.getMessage();
        result = result * 59 + (($message == null) ? 43 : $message.hashCode());
        final Object $body = this.getBody();
        result = result * 59 + (($body == null) ? 43 : $body.hashCode());
        return result;
    }
    
    public static class CustomHttpResponseBuilder<T>
    {
        private int status;
        private Map<String, String> headers;
        private String message;
        private T body;
        
        CustomHttpResponseBuilder() {
        }
        
        public CustomHttpResponseBuilder<T> status(final int status) {
            this.status = status;
            return this;
        }
        
        public CustomHttpResponseBuilder<T> headers(final Map<String, String> headers) {
            this.headers = headers;
            return this;
        }
        
        public CustomHttpResponseBuilder<T> message(final String message) {
            this.message = message;
            return this;
        }
        
        public CustomHttpResponseBuilder<T> body(final T body) {
            this.body = body;
            return this;
        }
        
        public CustomHttpResponse<T> build() {
            return new CustomHttpResponse<T>(this.status, this.headers, this.message, this.body);
        }
        
        @Override
        public String toString() {
            return "CustomHttpResponse.CustomHttpResponseBuilder(status=" + this.status + ", headers=" + this.headers + ", message=" + this.message + ", body=" + this.body + ")";
        }
    }
}
