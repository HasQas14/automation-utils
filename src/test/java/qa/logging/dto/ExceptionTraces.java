// 

// 

package qa.logging.dto;

public class ExceptionTraces
{
    private String logContent;
    private String logStackId;
    private String traceId;
    private String createdTime;
    private String ip;
    private String appName;
    
    public String getLogContent() {
        return this.logContent;
    }
    
    public String getLogStackId() {
        return this.logStackId;
    }
    
    public String getTraceId() {
        return this.traceId;
    }
    
    public String getCreatedTime() {
        return this.createdTime;
    }
    
    public String getIp() {
        return this.ip;
    }
    
    public String getAppName() {
        return this.appName;
    }
    
    public void setLogContent(final String logContent) {
        this.logContent = logContent;
    }
    
    public void setLogStackId(final String logStackId) {
        this.logStackId = logStackId;
    }
    
    public void setTraceId(final String traceId) {
        this.traceId = traceId;
    }
    
    public void setCreatedTime(final String createdTime) {
        this.createdTime = createdTime;
    }
    
    public void setIp(final String ip) {
        this.ip = ip;
    }
    
    public void setAppName(final String appName) {
        this.appName = appName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExceptionTraces)) {
            return false;
        }
        final ExceptionTraces other = (ExceptionTraces)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$logContent = this.getLogContent();
        final Object other$logContent = other.getLogContent();
        Label_0065: {
            if (this$logContent == null) {
                if (other$logContent == null) {
                    break Label_0065;
                }
            }
            else if (this$logContent.equals(other$logContent)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$logStackId = this.getLogStackId();
        final Object other$logStackId = other.getLogStackId();
        Label_0102: {
            if (this$logStackId == null) {
                if (other$logStackId == null) {
                    break Label_0102;
                }
            }
            else if (this$logStackId.equals(other$logStackId)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$traceId = this.getTraceId();
        final Object other$traceId = other.getTraceId();
        Label_0139: {
            if (this$traceId == null) {
                if (other$traceId == null) {
                    break Label_0139;
                }
            }
            else if (this$traceId.equals(other$traceId)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$createdTime = this.getCreatedTime();
        final Object other$createdTime = other.getCreatedTime();
        Label_0176: {
            if (this$createdTime == null) {
                if (other$createdTime == null) {
                    break Label_0176;
                }
            }
            else if (this$createdTime.equals(other$createdTime)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$ip = this.getIp();
        final Object other$ip = other.getIp();
        Label_0213: {
            if (this$ip == null) {
                if (other$ip == null) {
                    break Label_0213;
                }
            }
            else if (this$ip.equals(other$ip)) {
                break Label_0213;
            }
            return false;
        }
        final Object this$appName = this.getAppName();
        final Object other$appName = other.getAppName();
        if (this$appName == null) {
            if (other$appName == null) {
                return true;
            }
        }
        else if (this$appName.equals(other$appName)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ExceptionTraces;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $logContent = this.getLogContent();
        result = result * 59 + (($logContent == null) ? 43 : $logContent.hashCode());
        final Object $logStackId = this.getLogStackId();
        result = result * 59 + (($logStackId == null) ? 43 : $logStackId.hashCode());
        final Object $traceId = this.getTraceId();
        result = result * 59 + (($traceId == null) ? 43 : $traceId.hashCode());
        final Object $createdTime = this.getCreatedTime();
        result = result * 59 + (($createdTime == null) ? 43 : $createdTime.hashCode());
        final Object $ip = this.getIp();
        result = result * 59 + (($ip == null) ? 43 : $ip.hashCode());
        final Object $appName = this.getAppName();
        result = result * 59 + (($appName == null) ? 43 : $appName.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "ExceptionTraces(logContent=" + this.getLogContent() + ", logStackId=" + this.getLogStackId() + ", traceId=" + this.getTraceId() + ", createdTime=" + this.getCreatedTime() + ", ip=" + this.getIp() + ", appName=" + this.getAppName() + ")";
    }
}
