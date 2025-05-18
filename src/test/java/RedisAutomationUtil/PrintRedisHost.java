package RedisAutomationUtil;

import ConfigLoader.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.URI;

import static RedisAutomationUtil.RedisUtilMain.configPath;

public class PrintRedisHost {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintRedisHost.class);
    public static void print() {
        try {
            ConfigLoader configLoader = new ConfigLoader(configPath);
            String redisUrl = configLoader.getProperty("REDIS_URL");
            String redisPassword = configLoader.getProperty("REDIS_PASSWORD");

            URI uri = new URI(redisUrl);
            String host = uri.getHost();
            InetAddress address = InetAddress.getByName(host);
            System.out.println("Host: " + host);
            System.out.println("Resolved IP: " + address.getHostAddress());
        }
        catch (Exception e){
            LOGGER.error("❌ Cannot connect to Redis:");
            e.printStackTrace();
        }
    }
}
