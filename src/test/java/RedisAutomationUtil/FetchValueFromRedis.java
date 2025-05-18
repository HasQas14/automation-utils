package RedisAutomationUtil;

import ConfigLoader.ConfigLoader;
import RedisAdapter.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static RedisAutomationUtil.RedisUtilMain.configPath;

public class FetchValueFromRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchValueFromRedis.class);

    public static void fetch(String key) {
        ConfigLoader configLoader = new ConfigLoader(configPath);
        String redisUrl = configLoader.getProperty("REDIS_URL");
        String redisPassword = configLoader.getProperty("REDIS_PASSWORD");

        RedisAdapter redisAdapter = new RedisAdapter(redisUrl, redisPassword);
        String valueInRedis = redisAdapter.get(key);
        LOGGER.info("mapped value for key {}: {}", key, valueInRedis);
    }
}
