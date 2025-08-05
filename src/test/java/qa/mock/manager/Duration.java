// 

// 

package qa.mock.manager;

import java.time.temporal.TemporalUnit;
import java.time.temporal.ChronoUnit;
import java.time.Instant;

public enum Duration
{
    LAST_TEN_MINUTES(Instant.now().minus(10L, (TemporalUnit)ChronoUnit.MINUTES).getEpochSecond()), 
    LAST_FIFTEEN_MINUTES(Instant.now().minus(15L, (TemporalUnit)ChronoUnit.MINUTES).getEpochSecond()), 
    LAST_THIRTY_MINUTES(Instant.now().minus(30L, (TemporalUnit)ChronoUnit.MINUTES).getEpochSecond()), 
    LAST_ONE_HOUR(Instant.now().minus(1L, (TemporalUnit)ChronoUnit.HOURS).getEpochSecond());
    
    private long timeStamp;
    
    private Duration(final long epochSecond) {
        this.timeStamp = epochSecond;
    }
    
    public long getTimeStamp() {
        return this.timeStamp;
    }
}
