// 

// 

package qa.mock.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;

public class GrpcRequest
{
    @NonNull
    private String service_ip;
    @NonNull
    private String service_port;
    @NonNull
    private String method_name;
    @NonNull
    private String class_name;
    @NonNull
    private String request_message_class_name;
    @NonNull
    private Object message;
    
    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
        try {
            return mapper.writeValueAsString((Object)this);
        }
        catch (final JsonProcessingException e) {
            throw new RuntimeException("Error occured while toString for GrpcRequest", (Throwable)e);
        }
    }
    
    public GrpcRequest(@NonNull final String service_ip, @NonNull final String service_port, @NonNull final String method_name, @NonNull final String class_name, @NonNull final String request_message_class_name, @NonNull final Object message) {
        if (service_ip == null) {
            throw new NullPointerException("service_ip is marked non-null but is null");
        }
        if (service_port == null) {
            throw new NullPointerException("service_port is marked non-null but is null");
        }
        if (method_name == null) {
            throw new NullPointerException("method_name is marked non-null but is null");
        }
        if (class_name == null) {
            throw new NullPointerException("class_name is marked non-null but is null");
        }
        if (request_message_class_name == null) {
            throw new NullPointerException("request_message_class_name is marked non-null but is null");
        }
        if (message == null) {
            throw new NullPointerException("message is marked non-null but is null");
        }
        this.service_ip = service_ip;
        this.service_port = service_port;
        this.method_name = method_name;
        this.class_name = class_name;
        this.request_message_class_name = request_message_class_name;
        this.message = message;
    }
    
    @NonNull
    public String getService_ip() {
        return this.service_ip;
    }
    
    @NonNull
    public String getService_port() {
        return this.service_port;
    }
    
    @NonNull
    public String getMethod_name() {
        return this.method_name;
    }
    
    @NonNull
    public String getClass_name() {
        return this.class_name;
    }
    
    @NonNull
    public String getRequest_message_class_name() {
        return this.request_message_class_name;
    }
    
    @NonNull
    public Object getMessage() {
        return this.message;
    }
    
    public void setService_ip(@NonNull final String service_ip) {
        if (service_ip == null) {
            throw new NullPointerException("service_ip is marked non-null but is null");
        }
        this.service_ip = service_ip;
    }
    
    public void setService_port(@NonNull final String service_port) {
        if (service_port == null) {
            throw new NullPointerException("service_port is marked non-null but is null");
        }
        this.service_port = service_port;
    }
    
    public void setMethod_name(@NonNull final String method_name) {
        if (method_name == null) {
            throw new NullPointerException("method_name is marked non-null but is null");
        }
        this.method_name = method_name;
    }
    
    public void setClass_name(@NonNull final String class_name) {
        if (class_name == null) {
            throw new NullPointerException("class_name is marked non-null but is null");
        }
        this.class_name = class_name;
    }
    
    public void setRequest_message_class_name(@NonNull final String request_message_class_name) {
        if (request_message_class_name == null) {
            throw new NullPointerException("request_message_class_name is marked non-null but is null");
        }
        this.request_message_class_name = request_message_class_name;
    }
    
    public void setMessage(@NonNull final Object message) {
        if (message == null) {
            throw new NullPointerException("message is marked non-null but is null");
        }
        this.message = message;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GrpcRequest)) {
            return false;
        }
        final GrpcRequest other = (GrpcRequest)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$service_ip = this.getService_ip();
        final Object other$service_ip = other.getService_ip();
        Label_0065: {
            if (this$service_ip == null) {
                if (other$service_ip == null) {
                    break Label_0065;
                }
            }
            else if (this$service_ip.equals(other$service_ip)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$service_port = this.getService_port();
        final Object other$service_port = other.getService_port();
        Label_0102: {
            if (this$service_port == null) {
                if (other$service_port == null) {
                    break Label_0102;
                }
            }
            else if (this$service_port.equals(other$service_port)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$method_name = this.getMethod_name();
        final Object other$method_name = other.getMethod_name();
        Label_0139: {
            if (this$method_name == null) {
                if (other$method_name == null) {
                    break Label_0139;
                }
            }
            else if (this$method_name.equals(other$method_name)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$class_name = this.getClass_name();
        final Object other$class_name = other.getClass_name();
        Label_0176: {
            if (this$class_name == null) {
                if (other$class_name == null) {
                    break Label_0176;
                }
            }
            else if (this$class_name.equals(other$class_name)) {
                break Label_0176;
            }
            return false;
        }
        final Object this$request_message_class_name = this.getRequest_message_class_name();
        final Object other$request_message_class_name = other.getRequest_message_class_name();
        Label_0213: {
            if (this$request_message_class_name == null) {
                if (other$request_message_class_name == null) {
                    break Label_0213;
                }
            }
            else if (this$request_message_class_name.equals(other$request_message_class_name)) {
                break Label_0213;
            }
            return false;
        }
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null) {
            if (other$message == null) {
                return true;
            }
        }
        else if (this$message.equals(other$message)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof GrpcRequest;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $service_ip = this.getService_ip();
        result = result * 59 + (($service_ip == null) ? 43 : $service_ip.hashCode());
        final Object $service_port = this.getService_port();
        result = result * 59 + (($service_port == null) ? 43 : $service_port.hashCode());
        final Object $method_name = this.getMethod_name();
        result = result * 59 + (($method_name == null) ? 43 : $method_name.hashCode());
        final Object $class_name = this.getClass_name();
        result = result * 59 + (($class_name == null) ? 43 : $class_name.hashCode());
        final Object $request_message_class_name = this.getRequest_message_class_name();
        result = result * 59 + (($request_message_class_name == null) ? 43 : $request_message_class_name.hashCode());
        final Object $message = this.getMessage();
        result = result * 59 + (($message == null) ? 43 : $message.hashCode());
        return result;
    }
}
