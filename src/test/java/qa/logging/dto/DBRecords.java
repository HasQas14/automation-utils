package qa.logging.dto;

import java.util.LinkedList;

public class DBRecords
{
    private int result_size;
    private boolean isSuccess;
    private LinkedList<DBRecord> dbRecords;
    
    DBRecords(final int result_size, final boolean isSuccess, final LinkedList<DBRecord> dbRecords) {
        this.result_size = result_size;
        this.isSuccess = isSuccess;
        this.dbRecords = dbRecords;
    }
    
    public static DBRecordsBuilder builder() {
        return new DBRecordsBuilder();
    }
    
    public int getResult_size() {
        return this.result_size;
    }
    
    public boolean isSuccess() {
        return this.isSuccess;
    }
    
    public LinkedList<DBRecord> getDbRecords() {
        return this.dbRecords;
    }
    
    public void setResult_size(final int result_size) {
        this.result_size = result_size;
    }
    
    public void setSuccess(final boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    
    public void setDbRecords(final LinkedList<DBRecord> dbRecords) {
        this.dbRecords = dbRecords;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DBRecords)) {
            return false;
        }
        final DBRecords other = (DBRecords)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getResult_size() != other.getResult_size()) {
            return false;
        }
        if (this.isSuccess() != other.isSuccess()) {
            return false;
        }
        final Object this$dbRecords = this.getDbRecords();
        final Object other$dbRecords = other.getDbRecords();
        if (this$dbRecords == null) {
            if (other$dbRecords == null) {
                return true;
            }
        }
        else if (this$dbRecords.equals(other$dbRecords)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof DBRecords;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getResult_size();
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        final Object $dbRecords = this.getDbRecords();
        result = result * 59 + (($dbRecords == null) ? 43 : $dbRecords.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "DBRecords(result_size=" + this.getResult_size() + ", isSuccess=" + this.isSuccess() + ", dbRecords=" + this.getDbRecords() + ")";
    }
    
    public static class DBRecordsBuilder
    {
        private int result_size;
        private boolean isSuccess;
        private LinkedList<DBRecord> dbRecords;
        
        DBRecordsBuilder() {
        }
        
        public DBRecordsBuilder result_size(final int result_size) {
            this.result_size = result_size;
            return this;
        }
        
        public DBRecordsBuilder isSuccess(final boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }
        
        public DBRecordsBuilder dbRecords(final LinkedList<DBRecord> dbRecords) {
            this.dbRecords = dbRecords;
            return this;
        }
        
        public DBRecords build() {
            return new DBRecords(this.result_size, this.isSuccess, this.dbRecords);
        }
        
        @Override
        public String toString() {
            return "DBRecords.DBRecordsBuilder(result_size=" + this.result_size + ", isSuccess=" + this.isSuccess + ", dbRecords=" + this.dbRecords + ")";
        }
    }
}
