// 

// 

package qa.msgBroker.response;

import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.Map;

public class MessageBrokerResponse
{
    private String topicName;
    private Map topicRecord;
    private Date createdTime;
    private String environmentName;
    private Map topicRecordValue;
    private Long rowId;
    private Map<String, Object> additionalProperties;
    
    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        try {
            return mapper.writeValueAsString((Object)this);
        }
        catch (final Exception ex) {
            return "KafkaResponse{topicName='" + this.topicName + '\'' + ", topicRecord=" + this.topicRecord + ", createdTime=" + this.createdTime + ", environmentName='" + this.environmentName + '\'' + ", topicRecordValue=" + this.topicRecordValue + ", rowId=" + this.rowId + ", additionalProperties=" + this.additionalProperties + '}';
        }
    }
    
    MessageBrokerResponse(final String topicName, final Map topicRecord, final Date createdTime, final String environmentName, final Map topicRecordValue, final Long rowId, final Map<String, Object> additionalProperties) {
        this.additionalProperties = new HashMap<String, Object>();
        this.topicName = topicName;
        this.topicRecord = topicRecord;
        this.createdTime = createdTime;
        this.environmentName = environmentName;
        this.topicRecordValue = topicRecordValue;
        this.rowId = rowId;
        this.additionalProperties = additionalProperties;
    }
    
    public static MessageBrokerResponseBuilder builder() {
        return new MessageBrokerResponseBuilder();
    }
    
    public MessageBrokerResponseBuilder toBuilder() {
        return new MessageBrokerResponseBuilder().topicName(this.topicName).topicRecord(this.topicRecord).createdTime(this.createdTime).environmentName(this.environmentName).topicRecordValue(this.topicRecordValue).rowId(this.rowId).additionalProperties(this.additionalProperties);
    }
    
    public String getTopicName() {
        return this.topicName;
    }
    
    public Map getTopicRecord() {
        return this.topicRecord;
    }
    
    public Date getCreatedTime() {
        return this.createdTime;
    }
    
    public String getEnvironmentName() {
        return this.environmentName;
    }
    
    public Map getTopicRecordValue() {
        return this.topicRecordValue;
    }
    
    public Long getRowId() {
        return this.rowId;
    }
    
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
    
    public void setTopicName(final String topicName) {
        this.topicName = topicName;
    }
    
    public void setTopicRecord(final Map topicRecord) {
        this.topicRecord = topicRecord;
    }
    
    public void setCreatedTime(final Date createdTime) {
        this.createdTime = createdTime;
    }
    
    public void setEnvironmentName(final String environmentName) {
        this.environmentName = environmentName;
    }
    
    public void setTopicRecordValue(final Map topicRecordValue) {
        this.topicRecordValue = topicRecordValue;
    }
    
    public void setRowId(final Long rowId) {
        this.rowId = rowId;
    }
    
    public void setAdditionalProperties(final Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MessageBrokerResponse)) {
            return false;
        }
        final MessageBrokerResponse other = (MessageBrokerResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$rowId = this.getRowId();
        final Object other$rowId = other.getRowId();
        Label_0065: {
            if (this$rowId == null) {
                if (other$rowId == null) {
                    break Label_0065;
                }
            }
            else if (this$rowId.equals(other$rowId)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$topicName = this.getTopicName();
        final Object other$topicName = other.getTopicName();
        Label_0102: {
            if (this$topicName == null) {
                if (other$topicName == null) {
                    break Label_0102;
                }
            }
            else if (this$topicName.equals(other$topicName)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$topicRecord = this.getTopicRecord();
        final Object other$topicRecord = other.getTopicRecord();
        Label_0139: {
            if (this$topicRecord == null) {
                if (other$topicRecord == null) {
                    break Label_0139;
                }
            }
            else if (this$topicRecord.equals(other$topicRecord)) {
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
        final Object this$environmentName = this.getEnvironmentName();
        final Object other$environmentName = other.getEnvironmentName();
        Label_0213: {
            if (this$environmentName == null) {
                if (other$environmentName == null) {
                    break Label_0213;
                }
            }
            else if (this$environmentName.equals(other$environmentName)) {
                break Label_0213;
            }
            return false;
        }
        final Object this$topicRecordValue = this.getTopicRecordValue();
        final Object other$topicRecordValue = other.getTopicRecordValue();
        Label_0250: {
            if (this$topicRecordValue == null) {
                if (other$topicRecordValue == null) {
                    break Label_0250;
                }
            }
            else if (this$topicRecordValue.equals(other$topicRecordValue)) {
                break Label_0250;
            }
            return false;
        }
        final Object this$additionalProperties = this.getAdditionalProperties();
        final Object other$additionalProperties = other.getAdditionalProperties();
        if (this$additionalProperties == null) {
            if (other$additionalProperties == null) {
                return true;
            }
        }
        else if (this$additionalProperties.equals(other$additionalProperties)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof MessageBrokerResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $rowId = this.getRowId();
        result = result * 59 + (($rowId == null) ? 43 : $rowId.hashCode());
        final Object $topicName = this.getTopicName();
        result = result * 59 + (($topicName == null) ? 43 : $topicName.hashCode());
        final Object $topicRecord = this.getTopicRecord();
        result = result * 59 + (($topicRecord == null) ? 43 : $topicRecord.hashCode());
        final Object $createdTime = this.getCreatedTime();
        result = result * 59 + (($createdTime == null) ? 43 : $createdTime.hashCode());
        final Object $environmentName = this.getEnvironmentName();
        result = result * 59 + (($environmentName == null) ? 43 : $environmentName.hashCode());
        final Object $topicRecordValue = this.getTopicRecordValue();
        result = result * 59 + (($topicRecordValue == null) ? 43 : $topicRecordValue.hashCode());
        final Object $additionalProperties = this.getAdditionalProperties();
        result = result * 59 + (($additionalProperties == null) ? 43 : $additionalProperties.hashCode());
        return result;
    }
    
    public static class MessageBrokerResponseBuilder
    {
        private String topicName;
        private Map topicRecord;
        private Date createdTime;
        private String environmentName;
        private Map topicRecordValue;
        private Long rowId;
        private Map<String, Object> additionalProperties;
        
        MessageBrokerResponseBuilder() {
        }
        
        public MessageBrokerResponseBuilder topicName(final String topicName) {
            this.topicName = topicName;
            return this;
        }
        
        public MessageBrokerResponseBuilder topicRecord(final Map topicRecord) {
            this.topicRecord = topicRecord;
            return this;
        }
        
        public MessageBrokerResponseBuilder createdTime(final Date createdTime) {
            this.createdTime = createdTime;
            return this;
        }
        
        public MessageBrokerResponseBuilder environmentName(final String environmentName) {
            this.environmentName = environmentName;
            return this;
        }
        
        public MessageBrokerResponseBuilder topicRecordValue(final Map topicRecordValue) {
            this.topicRecordValue = topicRecordValue;
            return this;
        }
        
        public MessageBrokerResponseBuilder rowId(final Long rowId) {
            this.rowId = rowId;
            return this;
        }
        
        public MessageBrokerResponseBuilder additionalProperties(final Map<String, Object> additionalProperties) {
            this.additionalProperties = additionalProperties;
            return this;
        }
        
        public MessageBrokerResponse build() {
            return new MessageBrokerResponse(this.topicName, this.topicRecord, this.createdTime, this.environmentName, this.topicRecordValue, this.rowId, this.additionalProperties);
        }
        
        @Override
        public String toString() {
            return "MessageBrokerResponse.MessageBrokerResponseBuilder(topicName=" + this.topicName + ", topicRecord=" + this.topicRecord + ", createdTime=" + this.createdTime + ", environmentName=" + this.environmentName + ", topicRecordValue=" + this.topicRecordValue + ", rowId=" + this.rowId + ", additionalProperties=" + this.additionalProperties + ")";
        }
    }
}
