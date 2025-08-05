// 

// 

package qa.redis.config;

import redis.clients.jedis.JedisCluster;
import qa.redis.adapter.ClusterRedisAdapterImpl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.HostAndPort;
import qa.redis.adapter.SingleRedisAdapterImpl;
import org.assertj.core.api.Assertions;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Duration;
import org.slf4j.Logger;
import java.util.Map;

public class RedisClientConfig
{
    private Map<String, InstanceConfig> redisInstanceHolder;
    private Logger logger;
    private RedisType defaultRedisType;
    private static final RedisClientConfig redisClientConfig;
    private Duration MAX_TIME;
    private Duration POLL_TIME;
    
    private RedisClientConfig() {
        this.redisInstanceHolder = new ConcurrentHashMap<String, InstanceConfig>();
        this.logger = LoggerFactory.getLogger((Class)RedisClientConfig.class);
        this.MAX_TIME = Duration.ofSeconds(15L);
        this.POLL_TIME = Duration.ofMillis(500L);
    }
    
    public static RedisClientConfig getInstance() {
        return RedisClientConfig.redisClientConfig;
    }
    
    public static <T> T getRedisUtilInstance(final Class<T> tClass) {
        return (T)getInstance().getConfig().getRedisUtilInstance();
    }
    
    public static <T> T getRedisUtilInstance(final String url, final Class<T> tClass) {
        return (T)getInstance().getConfig(url).getRedisUtilInstance();
    }
    
    public static <T> T getRedisUtilInstance(final RedisType redisType, final String url, final Class<T> tClass) {
        return (T)getInstance().getConfig(redisType, url).getRedisUtilInstance();
    }
    
    public InstanceConfig getConfig() {
        if (this.redisInstanceHolder.isEmpty()) {
            Assertions.fail(String.format("RedisClientConfig not initialized", new Object[0]));
        }
        return (InstanceConfig)this.redisInstanceHolder.entrySet().stream().findFirst().get().getValue();
    }
    
    public InstanceConfig getConfig(final String url) {
        final String key = this.getKey(url, this.defaultRedisType);
        if (this.redisInstanceHolder.containsKey(key)) {
            return this.redisInstanceHolder.get(key);
        }
        Assertions.fail(String.format("redis with url: %s and redisType: %s is not configured", url, this.defaultRedisType));
        return null;
    }
    
    public InstanceConfig getConfig(final RedisType redisType, final String url) {
        final String key = this.getKey(url, redisType);
        if (this.redisInstanceHolder.containsKey(key)) {
            return this.redisInstanceHolder.get(key);
        }
        Assertions.fail(String.format("redis with url: %s and redisType: %s is not configured", url, redisType));
        return null;
    }
    
    public void init(final RedisType redisType, final String url) {
        try {
            this.defaultRedisType = redisType;
            final String key = this.getKey(url, redisType);
            if (this.redisInstanceHolder.containsKey(key)) {
                this.logger.warn("Trying to re-initialize adapter with same configs");
                return;
            }
            this.logger.info(String.format("Configuring RedisClient with configs, redisType: %s and redisUrl: %s", redisType, url));
            final InstanceConfig instanceConfig = new InstanceConfig();
            instanceConfig.setRedisType(redisType);
            instanceConfig.setUrl(url);
            switch (redisType) {
                case SINGLE: {
                    final SingleRedisAdapterImpl adapter = SingleRedisAdapterImpl.getInstance();
                    final Jedis redisClient = new Jedis(HostAndPort.from(url));
                    instanceConfig.setIRedisAdapter(adapter);
                    instanceConfig.setRedisUtilInstance(redisClient);
                    this.redisInstanceHolder.put(key, instanceConfig);
                    break;
                }
                case CLUSTER: {
                    final ClusterRedisAdapterImpl adapter2 = ClusterRedisAdapterImpl.getInstance();
                    final JedisCluster redisClient2 = new JedisCluster(HostAndPort.from(url));
                    instanceConfig.setIRedisAdapter(adapter2);
                    instanceConfig.setRedisUtilInstance(redisClient2);
                    this.redisInstanceHolder.put(key, instanceConfig);
                    break;
                }
            }
            this.logger.info(String.format("RedisClient configuration successful with configs, redisType: %s and redisUrl: %s", redisType, url));
        }
        catch (final Exception e) {
            this.logger.error("error occured in configuring redis client:", (Throwable)e);
        }
    }
    
    public void init(final String url) {
        this.init(RedisType.CLUSTER, url);
    }
    
    private String getKey(final String url, final RedisType redis_type) {
        return url + redis_type;
    }
    
    public Duration getMAX_TIME() {
        return this.MAX_TIME;
    }
    
    public Duration getPOLL_TIME() {
        return this.POLL_TIME;
    }
    
    static {
        redisClientConfig = new RedisClientConfig();
    }
}
