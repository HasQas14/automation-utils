package RedisAdapter;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAdapter.class);
    private final RedisClient redisClient;
    private final StatefulRedisConnection<String, String> connection;
    private final RedisCommands<String, String> commands;

    public RedisAdapter(String redisUrl, String redisPassword) {
        // rediss:// if redis is encypted otherwise redis://
        if(redisPassword.equalsIgnoreCase("")){
            this.redisClient = RedisClient.create("rediss://"+redisUrl);
        }
        else{
            this.redisClient = RedisClient.create("rediss://"+redisPassword+"@"+redisUrl);
        }
        this.connection = redisClient.connect();
        LOGGER.info("redis client connected at: {}", connection);
        this.commands = connection.sync();
        LOGGER.info("Redis PING response: {}", this.commands.ping());
    }

    // key never expires in redis
    public void set(String key, String value) {
        try{
            commands.setex(key, 0, value);
            LOGGER.info("non expiry key {} set in redis with value: {}", key, value);
        } catch (Exception e) {
            LOGGER.error("could not set key in redis");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // key expires in ttlSeconds
    public void set(String key, String value, int ttlSeconds) {
        try{
            commands.setex(key, ttlSeconds, value);
            LOGGER.info("expiry key {} set in redis with value: {} for duration {} secs", key, value, ttlSeconds);
        } catch (Exception e) {
            LOGGER.error("could not set key in redis");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public String get(String key) {
        try{
            String value = commands.get(key);
            LOGGER.info("value {} returned for key {}: ", value, key);
            return value;
        } catch (Exception e) {
            LOGGER.error("cannot get key from redis");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void delete(String key) {
        try{
            commands.del(key);
            LOGGER.info("key successfully deleted from the redis");
        } catch (Exception e) {
            LOGGER.error("no such key found in redis or facing some other issue");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void flushRedis() {
        commands.flushdb();
    }

    public void close() {
        connection.close();
        redisClient.shutdown();
    }
}