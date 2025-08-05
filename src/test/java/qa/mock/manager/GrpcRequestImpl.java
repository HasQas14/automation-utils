// 

// 

package qa.mock.manager;

import org.slf4j.LoggerFactory;
import qa.mock.MockAdapter;
import qa.generic.api.CustomHttpResponse;
import qa.mock.apiRequest.RestGrpcApi;
import qa.mock.dto.GrpcResponse;
import qa.mock.dto.GrpcRequest;
import org.slf4j.Logger;

import java.net.URISyntaxException;

public class GrpcRequestImpl implements IGrpcRequest
{
    private static GrpcRequestImpl grpcRequest;
    private static final Logger logger;
    
    private GrpcRequestImpl() {
    }
    
    public static GrpcRequestImpl getInstance() {
        if (GrpcRequestImpl.grpcRequest == null) {
            GrpcRequestImpl.grpcRequest = new GrpcRequestImpl();
        }
        return GrpcRequestImpl.grpcRequest;
    }
    
    @Override
    public GrpcResponse do_grpcRequest(final GrpcRequest grpcRequest) throws URISyntaxException {
        assert null != grpcRequest : "grpcRequest cannot be null";
        final GrpcResponse response = new GrpcResponse();
        final RestGrpcApi restGrpcApi = new RestGrpcApi(grpcRequest);
        final CustomHttpResponse resp = restGrpcApi.execute();
        switch (resp.getStatus()) {
            case 400:
            case 500: {
                response.setStatus("ERROR");
                response.setResponse_code(500);
                break;
            }
            default: {
                response.setStatus(resp.getMessage());
                response.setResponse_code(resp.getStatus());
                response.setBody((String) resp.getBody());
                break;
            }
        }
        return response;
    }
    
    static {
        logger = LoggerFactory.getLogger((Class)MockAdapter.class);
    }
}
