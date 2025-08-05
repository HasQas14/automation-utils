package qa.delete.client;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.time.Duration;
import qa.generic.ClientUtility;

public final class KafkaClientUtility implements ClientUtility
{
    private static KafkaClientUtility kafkaClient;
    private Duration MAX_TIME;
    private Duration POLL_DELAY;
    private Logger logger;
    
    public static KafkaClientUtility getInstance() {
        return KafkaClientUtility.kafkaClient;
    }
    
    private KafkaClientUtility() {
        this.MAX_TIME = null;
        this.POLL_DELAY = null;
        this.logger = LoggerFactory.getLogger((Class)KafkaClientUtility.class);
        KafkaClientConfig.getInstance().lookUpForConfiguration();
        this.logger.info("Kafka consumer client initialized");
    }
    
    @Override
    public boolean initialize() {
        if (KafkaClientConfig.getInstance().configurationStatus()) {
            this.logger.info("Kafka consumer client configuration SUCCESS");
            return true;
        }
        this.logger.error("Kafka consumer client configuration FAILURE");
        return false;
    }
    
    public void setDelay_Durations(final Duration MAX_TIME, final Duration POLL_DELAY) {
        this.MAX_TIME = MAX_TIME;
        this.POLL_DELAY = POLL_DELAY;
    }
    
    public Duration getMAX_TIME() {
        return this.MAX_TIME;
    }
    
    public Duration getPOLL_DELAY() {
        return this.POLL_DELAY;
    }
    
    static {
        KafkaClientUtility.kafkaClient = null;
        KafkaClientUtility.kafkaClient = new KafkaClientUtility();
    }
}
