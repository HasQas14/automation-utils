package qa.delete.response;

import java.util.HashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.NonNull;

public class GenerateTopicDataRequest
{
    @NonNull
    private String topic_name;
    @NonNull
    private String bootStrap_server;
    @NonNull
    private String client_id;
    private final Map<String, Object> properties;
    @NonNull
    private Object topic_data;
    
    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        try {
            return mapper.writeValueAsString((Object)this);
        }
        catch (final JsonProcessingException ex) {
            return "GenerateTopicDataRequest{topic_name='" + this.topic_name + '\'' + ", bootStrap_server='" + this.bootStrap_server + '\'' + ", client_id='" + this.client_id + '\'' + ", properties=" + this.properties + ", topic_data='" + this.topic_data + '\'' + '}';
        }
    }
    
    public static GenerateTopicDataRequestBuilder builder() {
        return new GenerateTopicDataRequestBuilder();
    }
    
    @NonNull
    public String getTopic_name() {
        return this.topic_name;
    }
    
    @NonNull
    public String getBootStrap_server() {
        return this.bootStrap_server;
    }
    
    @NonNull
    public String getClient_id() {
        return this.client_id;
    }
    
    public Map<String, Object> getProperties() {
        return this.properties;
    }
    
    @NonNull
    public Object getTopic_data() {
        return this.topic_data;
    }
    
    public void setTopic_name(@NonNull final String topic_name) {
        if (topic_name == null) {
            throw new NullPointerException("topic_name is marked non-null but is null");
        }
        this.topic_name = topic_name;
    }
    
    public void setBootStrap_server(@NonNull final String bootStrap_server) {
        if (bootStrap_server == null) {
            throw new NullPointerException("bootStrap_server is marked non-null but is null");
        }
        this.bootStrap_server = bootStrap_server;
    }
    
    public void setClient_id(@NonNull final String client_id) {
        if (client_id == null) {
            throw new NullPointerException("client_id is marked non-null but is null");
        }
        this.client_id = client_id;
    }
    
    public void setTopic_data(@NonNull final Object topic_data) {
        if (topic_data == null) {
            throw new NullPointerException("topic_data is marked non-null but is null");
        }
        this.topic_data = topic_data;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GenerateTopicDataRequest)) {
            return false;
        }
        final GenerateTopicDataRequest other = (GenerateTopicDataRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$topic_name = this.getTopic_name();
        final Object other$topic_name = other.getTopic_name();
        Label_0065: {
            if (this$topic_name == null) {
                if (other$topic_name == null) {
                    break Label_0065;
                }
            }
            else if (this$topic_name.equals(other$topic_name)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$bootStrap_server = this.getBootStrap_server();
        final Object other$bootStrap_server = other.getBootStrap_server();
        Label_0102: {
            if (this$bootStrap_server == null) {
                if (other$bootStrap_server == null) {
                    break Label_0102;
                }
            }
            else if (this$bootStrap_server.equals(other$bootStrap_server)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$client_id = this.getClient_id();
        final Object other$client_id = other.getClient_id();
        Label_0139: {
            if (this$client_id == null) {
                if (other$client_id == null) {
                    break Label_0139;
                }
            }
            else if (this$client_id.equals(other$client_id)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$properties = this.getProperties();
        final Object other$properties = other.getProperties();
        Label_0176: {
            if (this$properties == null) {
                if (other$properties == null) {
                    break Label_0176;
                }
            }
            else if (this$properties.equals(other$properties)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$topic_data = this.getTopic_data();
        final Object other$topic_data = other.getTopic_data();
        if (this$topic_data == null) {
            if (other$topic_data == null) {
                return true;
            }
        }
        else if (this$topic_data.equals(other$topic_data)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof GenerateTopicDataRequest;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $topic_name = this.getTopic_name();
        result = result * 59 + (($topic_name == null) ? 43 : $topic_name.hashCode());
        final Object $bootStrap_server = this.getBootStrap_server();
        result = result * 59 + (($bootStrap_server == null) ? 43 : $bootStrap_server.hashCode());
        final Object $client_id = this.getClient_id();
        result = result * 59 + (($client_id == null) ? 43 : $client_id.hashCode());
        final Object $properties = this.getProperties();
        result = result * 59 + (($properties == null) ? 43 : $properties.hashCode());
        final Object $topic_data = this.getTopic_data();
        result = result * 59 + (($topic_data == null) ? 43 : $topic_data.hashCode());
        return result;
    }
    
    public GenerateTopicDataRequest(@NonNull final String topic_name, @NonNull final String bootStrap_server, @NonNull final String client_id, @NonNull final Object topic_data) {
        this.properties = new HashMap<String, Object>();
        if (topic_name == null) {
            throw new NullPointerException("topic_name is marked non-null but is null");
        }
        if (bootStrap_server == null) {
            throw new NullPointerException("bootStrap_server is marked non-null but is null");
        }
        if (client_id == null) {
            throw new NullPointerException("client_id is marked non-null but is null");
        }
        if (topic_data == null) {
            throw new NullPointerException("topic_data is marked non-null but is null");
        }
        this.topic_name = topic_name;
        this.bootStrap_server = bootStrap_server;
        this.client_id = client_id;
        this.topic_data = topic_data;
    }
    
    public GenerateTopicDataRequest() {
        this.properties = new HashMap<String, Object>();
    }
    
    public static class GenerateTopicDataRequestBuilder
    {
        private String topic_name;
        private String bootStrap_server;
        private String client_id;
        private Object topic_data;
        
        GenerateTopicDataRequestBuilder() {
        }
        
        public GenerateTopicDataRequestBuilder topic_name(@NonNull final String topic_name) {
            if (topic_name == null) {
                throw new NullPointerException("topic_name is marked non-null but is null");
            }
            this.topic_name = topic_name;
            return this;
        }
        
        public GenerateTopicDataRequestBuilder bootStrap_server(@NonNull final String bootStrap_server) {
            if (bootStrap_server == null) {
                throw new NullPointerException("bootStrap_server is marked non-null but is null");
            }
            this.bootStrap_server = bootStrap_server;
            return this;
        }
        
        public GenerateTopicDataRequestBuilder client_id(@NonNull final String client_id) {
            if (client_id == null) {
                throw new NullPointerException("client_id is marked non-null but is null");
            }
            this.client_id = client_id;
            return this;
        }
        
        public GenerateTopicDataRequestBuilder topic_data(@NonNull final Object topic_data) {
            if (topic_data == null) {
                throw new NullPointerException("topic_data is marked non-null but is null");
            }
            this.topic_data = topic_data;
            return this;
        }
        
        public GenerateTopicDataRequest build() {
            return new GenerateTopicDataRequest(this.topic_name, this.bootStrap_server, this.client_id, this.topic_data);
        }
        
        @Override
        public String toString() {
            return "GenerateTopicDataRequest.GenerateTopicDataRequestBuilder(topic_name=" + this.topic_name + ", bootStrap_server=" + this.bootStrap_server + ", client_id=" + this.client_id + ", topic_data=" + this.topic_data + ")";
        }
    }
}
