// 

// 

package qa.utilities.logAdapter.adapter.impl;

public class AdapterConfig
{
    private String host;
    private int port;
    private int fromPoint;
    private int recordSize;
    private String timeStampFieldName;
    
    private static int $default$recordSize() {
        return 100;
    }
    
    private static String $default$timeStampFieldName() {
        return "@timestamp";
    }
    
    public static AdapterConfigBuilder builder() {
        return new AdapterConfigBuilder();
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public int getFromPoint() {
        return this.fromPoint;
    }
    
    public int getRecordSize() {
        return this.recordSize;
    }
    
    public String getTimeStampFieldName() {
        return this.timeStampFieldName;
    }
    
    public void setHost(final String host) {
        this.host = host;
    }
    
    public void setPort(final int port) {
        this.port = port;
    }
    
    public void setFromPoint(final int fromPoint) {
        this.fromPoint = fromPoint;
    }
    
    public void setRecordSize(final int recordSize) {
        this.recordSize = recordSize;
    }
    
    public void setTimeStampFieldName(final String timeStampFieldName) {
        this.timeStampFieldName = timeStampFieldName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AdapterConfig)) {
            return false;
        }
        final AdapterConfig other = (AdapterConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getPort() != other.getPort()) {
            return false;
        }
        if (this.getFromPoint() != other.getFromPoint()) {
            return false;
        }
        if (this.getRecordSize() != other.getRecordSize()) {
            return false;
        }
        final Object this$host = this.getHost();
        final Object other$host = other.getHost();
        Label_0104: {
            if (this$host == null) {
                if (other$host == null) {
                    break Label_0104;
                }
            }
            else if (this$host.equals(other$host)) {
                break Label_0104;
            }
            return false;
        }
        final Object this$timeStampFieldName = this.getTimeStampFieldName();
        final Object other$timeStampFieldName = other.getTimeStampFieldName();
        if (this$timeStampFieldName == null) {
            if (other$timeStampFieldName == null) {
                return true;
            }
        }
        else if (this$timeStampFieldName.equals(other$timeStampFieldName)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof AdapterConfig;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getPort();
        result = result * 59 + this.getFromPoint();
        result = result * 59 + this.getRecordSize();
        final Object $host = this.getHost();
        result = result * 59 + (($host == null) ? 43 : $host.hashCode());
        final Object $timeStampFieldName = this.getTimeStampFieldName();
        result = result * 59 + (($timeStampFieldName == null) ? 43 : $timeStampFieldName.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "AdapterConfig(host=" + this.getHost() + ", port=" + this.getPort() + ", fromPoint=" + this.getFromPoint() + ", recordSize=" + this.getRecordSize() + ", timeStampFieldName=" + this.getTimeStampFieldName() + ")";
    }
    
    public AdapterConfig() {
        this.fromPoint = 0;
        this.recordSize = $default$recordSize();
        this.timeStampFieldName = $default$timeStampFieldName();
    }
    
    public AdapterConfig(final String host, final int port, final int fromPoint, final int recordSize, final String timeStampFieldName) {
        this.fromPoint = 0;
        this.host = host;
        this.port = port;
        this.fromPoint = fromPoint;
        this.recordSize = recordSize;
        this.timeStampFieldName = timeStampFieldName;
    }
    
    public static class AdapterConfigBuilder
    {
        private String host;
        private int port;
        private int fromPoint;
        private boolean recordSize$set;
        private int recordSize$value;
        private boolean timeStampFieldName$set;
        private String timeStampFieldName$value;
        
        AdapterConfigBuilder() {
        }
        
        public AdapterConfigBuilder host(final String host) {
            this.host = host;
            return this;
        }
        
        public AdapterConfigBuilder port(final int port) {
            this.port = port;
            return this;
        }
        
        public AdapterConfigBuilder fromPoint(final int fromPoint) {
            this.fromPoint = fromPoint;
            return this;
        }
        
        public AdapterConfigBuilder recordSize(final int recordSize) {
            this.recordSize$value = recordSize;
            this.recordSize$set = true;
            return this;
        }
        
        public AdapterConfigBuilder timeStampFieldName(final String timeStampFieldName) {
            this.timeStampFieldName$value = timeStampFieldName;
            this.timeStampFieldName$set = true;
            return this;
        }
        
        public AdapterConfig build() {
            int recordSize$value = this.recordSize$value;
            if (!this.recordSize$set) {
                recordSize$value = $default$recordSize();
            }
            String timeStampFieldName$value = this.timeStampFieldName$value;
            if (!this.timeStampFieldName$set) {
                timeStampFieldName$value = $default$timeStampFieldName();
            }
            return new AdapterConfig(this.host, this.port, this.fromPoint, recordSize$value, timeStampFieldName$value);
        }
        
        @Override
        public String toString() {
            return "AdapterConfig.AdapterConfigBuilder(host=" + this.host + ", port=" + this.port + ", fromPoint=" + this.fromPoint + ", recordSize$value=" + this.recordSize$value + ", timeStampFieldName$value=" + this.timeStampFieldName$value + ")";
        }
    }
}
