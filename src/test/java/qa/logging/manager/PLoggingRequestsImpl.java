// 

// 

package qa.logging.manager;

import qa.logging.request.PApp_zdal_db_digest_listApi;
import qa.logging.request.PApp_query_app_stack_logs;
import qa.logging.request.PApp_InfoRequestApi;
import qa.logging.dto.DBRecords;
import qa.logging.dto.ExceptionTraces;
import qa.generic.api.CustomHttpResponse;

import java.net.URISyntaxException;
import java.util.ArrayList;
import qa.logging.dto.LogAppResponse;
import java.util.List;

public class PLoggingRequestsImpl implements ILoggingRequests
{
    @Override
    public List<LogAppResponse> getLogs(final String traceId) {
        return null;
    }
    
    @Override
    public List<LogAppResponse> getLogs(final String traceId, final String appName) throws URISyntaxException {
        final _Query_From_P query_from_p = new _Query_From_P();
        final List<LogAppResponse> logAppResponses = new ArrayList<LogAppResponse>();
        final CustomHttpResponse<LogAppResponse> customHttpResponse = query_from_p.getCompletResp(traceId, appName);
        if ("SUCCESS".equalsIgnoreCase(customHttpResponse.getMessage())) {
            logAppResponses.add(customHttpResponse.getBody());
        }
        return logAppResponses;
    }
    
    private class _Query_From_P
    {
        CustomHttpResponse<LogAppResponse> getCompletResp(final String traceId, final String appName) throws URISyntaxException {
            final CustomHttpResponse<LogAppResponse> custResp = this._queryFromInfoApi(traceId, appName);
            custResp.getBody().setExceptionTraces(this._queryExceptionLogs(traceId, appName).getBody());
            custResp.getBody().setDbRecords(this._queryDbRecords(traceId, appName).getBody());
            return custResp;
        }
        
        private CustomHttpResponse<LogAppResponse> _queryFromInfoApi(final String traceId, final String appName) throws URISyntaxException {
            final PApp_InfoRequestApi pApp_infoRequestApi = new PApp_InfoRequestApi(traceId, appName);
            return pApp_infoRequestApi.execute();
        }
        
        private CustomHttpResponse<List<ExceptionTraces>> _queryExceptionLogs(final String traceId, final String appName) throws URISyntaxException {
            final PApp_query_app_stack_logs pApp_query_app_stack_logs = new PApp_query_app_stack_logs(traceId, appName);
            return pApp_query_app_stack_logs.execute();
        }
        
        private CustomHttpResponse<DBRecords> _queryDbRecords(final String traceId, final String appName) throws URISyntaxException {
            final PApp_zdal_db_digest_listApi pApp_zdal_db_digest_listApi = new PApp_zdal_db_digest_listApi(traceId, appName);
            return pApp_zdal_db_digest_listApi.execute();
        }
    }
}
