// 

// 

package qa.redis;

import org.slf4j.LoggerFactory;
import java.util.concurrent.Callable;
import qa.generic.PollingPredicate;
import java.util.function.Predicate;
import redis.clients.jedis.exceptions.JedisMovedDataException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.HashMap;
import java.util.Set;
import java.time.Duration;
import org.slf4j.Logger;

public class RedisKeysOperation
{
    private static Logger logger;
    
    RedisKeysOperation() {
    }
    
    public static RedisKeysOperation getInstance() {
        return new RedisKeysOperation();
    }
    
    public String getValue(final String key) {
        final String valueForKey = this.pollingToGetValueForKey(key, i -> !i.isEmpty(), Duration.ofSeconds(10L), Duration.ofSeconds(1L));
        RedisKeysOperation.logger.info(String.format("The string value for provided %s key is:: %s", key, valueForKey));
        return valueForKey;
    }
    
    public Set<String> listAllKeys() {
        final Set<String> allKeys = RedisClientConfiguration.getInstance().getClient().keys("*");
        RedisKeysOperation.logger.info("Listing All keys in Redis");
        return allKeys;
    }
    
    public HashMap<String, String> searchKeyIsPresent(final String... keys) {
        final HashMap<String, String> keysResult = new HashMap<String, String>();
        final AtomicReference<String> keyParam = new AtomicReference<String>();
        try {
            Arrays.stream(keys).forEach(x -> {
                keyParam.set(x);
                final boolean result = RedisClientConfiguration.getInstance().getClient().exists(x);
                if (result) {
                    keysResult.put(x, "Present in redis");
                    RedisKeysOperation.logger.info(String.format("%s key is present in redis", x));
                }
                else {
                    keysResult.put(x, " Not Present in redis");
                    RedisKeysOperation.logger.info(String.format("%s Not key is present in redis", x));
                }
                return;
            });
        }
        catch (final JedisMovedDataException e) {
            keysResult.put(keyParam.get(), " Not Present in redis");
            RedisKeysOperation.logger.info(String.format("%s Moved data exception", keyParam.get()));
        }
        return keysResult;
    }
    
    public HashMap<String, String> getValue(final String... keys) {
        final HashMap<String, String> keyValues = new HashMap<String, String>();
        Arrays.stream(keys).forEach(x -> {
            final String value = this.pollingToGetValueForKey(x, i -> !i.isEmpty(), Duration.ofSeconds(10L), Duration.ofMillis(1L));
            keyValues.put(x, value);
            return;
        });
        return keyValues;
    }
    
    public byte[] getValue(final byte[] key) {
        final byte[] valueForKey = RedisClientConfiguration.getInstance().getClient().get(key);
        RedisKeysOperation.logger.info(String.format("The byte array value for provided %s key is:: %s", key, valueForKey));
        return valueForKey;
    }
    
    public String pollingToGetValueForKey(final String key, final Predicate<String> keyPredicate, final Duration maxTimeToWait, final Duration pollInterval) {
        final String predicate = new PollingPredicate<String>(this.checkKeyValue(key), keyPredicate, maxTimeToWait, pollInterval).evaluate();
        return predicate;
    }
    
    private Callable<String> checkKeyValue(final String key) {
        RedisKeysOperation.logger.info("Starting get value from redis by using key in polling manner ...............");
        final int[] counter = { 0 };
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                RedisKeysOperation.logger.info("polling value..... " + counter[0]++);
                final String keyValue = RedisClientConfiguration.getInstance().getClient().get(key);
                return keyValue;
            }
        };
    }
    
    static {
        RedisKeysOperation.logger = LoggerFactory.getLogger((Class)RedisKeysOperation.class);
    }
}
