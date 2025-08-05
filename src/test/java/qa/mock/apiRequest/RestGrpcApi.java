// 

// 

package qa.mock.apiRequest;

import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import qa.mock.MockClientConfig;
import qa.mock.dto.GrpcRequest;
import qa.generic.api.MethodType;
import org.slf4j.Logger;
import qa.generic.api.HttpExec;

public class RestGrpcApi extends HttpExec
{
    private static final Logger logger;
    
    private RestGrpcApi(final MethodType methodType, final String baseUri, final String basePath, final Class clazz) throws URISyntaxException {
        super(methodType, baseUri, basePath, clazz);
    }
    
    public RestGrpcApi(final GrpcRequest grpcRequest) throws URISyntaxException {
        this(MethodType.POST, MockClientConfig.QA_GRPC_MOCK_SERVICE_URL_PROP_VALUE, "rest_grpc/request", String.class);
        try {
            this.setNewBody(grpcRequest.toString());
        }
        catch (final UnsupportedEncodingException e) {
            RestGrpcApi.logger.error("error occurred while setting request body", (Throwable)e);
            assert false;
        }
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)RestGrpcApi.class);
    }
}
