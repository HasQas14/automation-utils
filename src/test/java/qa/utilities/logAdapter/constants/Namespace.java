// 

// 

package qa.utilities.logAdapter.constants;

public class Namespace
{
    private String namespaceValue;
    private String namespaceKeyName;
    
    private static String $default$namespaceKeyName() {
        return "kubernetes.namespace_name";
    }
    
    public static NamespaceBuilder builder() {
        return new NamespaceBuilder();
    }
    
    public String getNamespaceValue() {
        return this.namespaceValue;
    }
    
    public String getNamespaceKeyName() {
        return this.namespaceKeyName;
    }
    
    public void setNamespaceValue(final String namespaceValue) {
        this.namespaceValue = namespaceValue;
    }
    
    public void setNamespaceKeyName(final String namespaceKeyName) {
        this.namespaceKeyName = namespaceKeyName;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Namespace)) {
            return false;
        }
        final Namespace other = (Namespace)o;
        if (!other.canEqual(this)) {
            return false;
        }
        final Object this$namespaceValue = this.getNamespaceValue();
        final Object other$namespaceValue = other.getNamespaceValue();
        Label_0065: {
            if (this$namespaceValue == null) {
                if (other$namespaceValue == null) {
                    break Label_0065;
                }
            }
            else if (this$namespaceValue.equals(other$namespaceValue)) {
                break Label_0065;
            }
            return false;
        }
        final Object this$namespaceKeyName = this.getNamespaceKeyName();
        final Object other$namespaceKeyName = other.getNamespaceKeyName();
        if (this$namespaceKeyName == null) {
            if (other$namespaceKeyName == null) {
                return true;
            }
        }
        else if (this$namespaceKeyName.equals(other$namespaceKeyName)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof Namespace;
    }
    
    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $namespaceValue = this.getNamespaceValue();
        result = result * 59 + (($namespaceValue == null) ? 43 : $namespaceValue.hashCode());
        final Object $namespaceKeyName = this.getNamespaceKeyName();
        result = result * 59 + (($namespaceKeyName == null) ? 43 : $namespaceKeyName.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "Namespace(namespaceValue=" + this.getNamespaceValue() + ", namespaceKeyName=" + this.getNamespaceKeyName() + ")";
    }
    
    public Namespace(final String namespaceValue, final String namespaceKeyName) {
        this.namespaceValue = namespaceValue;
        this.namespaceKeyName = namespaceKeyName;
    }
    
    public Namespace() {
        this.namespaceKeyName = $default$namespaceKeyName();
    }
    
    public static class NamespaceBuilder
    {
        private String namespaceValue;
        private boolean namespaceKeyName$set;
        private String namespaceKeyName$value;
        
        NamespaceBuilder() {
        }
        
        public NamespaceBuilder namespaceValue(final String namespaceValue) {
            this.namespaceValue = namespaceValue;
            return this;
        }
        
        public NamespaceBuilder namespaceKeyName(final String namespaceKeyName) {
            this.namespaceKeyName$value = namespaceKeyName;
            this.namespaceKeyName$set = true;
            return this;
        }
        
        public Namespace build() {
            String namespaceKeyName$value = this.namespaceKeyName$value;
            if (!this.namespaceKeyName$set) {
                namespaceKeyName$value = $default$namespaceKeyName();
            }
            return new Namespace(this.namespaceValue, namespaceKeyName$value);
        }
        
        @Override
        public String toString() {
            return "Namespace.NamespaceBuilder(namespaceValue=" + this.namespaceValue + ", namespaceKeyName$value=" + this.namespaceKeyName$value + ")";
        }
    }
}
