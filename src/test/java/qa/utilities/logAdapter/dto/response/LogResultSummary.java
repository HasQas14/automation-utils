// 

// 

package qa.utilities.logAdapter.dto.response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

public class LogResultSummary
{
    private Map<String, LogsResponseGroup> logsGroup;
    
    public LogsResponseGroup getLogResponseGroup(final String matchingIndexName) {
        final AtomicReference<String> temp = new AtomicReference<String>("");
        final AtomicReference<Double> val = new AtomicReference<Double>(0.0);
        this.logsGroup.forEach((k, v) -> {
            final String indexName = k;
            final double percMatch = this.percentageMatch(matchingIndexName, indexName);
            if (val.get() < percMatch) {
                val.set(percMatch);
                temp.set(indexName);
            }
            return;
        });
        return this.logsGroup.get(temp.get());
    }
    
    private double percentageMatch(final String s1, final String s2) {
        if (Objects.isNull(s1) || Objects.isNull(s2) || "".equals(s1)) {
            return 0.0;
        }
        final int[][] result = new int[s2.length() + 1][s1.length() + 1];
        for (int i = 1; i < result.length; ++i) {
            final int[] row = result[i];
            final char c1 = s2.charAt(i - 1);
            for (int j = 1; j < row.length; ++j) {
                final char c2 = s1.charAt(j - 1);
                if (c1 == c2) {
                    final int temp = result[i - 1][j - 1] + 1;
                    result[i][j] = temp;
                }
                else {
                    final int max = Math.max(result[i - 1][j], result[i][j - 1]);
                    result[i][j] = max;
                }
            }
        }
        final int maxMatched = result[s2.length()][s1.length()];
        final int length = s1.length();
        return maxMatched * 100 / (double)length;
    }
    
    public LogResultSummary() {
        this.logsGroup = new ConcurrentHashMap<String, LogsResponseGroup>();
    }
    
    public Map<String, LogsResponseGroup> getLogsGroup() {
        return this.logsGroup;
    }
    
    public void setLogsGroup(final Map<String, LogsResponseGroup> logsGroup) {
        this.logsGroup = logsGroup;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LogResultSummary)) {
            return false;
        }
        final LogResultSummary other = (LogResultSummary)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$logsGroup = this.getLogsGroup();
        final Object other$logsGroup = other.getLogsGroup();
        if (this$logsGroup == null) {
            if (other$logsGroup == null) {
                return true;
            }
        }
        else if (this$logsGroup.equals(other$logsGroup)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof LogResultSummary;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $logsGroup = this.getLogsGroup();
        result = result * 59 + (($logsGroup == null) ? 43 : $logsGroup.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "LogResultSummary(logsGroup=" + this.getLogsGroup() + ")";
    }
}
