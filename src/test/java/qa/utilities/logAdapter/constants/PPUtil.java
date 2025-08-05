// 

// 

package qa.utilities.logAdapter.constants;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.concurrent.Callable;

public class PPUtil<T>
{
    private Callable<T> tCallable;
    private Predicate<T> tPredicate;
    private Duration MAX_TIME;
    private Duration POLL_TIME;
    
    public PPUtil(final Callable<T> tCallable) {
        this.MAX_TIME = Duration.ofSeconds(1L);
        this.POLL_TIME = Duration.ofMillis(500L);
        this.tCallable = tCallable;
        this.tPredicate = null;
    }
    
    public PPUtil(final Callable<T> tCallable, final Predicate<T> tPredicate) {
        this.MAX_TIME = Duration.ofSeconds(1L);
        this.POLL_TIME = Duration.ofMillis(500L);
        this.tCallable = tCallable;
        this.tPredicate = tPredicate;
    }
    
    public PPUtil(final Callable<T> tCallable, final Duration MAX_TIME, final Duration POLL_TIME) {
        this(tCallable);
        this.MAX_TIME = MAX_TIME;
        this.POLL_TIME = POLL_TIME;
    }
    
    public PPUtil(final Callable<T> tCallable, final Predicate<T> tPredicate, final Duration MAX_TIME, final Duration POLL_TIME) {
        this(tCallable, tPredicate);
        this.MAX_TIME = MAX_TIME;
        this.POLL_TIME = POLL_TIME;
    }
    
    public T evaluate() {
        final long maxToGo = this.MAX_TIME.toMillis() + System.currentTimeMillis();
        do {
            try {
                final T resp = this.tCallable.call();
                if (resp instanceof Boolean) {
                    if ((Boolean) resp) {
                        return resp;
                    }
                }
                else if (this.tPredicate.test(resp)) {
                    return resp;
                }
                Thread.sleep(this.POLL_TIME.toMillis());
            }
            catch (final Exception ex) {}
        } while (maxToGo > System.currentTimeMillis());
        return null;
    }
}
