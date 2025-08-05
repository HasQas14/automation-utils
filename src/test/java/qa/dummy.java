// 

// 

package qa;

import qa.redis.config.RedisClientConfig;

public class dummy
{
    public static void main(final String[] args) throws Exception {
        RedisClientConfig.getInstance().init("qa-bom-elasticache.5eyg3r.clustercfg.aps1.cache.amazonaws.com:6379");
        System.out.println();
    }
}
