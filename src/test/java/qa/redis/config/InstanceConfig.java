// 

// 

package qa.redis.config;

import qa.redis.response.RedisResponse;
import qa.redis.adapter.IRedisAdapter;

public class InstanceConfig
{
    private RedisType redisType;
    private String url;
    private IRedisAdapter<RedisResponse> iRedisAdapter;
    private Object redisUtilInstance;
    
    public RedisType getRedisType() {
        return this.redisType;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public IRedisAdapter<RedisResponse> getIRedisAdapter() {
        return this.iRedisAdapter;
    }
    
    public Object getRedisUtilInstance() {
        return this.redisUtilInstance;
    }
    
    public void setRedisType(final RedisType redisType) {
        this.redisType = redisType;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public void setIRedisAdapter(final IRedisAdapter<RedisResponse> iRedisAdapter) {
        this.iRedisAdapter = iRedisAdapter;
    }
    
    public void setRedisUtilInstance(final Object redisUtilInstance) {
        this.redisUtilInstance = redisUtilInstance;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof InstanceConfig)) {
            return false;
        }
        final InstanceConfig other = (InstanceConfig)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$redisType = this.getRedisType();
        final Object other$redisType = other.getRedisType();
        Label_0065: {
            if (this$redisType == null) {
                if (other$redisType == null) {
                    break Label_0065;
                }
            }
            else if (this$redisType.equals(other$redisType)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$url = this.getUrl();
        final Object other$url = other.getUrl();
        Label_0102: {
            if (this$url == null) {
                if (other$url == null) {
                    break Label_0102;
                }
            }
            else if (this$url.equals(other$url)) {
                break Label_0102;
            }
            return false;
        }
        final Object this$iRedisAdapter = this.getIRedisAdapter();
        final Object other$iRedisAdapter = other.getIRedisAdapter();
        Label_0139: {
            if (this$iRedisAdapter == null) {
                if (other$iRedisAdapter == null) {
                    break Label_0139;
                }
            }
            else if (this$iRedisAdapter.equals(other$iRedisAdapter)) {
                break Label_0139;
            }
            return false;
        }
        final Object this$redisUtilInstance = this.getRedisUtilInstance();
        final Object other$redisUtilInstance = other.getRedisUtilInstance();
        if (this$redisUtilInstance == null) {
            if (other$redisUtilInstance == null) {
                return true;
            }
        }
        else if (this$redisUtilInstance.equals(other$redisUtilInstance)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof InstanceConfig;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $redisType = this.getRedisType();
        result = result * 59 + (($redisType == null) ? 43 : $redisType.hashCode());
        final Object $url = this.getUrl();
        result = result * 59 + (($url == null) ? 43 : $url.hashCode());
        final Object $iRedisAdapter = this.getIRedisAdapter();
        result = result * 59 + (($iRedisAdapter == null) ? 43 : $iRedisAdapter.hashCode());
        final Object $redisUtilInstance = this.getRedisUtilInstance();
        result = result * 59 + (($redisUtilInstance == null) ? 43 : $redisUtilInstance.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "InstanceConfig(redisType=" + this.getRedisType() + ", url=" + this.getUrl() + ", iRedisAdapter=" + this.getIRedisAdapter() + ", redisUtilInstance=" + this.getRedisUtilInstance() + ")";
    }
}
