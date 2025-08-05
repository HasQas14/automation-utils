// 

// 

package qa.logging.request;

import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Arrays;
import qa.logging.dto.LogLine;
import java.util.LinkedList;
import org.jsoup.nodes.Element;
import qa.logging.dto.LogFileResponse;
import java.util.ArrayList;
import qa.logging.dto.Status;
import org.jsoup.nodes.Document;
import qa.logging.dto.LogAppResponse;
import qa.generic.api.CustomHttpResponse;
import qa.logging.LoggingConfigHolders;
import qa.generic.api.MethodType;
import qa.generic.api.HttpExec;

public class PApp_InfoRequestApi extends HttpExec
{
    private String traceId;
    private String appName;
    
    public PApp_InfoRequestApi(final String traceId, final String appName) throws URISyntaxException {
        super(MethodType.GET, LoggingConfigHolders.P_QA_LOGGING_SERVICE_URL_VALUE, "/log_trace/app_info.htm", String.class);
        this.traceId = traceId;
        this.appName = appName;
    }
    
    @Override
    public CustomHttpResponse<LogAppResponse> execute() {
        this.setQueryParam("isTiny", false);
        this.setQueryParam("traceId", this.traceId);
        this.setQueryParam("appName", this.appName);
        final CustomHttpResponse<Document> customHttpResponse = super.execute();
        return this.prepareResponse(customHttpResponse);
    }
    
    private CustomHttpResponse<LogAppResponse> prepareResponse(final CustomHttpResponse<Document> documentCustomHttpResponse) {
        final CustomHttpResponse.CustomHttpResponseBuilder<LogAppResponse> builder = CustomHttpResponse.builder();
        final LogAppResponse.LogAppResponseBuilder logAppResponseBuilder = LogAppResponse.builder();
        if (documentCustomHttpResponse.getMessage().equalsIgnoreCase("SUCCESS")) {
            logAppResponseBuilder.status(Status.builder().RESPONSE_CODE(documentCustomHttpResponse.getStatus()).RESPONSE_MESSAGE(documentCustomHttpResponse.getMessage()).build());
            if (null != documentCustomHttpResponse.getBody().getElementsByAttributeValue("id", "tab-1") && documentCustomHttpResponse.getBody().getElementsByAttributeValue("id", "tab-1").size() == 1) {
                final List<LogFileResponse> logFileResponses = new ArrayList<LogFileResponse>();
                ((Element)documentCustomHttpResponse.getBody().getElementsByAttributeValue("id", "tab-1").get(0)).getElementsByClass("search-result").forEach(item -> {
                    final LogFileResponse.LogFileResponseBuilder fileResponseBuilder = LogFileResponse.builder();
                    final Elements filehead = item.getElementsByTag("h3");
                    if (null != filehead && !filehead.isEmpty()) {
                        final String[] arr = filehead.text().split(" ");
                        if (null != arr && arr.length > 0) {
                            fileResponseBuilder.file_name(arr[0]);
                            logAppResponseBuilder.server_address(arr[arr.length - 1]);
                        }
                    }
                    if (null != item.getElementsByClass("contnt-pre") && !item.getElementsByClass("contnt-pre").isEmpty()) {
                        final List<TextNode> logNodes = ((Element)item.getElementsByClass("contnt-pre").get(0)).textNodes();
                        logNodes.forEach(i -> {
                            final LinkedList<LogLine> logLines = new LinkedList<LogLine>();
                            final String val = i.attr("#text");
                            Arrays.stream(val.split("\n")).forEach(line -> logLines.add(LogLine.builder().log(line).build()));
                            fileResponseBuilder.logLines(logLines);
                            return;
                        });
                    }
                    logFileResponses.add(fileResponseBuilder.build());
                    return;
                });
                logAppResponseBuilder.logFileResponses(logFileResponses);
            }
            return builder.message(documentCustomHttpResponse.getMessage()).headers(documentCustomHttpResponse.getHeaders()).status(documentCustomHttpResponse.getStatus()).body(logAppResponseBuilder.build()).build();
        }
        logAppResponseBuilder.status(Status.builder().RESPONSE_CODE(documentCustomHttpResponse.getStatus()).RESPONSE_MESSAGE(documentCustomHttpResponse.getMessage()).build());
        return builder.message(documentCustomHttpResponse.getMessage()).headers(documentCustomHttpResponse.getHeaders()).status(documentCustomHttpResponse.getStatus()).body(logAppResponseBuilder.build()).build();
    }
}
