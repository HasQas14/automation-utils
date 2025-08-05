// 

// 

package qa.mock.dto;

public class GrpcResponse
{
    private String body;
    private String status;
    private int response_code;
    
    public String getBody() {
        return this.body;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public int getResponse_code() {
        return this.response_code;
    }
    
    public void setBody(final String body) {
        this.body = body;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public void setResponse_code(final int response_code) {
        this.response_code = response_code;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GrpcResponse)) {
            return false;
        }
        final GrpcResponse other = (GrpcResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getResponse_code() != other.getResponse_code()) {
            return false;
        }
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        Label_0078: {
            if (this$body == null) {
                if (other$body == null) {
                    break Label_0078;
                }
            }
            else if (this$body.equals(other$body)) {
                break Label_0078;
            }
            return false;
        }
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null) {
            if (other$status == null) {
                return true;
            }
        }
        else if (this$status.equals(other$status)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof GrpcResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getResponse_code();
        final Object $body = this.getBody();
        result = result * 59 + (($body == null) ? 43 : $body.hashCode());
        final Object $status = this.getStatus();
        result = result * 59 + (($status == null) ? 43 : $status.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "GrpcResponse(body=" + this.getBody() + ", status=" + this.getStatus() + ", response_code=" + this.getResponse_code() + ")";
    }
}
