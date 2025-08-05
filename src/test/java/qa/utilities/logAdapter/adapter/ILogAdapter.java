// 

// 

package qa.utilities.logAdapter.adapter;

import java.time.Duration;
import java.util.function.Predicate;
import qa.utilities.logAdapter.constants.Namespace;
import qa.utilities.logAdapter.constants.QueriedIndexes;

public interface ILogAdapter<T>
{
    T search_withAndCondition(final QueriedIndexes p0, final Namespace p1, final String p2, final String... p3);
    
    T searchPolling_withAndCondition(final QueriedIndexes p0, final Namespace p1, final Predicate<T> p2, final Duration p3, final Duration p4, final String p5, final String... p6);
    
    T search_withOrCondition(final QueriedIndexes p0, final Namespace p1, final String p2, final String... p3);
    
    T searchPolling_withOrCondition(final QueriedIndexes p0, final Namespace p1, final Predicate<T> p2, final Duration p3, final Duration p4, final String p5, final String... p6);
    
    T search(final QueriedIndexes p0, final Namespace p1, final String p2);
    
    T searchPolling(final QueriedIndexes p0, final Namespace p1, final Predicate<T> p2, final Duration p3, final Duration p4, final String p5);
}
