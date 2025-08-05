// 

// 

package qa.logging.dto;

import java.util.LinkedList;

public class LogFileResponse
{
    private String file_name;
    private LinkedList<LogLine> logLines;
    
    LogFileResponse(final String file_name, final LinkedList<LogLine> logLines) {
        this.logLines = new LinkedList<LogLine>();
        this.file_name = file_name;
        this.logLines = logLines;
    }
    
    public static LogFileResponseBuilder builder() {
        return new LogFileResponseBuilder();
    }
    
    public String getFile_name() {
        return this.file_name;
    }
    
    public LinkedList<LogLine> getLogLines() {
        return this.logLines;
    }
    
    public void setFile_name(final String file_name) {
        this.file_name = file_name;
    }
    
    public void setLogLines(final LinkedList<LogLine> logLines) {
        this.logLines = logLines;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogFileResponse)) {
            return false;
        }
        final LogFileResponse other = (LogFileResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$file_name = this.getFile_name();
        final Object other$file_name = other.getFile_name();
        Label_0065: {
            if (this$file_name == null) {
                if (other$file_name == null) {
                    break Label_0065;
                }
            }
            else if (this$file_name.equals(other$file_name)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$logLines = this.getLogLines();
        final Object other$logLines = other.getLogLines();
        if (this$logLines == null) {
            if (other$logLines == null) {
                return true;
            }
        }
        else if (this$logLines.equals(other$logLines)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof LogFileResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $file_name = this.getFile_name();
        result = result * 59 + (($file_name == null) ? 43 : $file_name.hashCode());
        final Object $logLines = this.getLogLines();
        result = result * 59 + (($logLines == null) ? 43 : $logLines.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "LogFileResponse(file_name=" + this.getFile_name() + ", logLines=" + this.getLogLines() + ")";
    }
    
    public static class LogFileResponseBuilder
    {
        private String file_name;
        private LinkedList<LogLine> logLines;
        
        LogFileResponseBuilder() {
        }
        
        public LogFileResponseBuilder file_name(final String file_name) {
            this.file_name = file_name;
            return this;
        }
        
        public LogFileResponseBuilder logLines(final LinkedList<LogLine> logLines) {
            this.logLines = logLines;
            return this;
        }
        
        public LogFileResponse build() {
            return new LogFileResponse(this.file_name, this.logLines);
        }
        
        @Override
        public String toString() {
            return "LogFileResponse.LogFileResponseBuilder(file_name=" + this.file_name + ", logLines=" + this.logLines + ")";
        }
    }
}
