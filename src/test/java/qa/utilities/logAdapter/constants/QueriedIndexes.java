// 

// 

package qa.utilities.logAdapter.constants;

import java.util.Arrays;

public class QueriedIndexes
{
    private String[] indices;
    
    public static QueriedIndexesBuilder builder() {
        return new QueriedIndexesBuilder();
    }
    
    public String[] getIndices() {
        return this.indices;
    }
    
    public void setIndices(final String[] indices) {
        this.indices = indices;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof QueriedIndexes)) {
            return false;
        }
        final QueriedIndexes other = (QueriedIndexes)o;
        return other.canEqual(this) && Arrays.deepEquals(this.getIndices(), other.getIndices());
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof QueriedIndexes;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * 59 + Arrays.deepHashCode(this.getIndices());
        return result;
    }
    
    @Override
    public String toString() {
        return "QueriedIndexes(indices=" + Arrays.deepToString(this.getIndices()) + ")";
    }
    
    public QueriedIndexes() {
    }
    
    public QueriedIndexes(final String[] indices) {
        this.indices = indices;
    }
    
    public static class QueriedIndexesBuilder
    {
        private String[] indices;
        
        QueriedIndexesBuilder() {
        }
        
        public QueriedIndexesBuilder indices(final String[] indices) {
            this.indices = indices;
            return this;
        }
        
        public QueriedIndexes build() {
            return new QueriedIndexes(this.indices);
        }
        
        @Override
        public String toString() {
            return "QueriedIndexes.QueriedIndexesBuilder(indices=" + Arrays.deepToString(this.indices) + ")";
        }
    }
}
