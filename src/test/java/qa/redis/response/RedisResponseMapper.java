// 

// 

package qa.redis.response;

public class RedisResponseMapper
{
    public static RedisResponse map(final String key, final Object value, final long ttl) {
        return RedisResponse.builder().key(key).value(value).ttl(ttl).build();
    }
}
