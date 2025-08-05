// 

// 

package qa.mock.dto;

import java.util.Date;
import java.util.Map;

public class Request
{
    private String url;
    private Map headers;
    private Object requestBody;
    private Map queryParameter;
    private Date loggedDate;
    
    Request(final String url, final Map headers, final Object requestBody, final Map queryParameter, final Date loggedDate) {
        this.url = url;
        this.headers = headers;
        this.requestBody = requestBody;
        this.queryParameter = queryParameter;
        this.loggedDate = loggedDate;
    }
    
    public static RequestBuilder builder() {
        return new RequestBuilder();
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public Map getHeaders() {
        return this.headers;
    }
    
    public Object getRequestBody() {
        return this.requestBody;
    }
    
    public Map getQueryParameter() {
        return this.queryParameter;
    }
    
    public Date getLoggedDate() {
        return this.loggedDate;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public void setHeaders(final Map headers) {
        this.headers = headers;
    }
    
    public void setRequestBody(final Object requestBody) {
        this.requestBody = requestBody;
    }
    
    public void setQueryParameter(final Map queryParameter) {
        this.queryParameter = queryParameter;
    }
    
    public void setLoggedDate(final Date loggedDate) {
        this.loggedDate = loggedDate;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        final Request other = (Request)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        Label_0065: {
            if (this$url == null) {
                if (other$url == null) {
                    break Label_0065;
                }
            }
            else if (this$url.equals(other$url)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$headers = this.getHeaders();
        final Object other$headers = other.getHeaders();
        Label_0102: {
            if (this$headers == null) {
                if (other$headers == null) {
                    break Label_0102;
                }
            }
            else if (this$headers.equals(other$headers)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$requestBody = this.getRequestBody();
        final Object other$requestBody = other.getRequestBody();
        Label_0139: {
            if (this$requestBody == null) {
                if (other$requestBody == null) {
                    break Label_0139;
                }
            }
            else if (this$requestBody.equals(other$requestBody)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$queryParameter = this.getQueryParameter();
        final Object other$queryParameter = other.getQueryParameter();
        Label_0176: {
            if (this$queryParameter == null) {
                if (other$queryParameter == null) {
                    break Label_0176;
                }
            }
            else if (this$queryParameter.equals(other$queryParameter)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$loggedDate = this.getLoggedDate();
        final Object other$loggedDate = other.getLoggedDate();
        if (this$loggedDate == null) {
            if (other$loggedDate == null) {
                return true;
            }
        }
        else if (this$loggedDate.equals(other$loggedDate)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof Request;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $url = this.getUrl();
        result = result * 59 + (($url == null) ? 43 : $url.hashCode());
        final Object $headers = this.getHeaders();
        result = result * 59 + (($headers == null) ? 43 : $headers.hashCode());
        final Object $requestBody = this.getRequestBody();
        result = result * 59 + (($requestBody == null) ? 43 : $requestBody.hashCode());
        final Object $queryParameter = this.getQueryParameter();
        result = result * 59 + (($queryParameter == null) ? 43 : $queryParameter.hashCode());
        final Object $loggedDate = this.getLoggedDate();
        result = result * 59 + (($loggedDate == null) ? 43 : $loggedDate.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "Request(url=" + this.getUrl() + ", headers=" + this.getHeaders() + ", requestBody=" + this.getRequestBody() + ", queryParameter=" + this.getQueryParameter() + ", loggedDate=" + this.getLoggedDate() + ")";
    }
    
    public static class RequestBuilder
    {
        private String url;
        private Map headers;
        private Object requestBody;
        private Map queryParameter;
        private Date loggedDate;
        
        RequestBuilder() {
        }
        
        public RequestBuilder url(final String url) {
            this.url = url;
            return this;
        }
        
        public RequestBuilder headers(final Map headers) {
            this.headers = headers;
            return this;
        }
        
        public RequestBuilder requestBody(final Object requestBody) {
            this.requestBody = requestBody;
            return this;
        }
        
        public RequestBuilder queryParameter(final Map queryParameter) {
            this.queryParameter = queryParameter;
            return this;
        }
        
        public RequestBuilder loggedDate(final Date loggedDate) {
            this.loggedDate = loggedDate;
            return this;
        }
        
        public Request build() {
            return new Request(this.url, this.headers, this.requestBody, this.queryParameter, this.loggedDate);
        }
        
        @Override
        public String toString() {
            return "Request.RequestBuilder(url=" + this.url + ", headers=" + this.headers + ", requestBody=" + this.requestBody + ", queryParameter=" + this.queryParameter + ", loggedDate=" + this.loggedDate + ")";
        }
    }
}
