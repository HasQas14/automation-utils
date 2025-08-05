// 

// 

package qa.redis.adapter;

import org.slf4j.LoggerFactory;
import redis.clients.jedis.params.SetParams;
import java.time.Duration;
import qa.generic.PollingPredicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import redis.clients.jedis.ConnectionPool;
import java.util.Map;
import redis.clients.jedis.exceptions.JedisClusterOperationException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.HostAndPort;
import java.util.concurrent.atomic.AtomicReference;
import java.util.ArrayList;
import java.util.List;
import qa.redis.config.RedisType;
import qa.redis.response.RedisResponseMapper;
import qa.redis.config.RedisClientConfig;
import redis.clients.jedis.JedisCluster;
import org.slf4j.Logger;
import qa.redis.response.RedisResponse;

public class ClusterRedisAdapterImpl implements IRedisAdapter<RedisResponse>
{
    private static ClusterRedisAdapterImpl redisAdapter;
    private static Logger logger;
    
    private ClusterRedisAdapterImpl() {
    }
    
    public static ClusterRedisAdapterImpl getInstance() {
        if (ClusterRedisAdapterImpl.redisAdapter == null) {
            ClusterRedisAdapterImpl.redisAdapter = new ClusterRedisAdapterImpl();
        }
        return ClusterRedisAdapterImpl.redisAdapter;
    }
    
    @Override
    public RedisResponse findBy_key_firstOccurrence(final String key) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        return RedisResponseMapper.map(key, jedisCluster.get(key), jedisCluster.ttl(key));
    }
    
    @Override
    public RedisResponse findBy_key_firstOccurrence(final String key, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        return RedisResponseMapper.map(key, jedisCluster.get(key), jedisCluster.ttl(key));
    }
    
    @Override
    public RedisResponse findBy_key_firstOccurrence(final String key, final RedisType redisType, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        return RedisResponseMapper.map(key, jedisCluster.get(key), jedisCluster.ttl(key));
    }
    
    @Override
    public List<RedisResponse> findBy_key(final String key) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        redisResponses.add(RedisResponseMapper.map(key, jedisCluster.get(key), jedisCluster.ttl(key)));
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_key(final String key, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        redisResponses.add(RedisResponseMapper.map(key, jedisCluster.get(key), jedisCluster.ttl(key)));
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_key(final String key, final RedisType redisType, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        redisResponses.add(RedisResponseMapper.map(key, jedisCluster.get(key), jedisCluster.ttl(key)));
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_all() {
        final AtomicReference<JedisCluster> jedisCluster = new AtomicReference<JedisCluster>(RedisClientConfig.getRedisUtilInstance(JedisCluster.class));
        final Map<String, ConnectionPool> clusterNodes = jedisCluster.get().getClusterNodes();
        final ArrayList<String> allKeys = new ArrayList<String>();
        clusterNodes.keySet().stream().forEach(x -> {
            final Jedis jedis = new Jedis(HostAndPort.from(x));
            allKeys.addAll(jedis.keys("*"));
            return;
        });
        ClusterRedisAdapterImpl.logger.info("Listing All keys ,values,and ttl in Redis, " + allKeys);
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        allKeys.parallelStream().forEach(x -> {
            try {
                redisResponses.add(RedisResponseMapper.map(x, jedisCluster.get().get(x), jedisCluster.get().ttl(x)));
            }
            catch (final JedisDataException e) {
                ClusterRedisAdapterImpl.logger.info(String.format("%s for the key: %s", new Object[0]), (Object)e.getMessage(), (Object)x);
            }
            catch (final JedisClusterOperationException e2) {
                jedisCluster.set(RedisClientConfig.getRedisUtilInstance(JedisCluster.class));
            }
            return;
        });
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_all(final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        final Map<String, ConnectionPool> clusterNodes = jedisCluster.getClusterNodes();
        final ArrayList<String> allKeys = new ArrayList<String>();
        clusterNodes.keySet().stream().forEach(x -> {
            final Jedis jedis = new Jedis(HostAndPort.from(x));
            allKeys.add(jedis.keys("*").toString());
            return;
        });
        ClusterRedisAdapterImpl.logger.info("Listing All keys,values and TTL in Redis");
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        allKeys.stream().forEach(x -> redisResponses.add(RedisResponseMapper.map(x, jedisCluster.get(x), jedisCluster.ttl(x))));
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_all(final RedisType redisType, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        final Map<String, ConnectionPool> clusterNodes = jedisCluster.getClusterNodes();
        final ArrayList<String> allKeys = new ArrayList<String>();
        clusterNodes.keySet().stream().forEach(x -> {
            final Jedis jedis = new Jedis(HostAndPort.from(x));
            allKeys.add(jedis.keys("*").toString());
            return;
        });
        ClusterRedisAdapterImpl.logger.info("Listing All keys,values and TTL in Redis");
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        allKeys.stream().forEach(x -> redisResponses.add(RedisResponseMapper.map(x, jedisCluster.get(x), jedisCluster.ttl(x))));
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all();
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(redisType, url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all();
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_Key(final Predicate<String> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final Map<String, RedisResponse> redisResponseMap1 = new HashMap<String, RedisResponse>();
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(redisType, url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all();
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(redisType, url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all();
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public List<RedisResponse> findBy_ttl(final Predicate<Long> predicate, final Duration MAX_TIME, final Duration POLL_TIME, final RedisType redisType, final String url) {
        final List<RedisResponse> redisResponses = new ArrayList<RedisResponse>();
        final Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final List<RedisResponse> responses = ClusterRedisAdapterImpl.this.findBy_all(redisType, url);
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
        ClusterRedisAdapterImpl.logger.info("Redis Response: " + redisResponses);
        return redisResponses;
    }
    
    @Override
    public boolean is_key_present(final String key) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        boolean result = false;
        if (jedisCluster.exists(key)) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("%s key is present", key));
        }
        else {
            ClusterRedisAdapterImpl.logger.info(String.format("%s key is not present", key));
        }
        return result;
    }
    
    @Override
    public boolean is_key_present(final String key, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        boolean result = false;
        if (jedisCluster.exists(key)) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("%s key is present", key));
        }
        else {
            ClusterRedisAdapterImpl.logger.info(String.format("%s key is not present", key));
        }
        return result;
    }
    
    @Override
    public boolean is_key_present(final String key, final RedisType redisType, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        boolean result = false;
        if (jedisCluster.exists(key)) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("%s key is present", key));
        }
        else {
            ClusterRedisAdapterImpl.logger.info(String.format("%s key is not present", key));
        }
        return result;
    }
    
    @Override
    public HashMap<String, Boolean> deleteKeys(final List<String> keys) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        final HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        keys.stream().forEach(x -> {
            if (jedisCluster.del(x) == 1L) {
                ClusterRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", x));
                result.put(x, true);
            }
            else {
                result.put(x, false);
                ClusterRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", x));
            }
            return;
        });
        return result;
    }
    
    @Override
    public HashMap<String, Boolean> deleteKeys(final String url, final List<String> keys) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        final HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        keys.stream().forEach(x -> {
            if (jedisCluster.del(x) == 1L) {
                ClusterRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", x));
                result.put(x, true);
            }
            else {
                result.put(x, false);
                ClusterRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", x));
            }
            return;
        });
        return result;
    }
    
    @Override
    public HashMap<String, Boolean> deleteKeys(final RedisType redisType, final String url, final List<String> keys) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        final HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        keys.stream().forEach(x -> {
            if (jedisCluster.del(x) == 1L) {
                ClusterRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", x));
                result.put(x, true);
            }
            else {
                result.put(x, false);
                ClusterRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", x));
            }
            return;
        });
        return result;
    }
    
    @Override
    public boolean deleteKey(final String key) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        boolean result = false;
        if (jedisCluster.del(key) == 1L) {
            ClusterRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", key));
            result = true;
        }
        else {
            ClusterRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", key));
        }
        return result;
    }
    
    @Override
    public boolean deleteKey(final String url, final String key) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        boolean result = false;
        if (jedisCluster.del(key) == 1L) {
            ClusterRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", key));
            result = true;
        }
        else {
            ClusterRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", key));
        }
        return result;
    }
    
    @Override
    public boolean deleteKey(final RedisType redisType, final String url, final String key) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        boolean result = false;
        if (jedisCluster.del(key) == 1L) {
            ClusterRedisAdapterImpl.logger.info(String.format("Successfully deleted the key:%s", key));
            result = true;
        }
        else {
            ClusterRedisAdapterImpl.logger.error(String.format("key '%s' is not deleted and check whether key is present or not", key));
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        boolean result = false;
        final String Status = jedisCluster.set(key, value);
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            ClusterRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        boolean result = false;
        final String Status = jedisCluster.set(key, value);
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            ClusterRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final RedisType redisType, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        boolean result = false;
        final String Status = jedisCluster.set(key, value);
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            ClusterRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final long ttl) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(JedisCluster.class);
        boolean result = false;
        final String Status = jedisCluster.set(key, value, new SetParams().px(ttl));
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            ClusterRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final long ttl, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(url, JedisCluster.class);
        boolean result = false;
        final String Status = jedisCluster.set(key, value, new SetParams().px(ttl));
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            ClusterRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    @Override
    public boolean createKey(final String key, final String value, final long ttl, final RedisType redisType, final String url) {
        final JedisCluster jedisCluster = RedisClientConfig.getRedisUtilInstance(redisType, url, JedisCluster.class);
        boolean result = false;
        final String Status = jedisCluster.set(key, value, new SetParams().px(ttl));
        if (Status.equalsIgnoreCase("OK")) {
            result = true;
            ClusterRedisAdapterImpl.logger.info(String.format("This key '%s' and the value is '%s' created", key, value));
        }
        else {
            ClusterRedisAdapterImpl.logger.error("Could not able to create key value pair");
        }
        return result;
    }
    
    static {
        ClusterRedisAdapterImpl.redisAdapter = null;
        ClusterRedisAdapterImpl.logger = LoggerFactory.getLogger((Class)ClusterRedisAdapterImpl.class);
    }
}
