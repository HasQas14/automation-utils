// 

// 

package qa.utilities.logAdapter.dto.response;

public class MessageWrapper
{
    private String message;
    private String log_json;
    private String timestamp;
    
    public String getMessage() {
        return this.message;
    }
    
    public String getLog_json() {
        return this.log_json;
    }
    
    public String getTimestamp() {
        return this.timestamp;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public void setLog_json(final String log_json) {
        this.log_json = log_json;
    }
    
    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MessageWrapper)) {
            return false;
        }
        final MessageWrapper other = (MessageWrapper)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        Label_0065: {
            if (this$message == null) {
                if (other$message == null) {
                    break Label_0065;
                }
            }
            else if (this$message.equals(other$message)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$log_json = this.getLog_json();
        final Object other$log_json = other.getLog_json();
        Label_0102: {
            if (this$log_json == null) {
                if (other$log_json == null) {
                    break Label_0102;
                }
            }
            else if (this$log_json.equals(other$log_json)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null) {
            if (other$timestamp == null) {
                return true;
            }
        }
        else if (this$timestamp.equals(other$timestamp)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof MessageWrapper;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $message = this.getMessage();
        result = result * 59 + (($message == null) ? 43 : $message.hashCode());
        final Object $log_json = this.getLog_json();
        result = result * 59 + (($log_json == null) ? 43 : $log_json.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * 59 + (($timestamp == null) ? 43 : $timestamp.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "MessageWrapper(message=" + this.getMessage() + ", log_json=" + this.getLog_json() + ", timestamp=" + this.getTimestamp() + ")";
    }
}
