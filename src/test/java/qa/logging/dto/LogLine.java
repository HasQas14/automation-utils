// 

// 

package qa.logging.dto;

public class LogLine
{
    private String log;
    private long timestamp;
    
    LogLine(final String log, final long timestamp) {
        this.log = log;
        this.timestamp = timestamp;
    }
    
    public static LogLineBuilder builder() {
        return new LogLineBuilder();
    }
    
    public String getLog() {
        return this.log;
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public void setLog(final String log) {
        this.log = log;
    }
    
    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogLine)) {
            return false;
        }
        final LogLine other = (LogLine)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getTimestamp() != other.getTimestamp()) {
            return false;
        }
        final Object this$log = this.getLog();
        final Object other$log = other.getLog();
        if (this$log == null) {
            if (other$log == null) {
                return true;
            }
        }
        else if (this$log.equals(other$log)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof LogLine;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $timestamp = this.getTimestamp();
        result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
        final Object $log = this.getLog();
        result = result * 59 + (($log == null) ? 43 : $log.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "LogLine(log=" + this.getLog() + ", timestamp=" + this.getTimestamp() + ")";
    }
    
    public static class LogLineBuilder
    {
        private String log;
        private long timestamp;
        
        LogLineBuilder() {
        }
        
        public LogLineBuilder log(final String log) {
            this.log = log;
            return this;
        }
        
        public LogLineBuilder timestamp(final long timestamp) {
            this.timestamp = timestamp;
            return this;
        }
        
        public LogLine build() {
            return new LogLine(this.log, this.timestamp);
        }
        
        @Override
        public String toString() {
            return "LogLine.LogLineBuilder(log=" + this.log + ", timestamp=" + this.timestamp + ")";
        }
    }
}
