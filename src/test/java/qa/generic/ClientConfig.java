
package qa.generic;

public interface ClientConfig
{
    public static final String QA_UTILITY_CLIENT_FILE_NAME = "qa_utility_client.properties";
    
    void lookUpForConfiguration();
    
    boolean configurationStatus();
}
