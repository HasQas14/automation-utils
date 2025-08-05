package qa;

import qa.utilities.logAdapter.dto.response.LogsResponseGroup;
import qa.utilities.logAdapter.dto.response.LogResultSummary;
import qa.utilities.logAdapter.adapter.LogAdapterService;
import java.time.Duration;
import qa.utilities.logAdapter.constants.Namespace;
import qa.utilities.logAdapter.constants.QueriedIndexes;
import qa.utilities.logAdapter.adapter.impl.AdapterConfig;

public class dummy2
{
    public static void main(final String[] args) {
        final String query1 = "20240123011800000956611166674597616";
        final String query2 = "CONSUME-CENTER-MESSAGE-RECEIVE";
        final String query3 = "topic = TP_S_1213_EC_EVENTLOG_8131";
        final LogResultSummary resultSummary1 = LogAdapterService.searchPolling_withAndCondition(AdapterConfig.builder().host("kibana.pg2nonprod.paytm.com").port(9200).recordSize(1000).build(), QueriedIndexes.builder().indices(new String[] { "consumecenter*" }).build(), Namespace.builder().namespaceValue("").build(), r -> !r.getLogsGroup().isEmpty(), Duration.ofMinutes(1L), Duration.ofSeconds(5L), query1, query2, query3);
        final LogsResponseGroup responseGroup = resultSummary1.getLogResponseGroup("consumecenter*");
        System.out.println(responseGroup);
    }
}
