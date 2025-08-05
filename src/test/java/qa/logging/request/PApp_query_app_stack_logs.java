package qa.logging.request;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import qa.logging.dto.ExceptionTraces;
import qa.generic.api.CustomHttpResponse;
import qa.generic.api.MethodType;
import qa.generic.api.HttpExec;
import qa.logging.LoggingConfigHolders;

public class PApp_query_app_stack_logs extends HttpExec
{
    private String traceId;
    private String appName;

    public PApp_query_app_stack_logs(final String traceId, final String appName) throws URISyntaxException {
        super(MethodType.POST, LoggingConfigHolders.P_QA_LOGGING_SERVICE_URL_VALUE, "/log_trace/query_app_stack_logs.json", Map.class);
        this.traceId = traceId;
        this.appName = appName;
    }

    @Override
    public CustomHttpResponse<List<ExceptionTraces>> execute() {
        final String body = "traceId=" + this.traceId + "&appName=" + this.appName;
        try {
            this.setNewBody(body);
            this.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            final CustomHttpResponse<Map> temp = (CustomHttpResponse<Map>)super.execute();
            // Cast to CustomHttpResponse<List<ExceptionTraces>> safely
            return this.prepareResp((CustomHttpResponse<List<ExceptionTraces>>) (CustomHttpResponse<?>) temp);
        }
        catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomHttpResponse<List<ExceptionTraces>> prepareResp(final CustomHttpResponse<List<ExceptionTraces>> customHttpResponse) {
        final Map map = (Map) customHttpResponse.getBody();
        final List<ExceptionTraces> exceptionTracesList = new ArrayList<>();
        if (map.containsKey("isSuccess") && Boolean.parseBoolean(map.get("isSuccess").toString())) {
            if (map.get("exceptionStackLogs") instanceof ArrayList) {
                ((List<?>) map.get("exceptionStackLogs")).forEach(item -> {
                    final ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    final ExceptionTraces e = mapper.convertValue(item, ExceptionTraces.class);
                    exceptionTracesList.add(e);
                });
            }
            customHttpResponse.setBody(exceptionTracesList);
        } else {
            customHttpResponse.setBody(Collections.emptyList());
        }
        return customHttpResponse;
    }
}
