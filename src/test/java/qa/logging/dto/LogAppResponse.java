// 

// 

package qa.logging.dto;

import java.util.List;

public class LogAppResponse
{
    private List<LogFileResponse> logFileResponses;
    private String trace_id;
    private String app_name;
    private String server_address;
    private DBRecords dbRecords;
    private boolean isExceptionAvailable;
    private List<ExceptionTraces> exceptionTraces;
    private Status status;
    
    LogAppResponse(final List<LogFileResponse> logFileResponses, final String trace_id, final String app_name, final String server_address, final DBRecords dbRecords, final boolean isExceptionAvailable, final List<ExceptionTraces> exceptionTraces, final Status status) {
        this.logFileResponses = logFileResponses;
        this.trace_id = trace_id;
        this.app_name = app_name;
        this.server_address = server_address;
        this.dbRecords = dbRecords;
        this.isExceptionAvailable = isExceptionAvailable;
        this.exceptionTraces = exceptionTraces;
        this.status = status;
    }
    
    public static LogAppResponseBuilder builder() {
        return new LogAppResponseBuilder();
    }
    
    public List<LogFileResponse> getLogFileResponses() {
        return this.logFileResponses;
    }
    
    public String getTrace_id() {
        return this.trace_id;
    }
    
    public String getApp_name() {
        return this.app_name;
    }
    
    public String getServer_address() {
        return this.server_address;
    }
    
    public DBRecords getDbRecords() {
        return this.dbRecords;
    }
    
    public boolean isExceptionAvailable() {
        return this.isExceptionAvailable;
    }
    
    public List<ExceptionTraces> getExceptionTraces() {
        return this.exceptionTraces;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public void setLogFileResponses(final List<LogFileResponse> logFileResponses) {
        this.logFileResponses = logFileResponses;
    }
    
    public void setTrace_id(final String trace_id) {
        this.trace_id = trace_id;
    }
    
    public void setApp_name(final String app_name) {
        this.app_name = app_name;
    }
    
    public void setServer_address(final String server_address) {
        this.server_address = server_address;
    }
    
    public void setDbRecords(final DBRecords dbRecords) {
        this.dbRecords = dbRecords;
    }
    
    public void setExceptionAvailable(final boolean isExceptionAvailable) {
        this.isExceptionAvailable = isExceptionAvailable;
    }
    
    public void setExceptionTraces(final List<ExceptionTraces> exceptionTraces) {
        this.exceptionTraces = exceptionTraces;
    }
    
    public void setStatus(final Status status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogAppResponse)) {
            return false;
        }
        final LogAppResponse other = (LogAppResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.isExceptionAvailable() != other.isExceptionAvailable()) {
            return false;
        }
        final Object this$logFileResponses = this.getLogFileResponses();
        final Object other$logFileResponses = other.getLogFileResponses();
        Label_0078: {
            if (this$logFileResponses == null) {
                if (other$logFileResponses == null) {
                    break Label_0078;
                }
            }
            else if (this$logFileResponses.equals(other$logFileResponses)) {
                break Label_0078;
            }
            return false;
        }
        final Object this$trace_id = this.getTrace_id();
        final Object other$trace_id = other.getTrace_id();
        Label_0115: {
            if (this$trace_id == null) {
                if (other$trace_id == null) {
                    break Label_0115;
                }
            }
            else if (this$trace_id.equals(other$trace_id)) {
                break Label_0115;
            }
            return false;
        }
        final Object this$app_name = this.getApp_name();
        final Object other$app_name = other.getApp_name();
        Label_0152: {
            if (this$app_name == null) {
                if (other$app_name == null) {
                    break Label_0152;
                }
            }
            else if (this$app_name.equals(other$app_name)) {
                break Label_0152;
            }
            return false;
        }
        final Object this$server_address = this.getServer_address();
        final Object other$server_address = other.getServer_address();
        Label_0189: {
            if (this$server_address == null) {
                if (other$server_address == null) {
                    break Label_0189;
                }
            }
            else if (this$server_address.equals(other$server_address)) {
                break Label_0189;
            }
            return false;
        }
        final Object this$dbRecords = this.getDbRecords();
        final Object other$dbRecords = other.getDbRecords();
        Label_0226: {
            if (this$dbRecords == null) {
                if (other$dbRecords == null) {
                    break Label_0226;
                }
            }
            else if (this$dbRecords.equals(other$dbRecords)) {
                break Label_0226;
            }
            return false;
        }
        final Object this$exceptionTraces = this.getExceptionTraces();
        final Object other$exceptionTraces = other.getExceptionTraces();
        Label_0263: {
            if (this$exceptionTraces == null) {
                if (other$exceptionTraces == null) {
                    break Label_0263;
                }
            }
            else if (this$exceptionTraces.equals(other$exceptionTraces)) {
                break Label_0263;
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
        return other instanceof LogAppResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isExceptionAvailable() ? 79 : 97);
        final Object $logFileResponses = this.getLogFileResponses();
        result = result * 59 + (($logFileResponses == null) ? 43 : $logFileResponses.hashCode());
        final Object $trace_id = this.getTrace_id();
        result = result * 59 + (($trace_id == null) ? 43 : $trace_id.hashCode());
        final Object $app_name = this.getApp_name();
        result = result * 59 + (($app_name == null) ? 43 : $app_name.hashCode());
        final Object $server_address = this.getServer_address();
        result = result * 59 + (($server_address == null) ? 43 : $server_address.hashCode());
        final Object $dbRecords = this.getDbRecords();
        result = result * 59 + (($dbRecords == null) ? 43 : $dbRecords.hashCode());
        final Object $exceptionTraces = this.getExceptionTraces();
        result = result * 59 + (($exceptionTraces == null) ? 43 : $exceptionTraces.hashCode());
        final Object $status = this.getStatus();
        result = result * 59 + (($status == null) ? 43 : $status.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "LogAppResponse(logFileResponses=" + this.getLogFileResponses() + ", trace_id=" + this.getTrace_id() + ", app_name=" + this.getApp_name() + ", server_address=" + this.getServer_address() + ", dbRecords=" + this.getDbRecords() + ", isExceptionAvailable=" + this.isExceptionAvailable() + ", exceptionTraces=" + this.getExceptionTraces() + ", status=" + this.getStatus() + ")";
    }
    
    public static class LogAppResponseBuilder
    {
        private List<LogFileResponse> logFileResponses;
        private String trace_id;
        private String app_name;
        private String server_address;
        private DBRecords dbRecords;
        private boolean isExceptionAvailable;
        private List<ExceptionTraces> exceptionTraces;
        private Status status;
        
        LogAppResponseBuilder() {
        }
        
        public LogAppResponseBuilder logFileResponses(final List<LogFileResponse> logFileResponses) {
            this.logFileResponses = logFileResponses;
            return this;
        }
        
        public LogAppResponseBuilder trace_id(final String trace_id) {
            this.trace_id = trace_id;
            return this;
        }
        
        public LogAppResponseBuilder app_name(final String app_name) {
            this.app_name = app_name;
            return this;
        }
        
        public LogAppResponseBuilder server_address(final String server_address) {
            this.server_address = server_address;
            return this;
        }
        
        public LogAppResponseBuilder dbRecords(final DBRecords dbRecords) {
            this.dbRecords = dbRecords;
            return this;
        }
        
        public LogAppResponseBuilder isExceptionAvailable(final boolean isExceptionAvailable) {
            this.isExceptionAvailable = isExceptionAvailable;
            return this;
        }
        
        public LogAppResponseBuilder exceptionTraces(final List<ExceptionTraces> exceptionTraces) {
            this.exceptionTraces = exceptionTraces;
            return this;
        }
        
        public LogAppResponseBuilder status(final Status status) {
            this.status = status;
            return this;
        }
        
        public LogAppResponse build() {
            return new LogAppResponse(this.logFileResponses, this.trace_id, this.app_name, this.server_address, this.dbRecords, this.isExceptionAvailable, this.exceptionTraces, this.status);
        }
        
        @Override
        public String toString() {
            return "LogAppResponse.LogAppResponseBuilder(logFileResponses=" + this.logFileResponses + ", trace_id=" + this.trace_id + ", app_name=" + this.app_name + ", server_address=" + this.server_address + ", dbRecords=" + this.dbRecords + ", isExceptionAvailable=" + this.isExceptionAvailable + ", exceptionTraces=" + this.exceptionTraces + ", status=" + this.status + ")";
        }
    }
}
