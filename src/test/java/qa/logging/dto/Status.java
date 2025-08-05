// 

// 

package qa.logging.dto;

public class Status
{
    private int RESPONSE_CODE;
    private String RESPONSE_MESSAGE;
    
    Status(final int RESPONSE_CODE, final String RESPONSE_MESSAGE) {
        this.RESPONSE_CODE = RESPONSE_CODE;
        this.RESPONSE_MESSAGE = RESPONSE_MESSAGE;
    }
    
    public static StatusBuilder builder() {
        return new StatusBuilder();
    }
    
    public int getRESPONSE_CODE() {
        return this.RESPONSE_CODE;
    }
    
    public String getRESPONSE_MESSAGE() {
        return this.RESPONSE_MESSAGE;
    }
    
    public void setRESPONSE_CODE(final int RESPONSE_CODE) {
        this.RESPONSE_CODE = RESPONSE_CODE;
    }
    
    public void setRESPONSE_MESSAGE(final String RESPONSE_MESSAGE) {
        this.RESPONSE_MESSAGE = RESPONSE_MESSAGE;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        final Status other = (Status)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getRESPONSE_CODE() != other.getRESPONSE_CODE()) {
            return false;
        }
        final Object this$RESPONSE_MESSAGE = this.getRESPONSE_MESSAGE();
        final Object other$RESPONSE_MESSAGE = other.getRESPONSE_MESSAGE();
        if (this$RESPONSE_MESSAGE == null) {
            if (other$RESPONSE_MESSAGE == null) {
                return true;
            }
        }
        else if (this$RESPONSE_MESSAGE.equals(other$RESPONSE_MESSAGE)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof Status;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getRESPONSE_CODE();
        final Object $RESPONSE_MESSAGE = this.getRESPONSE_MESSAGE();
        result = result * 59 + (($RESPONSE_MESSAGE == null) ? 43 : $RESPONSE_MESSAGE.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "Status(RESPONSE_CODE=" + this.getRESPONSE_CODE() + ", RESPONSE_MESSAGE=" + this.getRESPONSE_MESSAGE() + ")";
    }
    
    public static class StatusBuilder
    {
        private int RESPONSE_CODE;
        private String RESPONSE_MESSAGE;
        
        StatusBuilder() {
        }
        
        public StatusBuilder RESPONSE_CODE(final int RESPONSE_CODE) {
            this.RESPONSE_CODE = RESPONSE_CODE;
            return this;
        }
        
        public StatusBuilder RESPONSE_MESSAGE(final String RESPONSE_MESSAGE) {
            this.RESPONSE_MESSAGE = RESPONSE_MESSAGE;
            return this;
        }
        
        public Status build() {
            return new Status(this.RESPONSE_CODE, this.RESPONSE_MESSAGE);
        }
        
        @Override
        public String toString() {
            return "Status.StatusBuilder(RESPONSE_CODE=" + this.RESPONSE_CODE + ", RESPONSE_MESSAGE=" + this.RESPONSE_MESSAGE + ")";
        }
    }
}
