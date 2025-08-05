package qa.delete.client;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import qa.generic.ClientConfigBase;

public class KafkaClientConfig extends ClientConfigBase
{
    private Logger logger;
    private static KafkaClientConfig kafkaClientConfig;
    public static final String QA_KAFKA_CLIENT_URL_PROP_NAME = "qa.kafka.consumer.service.url";
    public static String QA_KAFKA_CLIENT_URL_PROP_VALUE;
    private static boolean configStatus;
    
    private KafkaClientConfig() {
        this.logger = LoggerFactory.getLogger((Class)KafkaClientConfig.class);
    }
    
    public static KafkaClientConfig getInstance() {
        if (null == KafkaClientConfig.kafkaClientConfig) {
            KafkaClientConfig.kafkaClientConfig = new KafkaClientConfig();
        }
        return KafkaClientConfig.kafkaClientConfig;
    }
    
    @Override
    public void lookUpForConfiguration() {
        this.logger.info(String.format("Configuring KafkaClient", new Object[0]));
        try {
            this.load("qa_utility_client.properties");
        }
        catch (final RuntimeException e) {
            this.logger.info("Configuring kafkaClient through environment variables");
        }
        KafkaClientConfig.QA_KAFKA_CLIENT_URL_PROP_VALUE = this.getProperty("qa.kafka.consumer.service.url");
        this.setConfigStatus();
    }
    
    private void setConfigStatus() {
        KafkaClientConfig.configStatus = (null != KafkaClientConfig.QA_KAFKA_CLIENT_URL_PROP_VALUE && !KafkaClientConfig.QA_KAFKA_CLIENT_URL_PROP_VALUE.isEmpty());
    }
    
    @Override
    public boolean configurationStatus() {
        return KafkaClientConfig.configStatus;
    }
    
    static {
        KafkaClientConfig.kafkaClientConfig = null;
        KafkaClientConfig.configStatus = false;
    }
}
