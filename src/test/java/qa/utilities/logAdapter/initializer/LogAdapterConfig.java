// 

// 

package qa.utilities.logAdapter.initializer;

import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;
import qa.utilities.logAdapter.constants.ImplType;
import java.util.Map;
import org.slf4j.Logger;

public class LogAdapterConfig
{
    private static Logger logger;
    private static final Map<String, EsConnectionInitializer> ADAPTER_CONFIG_MAP;
    
    private LogAdapterConfig() {
    }
    
    public static void initialize(final String host, final int port, final ImplType implType) {
        final String key = key(host, port) + "_" + implType.name();
        if (LogAdapterConfig.ADAPTER_CONFIG_MAP.containsKey(key) && LogAdapterConfig.ADAPTER_CONFIG_MAP.get(key).isConnectionStatus()) {
            LogAdapterConfig.logger.info("connection already exists for host {} port {}", (Object)host, (Object)port);
        }
        else {
            final EsConnectionInitializer initializer = new EsConnectionInitializer(host, port).initialize();
            LogAdapterConfig.ADAPTER_CONFIG_MAP.put(key, initializer);
        }
    }
    
    public static EsConnectionInitializer getAdapterInfo(final String host, final int port, final ImplType implType) {
        final String key = key(host, port) + "_" + implType.name();
        initialize(host, port, implType);
        return LogAdapterConfig.ADAPTER_CONFIG_MAP.get(key);
    }
    
    public static Map<String, EsConnectionInitializer> getAdapterConfigMap() {
        return LogAdapterConfig.ADAPTER_CONFIG_MAP;
    }
    
    private static String key(final String host, final int port) {
        return host + "_" + port;
    }
    
    static {
        LogAdapterConfig.logger = LoggerFactory.getLogger((Class)LogAdapterConfig.class);
        ADAPTER_CONFIG_MAP = new ConcurrentHashMap<String, EsConnectionInitializer>();
    }
}
