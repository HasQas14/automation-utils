package qa.logging.dto;

public class DBRecord
{
    private long createdTime;
    private String executeResult;
    private String sqlCode;
    private String sqlType;
    private String tableName;
    private String traceId;
    private String zdalDbDigestId;
    
    public long getCreatedTime() {
        return this.createdTime;
    }
    
    public String getExecuteResult() {
        return this.executeResult;
    }
    
    public String getSqlCode() {
        return this.sqlCode;
    }
    
    public String getSqlType() {
        return this.sqlType;
    }
    
    public String getTableName() {
        return this.tableName;
    }
    
    public String getTraceId() {
        return this.traceId;
    }
    
    public String getZdalDbDigestId() {
        return this.zdalDbDigestId;
    }
    
    public void setCreatedTime(final long createdTime) {
        this.createdTime = createdTime;
    }
    
    public void setExecuteResult(final String executeResult) {
        this.executeResult = executeResult;
    }
    
    public void setSqlCode(final String sqlCode) {
        this.sqlCode = sqlCode;
    }
    
    public void setSqlType(final String sqlType) {
        this.sqlType = sqlType;
    }
    
    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }
    
    public void setTraceId(final String traceId) {
        this.traceId = traceId;
    }
    
    public void setZdalDbDigestId(final String zdalDbDigestId) {
        this.zdalDbDigestId = zdalDbDigestId;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DBRecord)) {
            return false;
        }
        final DBRecord other = (DBRecord)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getCreatedTime() != other.getCreatedTime()) {
            return false;
        }
        final Object this$executeResult = this.getExecuteResult();
        final Object other$executeResult = other.getExecuteResult();
        Label_0079: {
            if (this$executeResult == null) {
                if (other$executeResult == null) {
                    break Label_0079;
                }
            }
            else if (this$executeResult.equals(other$executeResult)) {
                break Label_0079;
            }
            return false;
        }
        final Object this$sqlCode = this.getSqlCode();
        final Object other$sqlCode = other.getSqlCode();
        Label_0116: {
            if (this$sqlCode == null) {
                if (other$sqlCode == null) {
                    break Label_0116;
                }
            }
            else if (this$sqlCode.equals(other$sqlCode)) {
                break Label_0116;
            }
            return false;
        }
        final Object this$sqlType = this.getSqlType();
        final Object other$sqlType = other.getSqlType();
        Label_0153: {
            if (this$sqlType == null) {
                if (other$sqlType == null) {
                    break Label_0153;
                }
            }
            else if (this$sqlType.equals(other$sqlType)) {
                break Label_0153;
            }
            return false;
        }
        final Object this$tableName = this.getTableName();
        final Object other$tableName = other.getTableName();
        Label_0190: {
            if (this$tableName == null) {
                if (other$tableName == null) {
                    break Label_0190;
                }
            }
            else if (this$tableName.equals(other$tableName)) {
                break Label_0190;
            }
            return false;
        }
        final Object this$traceId = this.getTraceId();
        final Object other$traceId = other.getTraceId();
        Label_0227: {
            if (this$traceId == null) {
                if (other$traceId == null) {
                    break Label_0227;
                }
            }
            else if (this$traceId.equals(other$traceId)) {
                break Label_0227;
            }
            return false;
        }
        final Object this$zdalDbDigestId = this.getZdalDbDigestId();
        final Object other$zdalDbDigestId = other.getZdalDbDigestId();
        if (this$zdalDbDigestId == null) {
            if (other$zdalDbDigestId == null) {
                return true;
            }
        }
        else if (this$zdalDbDigestId.equals(other$zdalDbDigestId)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof DBRecord;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $createdTime = this.getCreatedTime();
        result = result * 59 + (int)($createdTime >>> 32 ^ $createdTime);
        final Object $executeResult = this.getExecuteResult();
        result = result * 59 + (($executeResult == null) ? 43 : $executeResult.hashCode());
        final Object $sqlCode = this.getSqlCode();
        result = result * 59 + (($sqlCode == null) ? 43 : $sqlCode.hashCode());
        final Object $sqlType = this.getSqlType();
        result = result * 59 + (($sqlType == null) ? 43 : $sqlType.hashCode());
        final Object $tableName = this.getTableName();
        result = result * 59 + (($tableName == null) ? 43 : $tableName.hashCode());
        final Object $traceId = this.getTraceId();
        result = result * 59 + (($traceId == null) ? 43 : $traceId.hashCode());
        final Object $zdalDbDigestId = this.getZdalDbDigestId();
        result = result * 59 + (($zdalDbDigestId == null) ? 43 : $zdalDbDigestId.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "DBRecord(createdTime=" + this.getCreatedTime() + ", executeResult=" + this.getExecuteResult() + ", sqlCode=" + this.getSqlCode() + ", sqlType=" + this.getSqlType() + ", tableName=" + this.getTableName() + ", traceId=" + this.getTraceId() + ", zdalDbDigestId=" + this.getZdalDbDigestId() + ")";
    }
}
