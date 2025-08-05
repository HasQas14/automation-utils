// 

// 

package qa.utilities.logAdapter.initializer;

import java.io.IOException;
import qa.utilities.logAdapter.exception.LogAdapterException;
import java.util.Objects;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClientBuilder;
import org.apache.http.HttpHost;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.ElasticsearchTransport;
import org.elasticsearch.client.RestClient;

public final class EsConnectionInitializer
{
    private RestClient restClient;
    private ElasticsearchTransport elasticsearchTransport;
    private ElasticsearchClient elasticsearchClient;
    private JacksonJsonpMapper jacksonJsonpMapper;
    private String host;
    private int port;
    private boolean connectionStatus;
    private Logger logger;
    
    public EsConnectionInitializer(final String host, final int port) {
        this.connectionStatus = false;
        this.logger = LoggerFactory.getLogger((Class)EsConnectionInitializer.class);
        this.host = host;
        this.port = port;
    }
    
    public RestClient getRestClient() {
        return this.restClient;
    }
    
    public ElasticsearchTransport getElasticsearchTransport() {
        return this.elasticsearchTransport;
    }
    
    public ElasticsearchClient getElasticsearchClient() {
        return this.elasticsearchClient;
    }
    
    public boolean isConnectionStatus() {
        return this.connectionStatus;
    }
    
    public EsConnectionInitializer initialize() {
        this.logger.info("Log adapter connection initializing: [{}] [{}]", (Object)this.host, (Object)this.port);
        this.restClient = RestClient.builder(new HttpHost[] { new HttpHost(this.host, this.port) }).setRequestConfigCallback((RestClientBuilder.RequestConfigCallback)new RestClientBuilder.RequestConfigCallback() {
            public RequestConfig.Builder customizeRequestConfig(final RequestConfig.Builder builder) {
                return builder.setConnectTimeout(25000).setSocketTimeout(25000);
            }
        }).build();
        this.jacksonJsonpMapper = new JacksonJsonpMapper();
        this.jacksonJsonpMapper.objectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.elasticsearchTransport = (ElasticsearchTransport)new RestClientTransport(this.restClient, (JsonpMapper)this.jacksonJsonpMapper);
        this.elasticsearchClient = new ElasticsearchClient(this.elasticsearchTransport);
        this.connectionStatus = true;
        this.logger.info("Log adapter connection initialized Successfully: [{}] [{}]", (Object)this.host, (Object)this.port);
        return this;
    }
    
    public EsConnectionInitializer closeConnection() throws LogAdapterException {
        try {
            this.logger.info("Closing connection [{}] : [{}]", (Object)this.host, (Object)this.port);
            if (Objects.nonNull(this.elasticsearchTransport)) {
                this.elasticsearchTransport.close();
            }
            else {
                this.logger.warn("ECT for [{}] : [{}] is either null or already closed", (Object)this.host, (Object)this.port);
            }
        }
        catch (final IOException e) {
            throw new LogAdapterException(e);
        }
        this.connectionStatus = false;
        return this;
    }
}
