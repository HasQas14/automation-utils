// 

// 

package qa.redis.adapter;

import org.slf4j.LoggerFactory;
import redis.clients.jedis.params.SetParams;
import java.time.Duration;
import qa.generic.PollingPredicate;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import qa.redis.config.RedisType;
import qa.redis.config.RedisClientConfig;
import redis.clients.jedis.Jedis;
import org.slf4j.Logger;
import qa.redis.response.RedisResponse;

public class SingleRedisAdapterImpl implements IRedisAdapter<RedisResponse>
{
    private static SingleRedisAdapterImpl redisAdapter;
    private static Logger logger;
    
    private SingleRedisAdapterImpl() {
    }
    
    public static SingleRedisAdapterImpl getInstance() {
        if (SingleRedisAdapterImpl.redisAdapter == null) {
            SingleRedisAdapterImpl.redisAdapter = new SingleRedisAdapterImpl();
        }
        return SingleRedisAdapterImpl.redisAdapter;
    }
    
    @Override
    public RedisResponse findBy_key_firstOccurrence(final String key) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        return RedisResponse.builder().key(key).value(jedis.get(key)).ttl(jedis.ttl(key)).build();
    }
    
    @Override
    public RedisResponse findBy_key_firstOccurrence(final String key, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        return RedisResponse.builder().key(key).value(jedis.get(key)).ttl(jedis.ttl(key)).build();
    }
    
    @Override
    public RedisResponse findBy_key_firstOccurrence(final String key, final RedisType redisType, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        return RedisResponse.builder().key(key).value(jedis.get(key)).ttl(jedis.ttl(key)).build();
    }
    
    @Override
    public List<RedisResponse> findBy_key(final String key) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        redisResponses.add(RedisResponse.builder().key(key).value(jedis.get(key)).ttl(jedis.ttl(key)).build());
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_key(final String key, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        redisResponses.add(RedisResponse.builder().key(key).value(jedis.get(key)).ttl(jedis.ttl(key)).build());
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_key(final String key, final RedisType redisType, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        redisResponses.add(RedisResponse.builder().key(key).value(jedis.get(key)).ttl(jedis.ttl(key)).build());
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_all() {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        final Set<String> keys = jedis.keys("*");
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        keys.stream().forEach(x -> redisResponses.add(RedisResponse.builder().key(x).value(jedis.get(x)).ttl(jedis.ttl(x)).build()));
        SingleRedisAdapterImpl.logger.info("Listing All keys,values and ttl in Redis: " + keys);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_all(final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        final Set<String> keys = jedis.keys("*");
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        keys.stream().forEach(x -> redisResponses.add(RedisResponse.builder().key(x).value(jedis.get(x)).ttl(jedis.ttl(x)).build()));
        SingleRedisAdapterImpl.logger.info("Listing All keys,values and ttl in Redis");
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_all(final RedisType redisType, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        final Set<String> keys = jedis.keys("*");
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        keys.stream().forEach(x -> redisResponses.add(RedisResponse.builder().key(x).value(jedis.get(x)).ttl(jedis.ttl(x)).build()));
        SingleRedisAdapterImpl.logger.info("Listing All keys,values and ttl in Redis");
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all();
                responses.forEach(i -> redisResponseMap1.put(i.getKey(), i));
                redisResponses.clear();
                redisResponseMap1.keySet().stream().filter(predicate).collect(Collectors.toList()).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(redisResponseMap1.get(i));
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(url);
                responses.forEach(i -> redisResponseMap1.put(i.getKey(), i));
                redisResponses.clear();
                redisResponseMap1.keySet().stream().filter(predicate).collect(Collectors.toList()).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(redisResponseMap1.get(i));
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, RedisClientConfig.getInstance().getMAX_TIME(), RedisClientConfig.getInstance().getPOLL_TIME()).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(redisType, url);
                responses.forEach(i -> redisResponseMap1.put(i.getKey(), i));
                redisResponses.clear();
                redisResponseMap1.keySet().stream().filter(predicate).collect(Collectors.toList()).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(redisResponseMap1.get(i));
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all();
                responses.forEach(i -> redisResponseMap1.put(i.getKey(), i));
                redisResponses.clear();
                redisResponseMap1.keySet().stream().filter(predicate).collect(Collectors.toList()).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(redisResponseMap1.get(i));
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, MAX_TIME, POLL_TIME).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(url);
                responses.forEach(i -> redisResponseMap1.put(i.getKey(), i));
                redisResponses.clear();
                redisResponseMap1.keySet().stream().filter(predicate).collect(Collectors.toList()).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(redisResponseMap1.get(i));
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, MAX_TIME, POLL_TIME).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(redisType, url);
                responses.forEach(i -> redisResponseMap1.put(i.getKey(), i));
                redisResponses.clear();
                redisResponseMap1.keySet().stream().filter(predicate).collect(Collectors.toList()).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(redisResponseMap1.get(i));
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, MAX_TIME, POLL_TIME).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all();
                redisResponses.clear();
                responses.stream().filter(i -> {
                    final Object val$predicate = predicate;
                    return predicate.test(i.getTtl());
                }).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(i);
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, RedisClientConfig.getInstance().getMAX_TIME(), RedisClientConfig.getInstance().getPOLL_TIME()).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(url);
                redisResponses.clear();
                responses.stream().filter(i -> {
                    final Object val$predicate = predicate;
                    return predicate.test(i.getTtl());
                }).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(i);
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, RedisClientConfig.getInstance().getMAX_TIME(), RedisClientConfig.getInstance().getPOLL_TIME()).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(redisType, url);
                redisResponses.clear();
                responses.stream().filter(i -> {
                    final Object val$predicate = predicate;
                    return predicate.test(i.getTtl());
                }).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(i);
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, RedisClientConfig.getInstance().getMAX_TIME(), RedisClientConfig.getInstance().getPOLL_TIME()).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all();
                redisResponses.clear();
                responses.stream().filter(i -> {
                    final Object val$predicate = predicate;
                    return predicate.test(i.getTtl());
                }).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(i);
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, MAX_TIME, POLL_TIME).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(url);
                redisResponses.clear();
                responses.stream().filter(i -> {
                    final Object val$predicate = predicate;
                    return predicate.test(i.getTtl());
                }).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(i);
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, MAX_TIME, POLL_TIME).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = SingleRedisAdapterImpl.this.findBy_all(redisType, url);
                redisResponses.clear();
                responses.stream().filter(i -> {
                    final Object val$predicate = predicate;
                    return predicate.test(i.getTtl());
                }).forEach(i -> {
                    final Object val$redisResponses = redisResponses;
                    redisResponses.add(i);
                    return;
                });
                return !redisResponses.isEmpty();
            }
        };
        new PollingPredicate(callable, MAX_TIME, POLL_TIME).evaluate();
        SingleRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public boolean is_key_present(final String key) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        boolean result = false;
        if (jedis.exists(key)) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("%s key is present", key));
        }
        else {
            SingleRedisAdapterImpl.logger.info(String.format("%s key is not present", key));
        }
        return result;
    }
    
    @Override
    public boolean is_key_present(final String key, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        boolean result = false;
        if (jedis.exists(key)) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("%s key is present", key));
        }
        else {
            SingleRedisAdapterImpl.logger.info(String.format("%s key is not present", key));
        }
        return result;
    }
    
    @Override
    public boolean is_key_present(final String key, final RedisType redisType, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        boolean result = false;
        if (jedis.exists(key)) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("%s key is present", key));
        }
        else {
            SingleRedisAdapterImpl.logger.info(String.format("%s key is not present", key));
        }
        return result;
    }
    
    @Override
    public HashMap<String, Boolean> deleteKeys(final List<String> keys) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        final HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        keys.stream().forEach(x -> {
            if (jedis.del(x) == 1L) {
                SingleRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", x));
                result.put(x, true);
            }
            else {
                result.put(x, false);
                SingleRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", x));
            }
            return;
        });
        return result;
    }
    
    @Override
    public HashMap<String, Boolean> deleteKeys(final String url, final List<String> keys) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        final HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        keys.stream().forEach(x -> {
            if (jedis.del(x) == 1L) {
                SingleRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", x));
                result.put(x, true);
            }
            else {
                result.put(x, false);
                SingleRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", x));
            }
            return;
        });
        return result;
    }
    
    @Override
    public HashMap<String, Boolean> deleteKeys(final RedisType redisType, final String url, final List<String> keys) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        final HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        keys.stream().forEach(x -> {
            if (jedis.del(x) == 1L) {
                SingleRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", x));
                result.put(x, true);
            }
            else {
                result.put(x, false);
                SingleRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", x));
            }
            return;
        });
        return result;
    }
    
    @Override
    public boolean deleteKey(final String key) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        boolean result = false;
        if (jedis.del(key) == 1L) {
            SingleRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", key));
            result = true;
        }
        else {
            SingleRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", key));
        }
        return result;
    }
    
    @Override
    public boolean deleteKey(final String url, final String key) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        boolean result = false;
        if (jedis.del(key) == 1L) {
            SingleRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", key));
            result = true;
        }
        else {
            SingleRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", key));
        }
        return result;
    }
    
    @Override
    public boolean deleteKey(final RedisType redisType, final String url, final String key) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        boolean result = false;
        if (jedis.del(key) == 1L) {
            SingleRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", key));
            result = true;
        }
        else {
            SingleRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", key));
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        boolean result = false;
        final String Status = jedis.set(key, value);
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            SingleRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        boolean result = false;
        final String Status = jedis.set(key, value);
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            SingleRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final RedisType redisType, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        boolean result = false;
        final String Status = jedis.set(key, value);
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            SingleRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final long ttl) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(Jedis.class);
        boolean result = false;
        final String Status = jedis.set(key, value, new SetParams().px(ttl));
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            SingleRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final long ttl, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(url, Jedis.class);
        boolean result = false;
        final String Status = jedis.set(key, value, new SetParams().px(ttl));
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            SingleRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final long ttl, final RedisType redisType, final String url) {
        final Jedis jedis = RedisClientConfig.getRedisUtilInstance(redisType, url, Jedis.class);
        boolean result = false;
        final String Status = jedis.set(key, value, new SetParams().px(ttl));
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            SingleRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            SingleRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    static {
        SingleRedisAdapterImpl.redisAdapter = null;
        SingleRedisAdapterImpl.logger = LoggerFactory.getLogger((Class)SingleRedisAdapterImpl.class);
    }
}
