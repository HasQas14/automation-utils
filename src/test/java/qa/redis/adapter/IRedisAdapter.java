// 

// 

package qa.redis.adapter;

import java.util.HashMap;
import java.time.Duration;
import java.util.function.Predicate;
import java.util.List;
import qa.redis.config.RedisType;

public interface IRedisAdapter<T>
{
    T findBy_key_firstOccurrence(final String p0);
    
    T findBy_key_firstOccurrence(final String p0, final String p1);
    
    T findBy_key_firstOccurrence(final String p0, final RedisType p1, final String p2);
    
    List<T> findBy_key(final String p0);
    
    List<T> findBy_key(final String p0, final String p1);
    
    List<T> findBy_key(final String p0, final RedisType p1, final String p2);
    
    List<T> findBy_all();
    
    List<T> findBy_all(final String p0);
    
    List<T> findBy_all(final RedisType p0, final String p1);
    
    List<T> findBy_Key(final Predicate<String> p0);
    
    List<T> findBy_Key(final Predicate<String> p0, final String p1);
    
    List<T> findBy_Key(final Predicate<String> p0, final RedisType p1, final String p2);
    
    List<T> findBy_Key(final Predicate<String> p0, final Duration p1, final Duration p2);
    
    List<T> findBy_Key(final Predicate<String> p0, final Duration p1, final Duration p2, final String p3);
    
    List<T> findBy_Key(final Predicate<String> p0, final Duration p1, final Duration p2, final RedisType p3, final String p4);
    
    List<T> findBy_ttl(final Predicate<Long> p0);
    
    List<T> findBy_ttl(final Predicate<Long> p0, final String p1);
    
    List<T> findBy_ttl(final Predicate<Long> p0, final RedisType p1, final String p2);
    
    List<T> findBy_ttl(final Predicate<Long> p0, final Duration p1, final Duration p2);
    
    List<T> findBy_ttl(final Predicate<Long> p0, final Duration p1, final Duration p2, final String p3);
    
    List<T> findBy_ttl(final Predicate<Long> p0, final Duration p1, final Duration p2, final RedisType p3, final String p4);
    
    boolean is_key_present(final String p0);
    
    boolean is_key_present(final String p0, final String p1);
    
    boolean is_key_present(final String p0, final RedisType p1, final String p2);
    
    HashMap<String, Boolean> deleteKeys(final List<String> p0);
    
    HashMap<String, Boolean> deleteKeys(final String p0, final List<String> p1);
    
    HashMap<String, Boolean> deleteKeys(final RedisType p0, final String p1, final List<String> p2);
    
    boolean deleteKey(final String p0);
    
    boolean deleteKey(final String p0, final String p1);
    
    boolean deleteKey(final RedisType p0, final String p1, final String p2);
    
    boolean createKey(final String p0, final String p1);
    
    boolean createKey(final String p0, final String p1, final String p2);
    
    boolean createKey(final String p0, final String p1, final RedisType p2, final String p3);
    
    boolean createKey(final String p0, final String p1, final long p2);
    
    boolean createKey(final String p0, final String p1, final long p2, final String p3);
    
    boolean createKey(final String p0, final String p1, final long p2, final RedisType p3, final String p4);
}
