// 

// 

package qa.redis.response;

public class RedisResponse
{
    private String key;
    private Object value;
    private long ttl;
    
    public static RedisResponseBuilder builder() {
        return new RedisResponseBuilder();
    }
    
    public RedisResponseBuilder toBuilder() {
        return new RedisResponseBuilder().key(this.key).value(this.value).ttl(this.ttl);
    }
    
    public String getKey() {
        return this.key;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public long getTtl() {
        return this.ttl;
    }
    
    public void setKey(final String key) {
        this.key = key;
    }
    
    public void setValue(final Object value) {
        this.value = value;
    }
    
    public void setTtl(final long ttl) {
        this.ttl = ttl;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RedisResponse)) {
            return false;
        }
        final RedisResponse other = (RedisResponse)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getTtl() != other.getTtl()) {
            return false;
        }
        final Object this$key = this.getKey();
        final Object other$key = other.getKey();
        Label_0079: {
            if (this$key == null) {
                if (other$key == null) {
                    break Label_0079;
                }
            }
            else if (this$key.equals(other$key)) {
                break Label_0079;
            }
            return false;
        }
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if (this$value == null) {
            if (other$value == null) {
                return true;
            }
        }
        else if (this$value.equals(other$value)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof RedisResponse;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $ttl = this.getTtl();
        result = result * 59 + (int)($ttl >>> 32 ^ $ttl);
        final Object $key = this.getKey();
        result = result * 59 + (($key == null) ? 43 : $key.hashCode());
        final Object $value = this.getValue();
        result = result * 59 + (($value == null) ? 43 : $value.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "RedisResponse(key=" + this.getKey() + ", value=" + this.getValue() + ", ttl=" + this.getTtl() + ")";
    }
    
    public RedisResponse() {
    }
    
    public RedisResponse(final String key, final Object value, final long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }
    
    public static class RedisResponseBuilder
    {
        private String key;
        private Object value;
        private long ttl;
        
        RedisResponseBuilder() {
        }
        
        public RedisResponseBuilder key(final String key) {
            this.key = key;
            return this;
        }
        
        public RedisResponseBuilder value(final Object value) {
            this.value = value;
            return this;
        }
        
        public RedisResponseBuilder ttl(final long ttl) {
            this.ttl = ttl;
            return this;
        }
        
        public RedisResponse build() {
            return new RedisResponse(this.key, this.value, this.ttl);
        }
        
        @Override
        public String toString() {
            return "RedisResponse.RedisResponseBuilder(key=" + this.key + ", value=" + this.value + ", ttl=" + this.ttl + ")";
        }
    }
}
