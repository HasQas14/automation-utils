// 

// 

package qa.redis.adapter;

import java.util.HashMap;
import java.util.List;
import qa.redis.config.RedisType;
import qa.redis.config.RedisClientConfig;
import qa.redis.response.RedisResponse;

public class RedisAdapter
{
    public static RedisResponse findBy_key_firstOccurrence(final String key) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().findBy_key_firstOccurrence(key);
    }
    
    public static RedisResponse findBy_key_firstOccurrence(final String key, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().findBy_key_firstOccurrence(key, url);
    }
    
    public static RedisResponse findBy_key_firstOccurrence(final String key, final RedisType redisType, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().findBy_key_firstOccurrence(key, redisType, url);
    }
    
    public static List<RedisResponse> findBy_key(final String key) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().findBy_key(key);
    }
    
    public static List<RedisResponse> findBy_key(final String key, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().findBy_key(key, url);
    }
    
    public static List<RedisResponse> findBy_key(final String key, final RedisType redisType, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().findBy_key(key, redisType, url);
    }
    
    public static boolean is_key_present(final String key) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().is_key_present(key);
    }
    
    public static boolean is_key_present(final String key, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().is_key_present(key, url);
    }
    
    public static boolean is_key_present(final String key, final RedisType redisType, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().is_key_present(key, redisType, url);
    }
    
    public static HashMap<String, Boolean> deleteKeys(final List<String> keys) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().deleteKeys(keys);
    }
    
    public static HashMap<String, Boolean> deleteKeys(final String url, final List<String> keys) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().deleteKeys(url, keys);
    }
    
    public static HashMap<String, Boolean> deleteKeys(final RedisType redisType, final String url, final List<String> keys) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().deleteKeys(redisType, url, keys);
    }
    
    public static boolean deleteKey(final String key) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().deleteKey(key);
    }
    
    public static boolean deleteKey(final String url, final String key) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().deleteKey(url, key);
    }
    
    public static boolean deleteKey(final RedisType redisType, final String url, final String key) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().deleteKey(redisType, url, key);
    }
    
    public static boolean createKey(final String key, final String value) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().createKey(key, value);
    }
    
    public static boolean createKey(final String key, final String value, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().createKey(key, value, url);
    }
    
    public static boolean createKey(final String key, final String value, final RedisType redisType, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().createKey(key, value, redisType, url);
    }
    
    public static boolean createKey(final String key, final String value, final long ttl) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().createKey(key, value, ttl);
    }
    
    public static boolean createKey(final String key, final String value, final long ttl, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().createKey(key, value, ttl, url);
    }
    
    public static boolean createKey(final String key, final String value, final long ttl, final RedisType redisType, final String url) {
        return RedisClientConfig.getInstance().getConfig().getIRedisAdapter().createKey(key, value, ttl, redisType, url);
    }
}
