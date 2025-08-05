package qa.generic;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.concurrent.Callable;

public class PollingPredicate<T>
{
    private final Callable<T> tCallable;
    private final Predicate<T> tPredicate;
    private Duration MAX_TIME;
    private Duration POLL_TIME;
    
    public PollingPredicate(final Callable<T> tCallable) {
        this.MAX_TIME = Duration.ofSeconds(1L);
        this.POLL_TIME = Duration.ofMillis(500L);
        this.tCallable = tCallable;
        this.tPredicate = null;
    }
    
    public PollingPredicate(final Callable<T> tCallable, final Predicate<T> tPredicate) {
        this.MAX_TIME = Duration.ofSeconds(1L);
        this.POLL_TIME = Duration.ofMillis(500L);
        this.tCallable = tCallable;
        this.tPredicate = tPredicate;
    }
    
    public PollingPredicate(final Callable<T> tCallable, final Duration MAX_TIME, final Duration POLL_TIME) {
        this(tCallable);
        this.MAX_TIME = MAX_TIME;
        this.POLL_TIME = POLL_TIME;
    }
    
    public PollingPredicate(final Callable<T> tCallable, final Predicate<T> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        this(tCallable, tPredicate);
        this.MAX_TIME = MAX_TIME;
        this.POLL_TIME = POLL_TIME;
    }

    public T evaluate() {
        final long maxToGo = System.currentTimeMillis() + this.MAX_TIME.toMillis();
        do {
            try {
                final T resp = this.tCallable.call();
                if (this.tPredicate == null) {
                    // If no predicate, return resp if it’s truthy (non-null and, if Boolean, true)
                    if (resp != null && Boolean.TRUE.equals(resp)) {
                        return resp;
                    }
                } else if (this.tPredicate.test(resp)) {
                    return resp;
                }
                Thread.sleep(this.POLL_TIME.toMillis());
            } catch (Exception ex) {
                // Log the exception if you want, don't silently ignore
                // e.g., logger.warn("Exception during evaluate()", ex);
            }
        } while (System.currentTimeMillis() < maxToGo);
        return null;
    }
}
