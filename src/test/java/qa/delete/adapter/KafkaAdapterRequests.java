package qa.delete.adapter;

import qa.delete.response.GenerateTopicDataRequest;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.function.Predicate;
import java.util.List;

public interface KafkaAdapterRequests<T>
{
    T findBy(final long p0) throws URISyntaxException;
    
    List<T> findBy(final String p0) throws URISyntaxException;
    
    List<T> findBy_size(final String p0, final int p1);
    
    List<T> findBy_env(final String p0, final String p1);
    
    List<T> findBy_env_size(final String p0, final String p1, final int p2);
    
    List<T> findBy(final String p0, final Predicate<List<T>> p1);
    
    List<T> findBy_size(final String p0, final int p1, final Predicate<List<T>> p2);
    
    List<T> findBy_env(final String p0, final Predicate<List<T>> p1, final String p2);
    
    List<T> findBy_env_size(final String p0, final Predicate<List<T>> p1, final String p2, final int p3);
    
    List<T> findBy(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3);
    
    List<T> findBy_size(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3, final int p4);
    
    List<T> findBy_env(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3, final String p4);
    
    List<T> findBy_env_size(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3, final String p4, final int p5);
    
    List<T> findBy(final String p0, final String p1) throws URISyntaxException;
    
    List<T> findBy_size(final String p0, final String p1, final int p2);
    
    List<T> findBy_env(final String p0, final String p1, final String p2);
    
    List<T> findBy_env_size(final String p0, final String p1, final String p2, final int p3);
    
    List<T> findBy(final String p0, final String p1, final Predicate<List<T>> p2);
    
    List<T> findBy_size(final String p0, final String p1, final Predicate<List<T>> p2, final int p3);
    
    List<T> findBy_env(final String p0, final String p1, final Predicate<List<T>> p2, final String p3);
    
    List<T> findBy_env_size(final String p0, final String p1, final Predicate<List<T>> p2, final String p3, final int p4);
    
    List<T> findBy(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4);
    
    List<T> findBy_size(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final int p5);
    
    List<T> findBy_env(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final String p5);
    
    List<T> findBy_env_size(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final String p5, final int p6);
    
    List<T> findBy(final String p0, final String p1, final String p2) throws URISyntaxException;
    
    List<T> findBy_size(final String p0, final String p1, final String p2, final int p3);
    
    List<T> findBy_env(final String p0, final String p1, final String p2, final String p3);
    
    List<T> findBy_env_size(final String p0, final String p1, final String p2, final String p3, final int p4);
    
    List<T> findBy(final String p0, final String p1, final String p2, final Predicate<List<T>> p3);
    
    List<T> findBy_size(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final int p4);
    
    List<T> findBy_env(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final String p4);
    
    List<T> findBy_env_size(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final String p4, final int p5);
    
    List<T> findBy(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final Duration p4, final Duration p5);
    
    List<T> findBy_size(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final Duration p4, final Duration p5, final int p6);
    
    List<T> findBy_env(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final Duration p4, final Duration p5, final String p6);
    
    List<T> findBy_env_size(final String p0, final String p1, final String p2, final Predicate<List<T>> p3, final Duration p4, final Duration p5, final String p6, final int p7);
    
    List<T> findBy(final String p0, final int p1) throws URISyntaxException;
    
    List<T> findBy_size(final String p0, final int p1, final int p2);
    
    List<T> findBy_env(final String p0, final int p1, final String p2);
    
    List<T> findBy_env_size(final String p0, final int p1, final String p2, final int p3);
    
    List<T> findBy(final String p0, final int p1, final Predicate<List<T>> p2);
    
    List<T> findBy_size(final String p0, final int p1, final Predicate<List<T>> p2, final int p3);
    
    List<T> findBy_env(final String p0, final int p1, final Predicate<List<T>> p2, final String p3);
    
    List<T> findBy_env_size(final String p0, final int p1, final Predicate<List<T>> p2, final String p3, final int p4);
    
    List<T> findBy(final String p0, final int p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4);
    
    List<T> findBy_size(final String p0, final int p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final int p5);
    
    List<T> findBy_env(final String p0, final int p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final String p5);
    
    List<T> findBy_env_size(final String p0, final int p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final String p5, final int p6);
    
    List<T> findBy_topicValue_contains(final String p0);
    
    List<T> findBy_topicValue_contains_size(final String p0, final int p1);
    
    List<T> findBy_topicValue_contains_env(final String p0, final String p1);
    
    List<T> findBy_topicValue_contains_env_size(final String p0, final String p1, final int p2);
    
    List<T> findBy_topicValue_contains(final String p0, final Predicate<List<T>> p1);
    
    List<T> findBy_topicValue_contains_size(final String p0, final Predicate<List<T>> p1, final int p2);
    
    List<T> findBy_topicValue_contains_env(final String p0, final Predicate<List<T>> p1, final String p2);
    
    List<T> findBy_topicValue_contains_env_size(final String p0, final Predicate<List<T>> p1, final String p2, final int p3);
    
    List<T> findBy_topicValue_contains(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3);
    
    List<T> findBy_topicValue_contains_size(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3, final int p4);
    
    List<T> findBy_topicValue_contains_env(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3, final String p4);
    
    List<T> findBy_topicValue_contains_env_size(final String p0, final Predicate<List<T>> p1, final Duration p2, final Duration p3, final String p4, final int p5);
    
    List<T> findBy_topicValue_contains(final String p0, final String p1);
    
    List<T> findBy_topicValue_contains_size(final String p0, final String p1, final int p2);
    
    List<T> findBy_topicValue_contains_env(final String p0, final String p1, final String p2);
    
    List<T> findBy_topicValue_contains_env_size(final String p0, final String p1, final String p2, final int p3);
    
    List<T> findBy_topicValue_contains(final String p0, final String p1, final Predicate<List<T>> p2);
    
    List<T> findBy_topicValue_contains_size(final String p0, final String p1, final Predicate<List<T>> p2, final int p3);
    
    List<T> findBy_topicValue_contains_env(final String p0, final String p1, final Predicate<List<T>> p2, final String p3);
    
    List<T> findBy_topicValue_contains_env_size(final String p0, final String p1, final Predicate<List<T>> p2, final String p3, final int p4);
    
    List<T> findBy_topicValue_contains(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4);
    
    List<T> findBy_topicValue_contains_size(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final int p5);
    
    List<T> findBy_topicValue_contains_env(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final String p5);
    
    List<T> findBy_topicValue_contains_env_size(final String p0, final String p1, final Predicate<List<T>> p2, final Duration p3, final Duration p4, final String p5, final int p6);
    
    boolean createTopicData(final GenerateTopicDataRequest p0);
}
