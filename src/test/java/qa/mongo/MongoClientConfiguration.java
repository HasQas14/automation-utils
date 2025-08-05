package qa.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MongoClientConfiguration {

    private static final Map<String, MongoClient> clientMap = new ConcurrentHashMap<>();
    private static final LinkedList<String> clientList = new LinkedList<>();
    private static final Logger logger = LoggerFactory.getLogger(MongoClientConfiguration.class);
    private static final MongoClientConfiguration instance = new MongoClientConfiguration();

    private MongoClientConfiguration() {
    }

    public static MongoClientConfiguration getInstance() {
        return instance;
    }

    public static void initialize(final String mongoConnectionString) {
        if (!clientMap.containsKey(mongoConnectionString)) {
            MongoClient client = createMongoClient(mongoConnectionString);
            addClient(mongoConnectionString, client);
            logger.info(String.format("MongoClient successfully initialized for %s", mongoConnectionString));
        } else {
            logger.info(String.format("MongoClient already initialized for %s", mongoConnectionString));
        }
    }

    public MongoClient getClient() {
        if (clientList.isEmpty()) {
            throw new AssertionError("MongoClient instance not available");
        }
        return clientMap.get(clientList.get(0));
    }

    public MongoClient getClient(final String mongoConnectionString) {
        if (clientMap.containsKey(mongoConnectionString)) {
            return clientMap.get(mongoConnectionString);
        }
        throw new AssertionError(String.format("MongoClient not available for given connection string: %s", mongoConnectionString));
    }

    private static void removeClient(final String mongoConnectionString) {
        clientMap.remove(mongoConnectionString);
        clientList.remove(mongoConnectionString);
    }

    private static void addClient(final String mongoConnectionString, final MongoClient client) {
        clientMap.put(mongoConnectionString, client);
        clientList.add(mongoConnectionString);
    }

    private static MongoClient createMongoClient(final String mongoConnectionString) {
        // Add keepAlive=true in connection string params if not already
        String connectionStringWithKeepAlive = mongoConnectionString;
        if (!connectionStringWithKeepAlive.contains("socketKeepAlive=true")) {
            connectionStringWithKeepAlive += connectionStringWithKeepAlive.contains("?") ? "&" : "?";
            connectionStringWithKeepAlive += "socketKeepAlive=true";
        }

        ConnectionString connectionString = new ConnectionString(connectionStringWithKeepAlive);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(settings);
    }
}
