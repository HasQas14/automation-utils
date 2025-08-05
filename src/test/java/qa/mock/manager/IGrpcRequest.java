// 

// 

package qa.mock.manager;

import qa.mock.dto.GrpcResponse;
import qa.mock.dto.GrpcRequest;

import java.net.URISyntaxException;

public interface IGrpcRequest
{
    GrpcResponse do_grpcRequest(final GrpcRequest p0) throws URISyntaxException;
}
