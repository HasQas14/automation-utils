// 

// 

package qa.redis;

import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import redis.clients.jedis.HostAndPort;
import org.slf4j.Logger;
import java.util.LinkedList;
import redis.clients.jedis.Jedis;
import java.util.Map;

public class RedisClientConfiguration
{
    private static final Map<String, Jedis> redisClientMap;
    private static final LinkedList<String> redisClientList;
    private static Logger logger;
    private static RedisClientConfiguration instance;
    private static String serverType;
    static final /* synthetic */ boolean assertionsDisabled = false;
    
    RedisClientConfiguration() {
    }
    
    public static RedisClientConfiguration getInstance() {
        return RedisClientConfiguration.instance;
    }
    
    public void initialize(final String redisUrl) {
        if (!RedisClientConfiguration.redisClientMap.containsKey(redisUrl)) {
            final Jedis redisClient = new Jedis(HostAndPort.from(redisUrl));
            RedisClientConfiguration.logger.info(String.format("Redis Client successfully initialized for %s", redisUrl));
            this.addRedisClient(redisUrl, redisClient);
        }
        else {
            RedisClientConfiguration.logger.info(String.format("Redis Client already initialized for %s", redisUrl));
        }
    }
    
    private void addRedisClient(final String redisUrl, final Jedis client) {
        RedisClientConfiguration.redisClientMap.put(redisUrl, client);
        RedisClientConfiguration.redisClientList.add(redisUrl);
    }
    
    public Jedis getClient() {
        if (RedisClientConfiguration.redisClientList.isEmpty() && !RedisClientConfiguration.assertionsDisabled) {
            throw new AssertionError((Object)"Redis Client instance not available");
        }
        return RedisClientConfiguration.redisClientMap.get(RedisClientConfiguration.redisClientList.get(0));
    }
    
    public Jedis getClient(final String redisUrl) {
        if (RedisClientConfiguration.redisClientMap.containsKey(redisUrl)) {
            return RedisClientConfiguration.redisClientMap.get(redisUrl);
        }
        assert false : String.format("Redis Client not available for given connection string: %s", redisUrl);
        return null;
    }
    
    public void disconnectClientConnection(final String redisUrl) {
        this.getClient(redisUrl).close();
    }
    
    private void removeRedisClient(final String redisUrl) {
        RedisClientConfiguration.redisClientMap.remove(redisUrl);
        RedisClientConfiguration.redisClientList.remove(redisUrl);
    }
    
    static {
        redisClientMap = new ConcurrentHashMap<String, Jedis>();
        redisClientList = new LinkedList<String>();
        RedisClientConfiguration.logger = LoggerFactory.getLogger((Class)RedisClientConfiguration.class);
        RedisClientConfiguration.instance = new RedisClientConfiguration();
    }
}
