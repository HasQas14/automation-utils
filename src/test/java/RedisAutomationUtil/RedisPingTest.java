package RedisAutomationUtil;

import ConfigLoader.ConfigLoader;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static RedisAutomationUtil.RedisUtilMain.configPath;

public class RedisPingTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisPingTest.class);
    public static boolean redisTest() {
        try {
            ConfigLoader configLoader = new ConfigLoader(configPath);
            String redisUrl = configLoader.getProperty("REDIS_URL");
            String redisPassword = configLoader.getProperty("REDIS_PASSWORD");

            // rediss:// if it uses encrypted redis otherwise redis://
            RedisClient client = RedisClient.create("rediss://"+redisPassword+"@"+redisUrl);
            StatefulRedisConnection<String, String> connection = client.connect();
            RedisCommands<String, String> commands = connection.sync();
            LOGGER.info("Redis PING response: {}\nSuccessfully connected to redis!", commands.ping());
            connection.close(); client.shutdown();
            return true;
        } catch (Exception e) {
            LOGGER.error("❌ Cannot connect to Redis");
            return false;
        }
    }
}