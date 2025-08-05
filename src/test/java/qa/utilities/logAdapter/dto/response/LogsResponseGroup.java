// 

// 

package qa.utilities.logAdapter.dto.response;

import java.util.List;

public class LogsResponseGroup
{
    private List<MessageWrapper> messageWrappers;
    
    public List<MessageWrapper> getMessageWrappers() {
        return this.messageWrappers;
    }
    
    public void setMessageWrappers(final List<MessageWrapper> messageWrappers) {
        this.messageWrappers = messageWrappers;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogsResponseGroup)) {
            return false;
        }
        final LogsResponseGroup other = (LogsResponseGroup)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$messageWrappers = this.getMessageWrappers();
        final Object other$messageWrappers = other.getMessageWrappers();
        if (this$messageWrappers == null) {
            if (other$messageWrappers == null) {
                return true;
            }
        }
        else if (this$messageWrappers.equals(other$messageWrappers)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof LogsResponseGroup;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $messageWrappers = this.getMessageWrappers();
        result = result * 59 + (($messageWrappers == null) ? 43 : $messageWrappers.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "LogsResponseGroup(messageWrappers=" + this.getMessageWrappers() + ")";
    }
}
