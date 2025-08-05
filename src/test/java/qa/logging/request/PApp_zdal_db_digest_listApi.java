package qa.logging.request;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import qa.logging.dto.DBRecord;
import qa.logging.dto.DBRecords;
import qa.generic.api.CustomHttpResponse;
import qa.generic.api.MethodType;
import qa.generic.api.HttpExec;
import qa.logging.LoggingConfigHolders;

import java.io.UnsupportedEncodingException;

public class PApp_zdal_db_digest_listApi extends HttpExec
{
    private String traceId;
    private String appName;

    public PApp_zdal_db_digest_listApi(final String traceId, final String appName) throws URISyntaxException {
        super(MethodType.POST, LoggingConfigHolders.P_QA_LOGGING_SERVICE_URL_VALUE, "/log_trace/zdal_db_digest_list.json", Map.class);
        this.traceId = traceId;
        this.appName = appName;
    }

    @Override
    public CustomHttpResponse<DBRecords> execute() {
        final String body = "traceId=" + this.traceId + "&currAppName=" + this.appName + "&limit=200&pageIndex=0&startNum=0";
        try {
            this.setNewBody(body);
            this.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            final CustomHttpResponse<Map> temp = (CustomHttpResponse<Map>)super.execute();
            // Cast to CustomHttpResponse<DBRecords> carefully
            return this.prepareResp((CustomHttpResponse<DBRecords>)(CustomHttpResponse<?>) temp);
        }
        catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomHttpResponse<DBRecords> prepareResp(final CustomHttpResponse<DBRecords> customHttpResponse) {
        final Map map = (Map) customHttpResponse.getBody();
        final LinkedList<DBRecord> dbRecords = new LinkedList<>();
        if (map.containsKey("isSuccess") && Boolean.parseBoolean(map.get("isSuccess").toString())) {
            if (map.get("rows") instanceof ArrayList) {
                ((ArrayList<?>) map.get("rows")).forEach(item -> {
                    final ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    final DBRecord dr = mapper.convertValue(item, DBRecord.class);
                    dbRecords.add(dr);
                });
            }
            customHttpResponse.setBody(DBRecords.builder()
                    .dbRecords(dbRecords)
                    .isSuccess(true)
                    .result_size(((ArrayList<?>) map.get("rows")).size())
                    .build());
        } else {
            customHttpResponse.setBody(DBRecords.builder()
                    .isSuccess(false)
                    .result_size(0)
                    .build());
        }
        return customHttpResponse;
    }
}
