package RedisAutomationUtil;

import ConfigLoader.ConfigLoader;
import RedisAdapter.RedisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static RedisAutomationUtil.RedisUtilMain.configPath;

public class AddKeyInRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddKeyInRedis.class);

    public static void add(String key, String value) {
        ConfigLoader configLoader = new ConfigLoader(configPath);
        String redisUrl = configLoader.getProperty("REDIS_URL");
        String redisPassword = configLoader.getProperty("REDIS_PASSWORD");

        RedisAdapter redisAdapter = new RedisAdapter(redisUrl, redisPassword);
        redisAdapter.set(key, value);
        LOGGER.info("successfully set key {}, value {} pair in redis", key, value);
    }
}
