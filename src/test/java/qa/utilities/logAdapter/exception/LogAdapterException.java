// 

// 

package qa.utilities.logAdapter.exception;

public class LogAdapterException extends Exception
{
    public LogAdapterException() {
    }
    
    public LogAdapterException(final String message) {
        super(message);
    }
    
    public LogAdapterException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public LogAdapterException(final Throwable cause) {
        super(cause);
    }
    
    protected LogAdapterException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
