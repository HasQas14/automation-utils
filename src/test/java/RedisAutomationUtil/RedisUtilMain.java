package RedisAutomationUtil;

public class RedisUtilMain {
    private static final String key = "";
    private static final String value = "";
    public static final String configPath = "src/main/resources/configs.properties"; // please create ur own config.properties in resources

    public static void main(String args[]){
        boolean successfulRedisConnection = RedisPingTest.redisTest();
        if(successfulRedisConnection){
            PrintRedisHost.print();
            AddKeyInRedis.add(key, value);
            FetchValueFromRedis.fetch(key);
        }
        else{
            System.out.println("Could not connect to redis");
        }
    }
}
