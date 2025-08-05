// 

// 

package qa.s3;

import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import org.slf4j.Logger;
import java.util.LinkedList;
import software.amazon.awssdk.services.s3.S3Client;
import java.util.Map;

public class S3ClientConfiguration
{
    private static final Map<String, S3Client> s3ClientMap;
    private static final LinkedList<String> s3ClientList;
    private static Logger logger;
    private static final S3ClientConfiguration instance;
    static final /* synthetic */ boolean assertionsDisabled = false;
    
    private S3ClientConfiguration() {
    }
    
    public static S3ClientConfiguration getInstance() {
        return S3ClientConfiguration.instance;
    }
    
    public void initialize(final String awsAccessKey, final String awsSecretKey, final String region) {
        if (!S3ClientConfiguration.s3ClientMap.containsKey(region)) {
            final S3Client s3Client = this.createS3Client(awsAccessKey, awsSecretKey, region);
            this.addClient(region, s3Client);
            S3ClientConfiguration.logger.info(String.format("S3Client successfully initialized for %s", region));
        }
        else {
            S3ClientConfiguration.logger.info(String.format("S3Client already initialized for %s", region));
        }
    }
    
    public S3Client getClient() {
        if (S3ClientConfiguration.s3ClientList.isEmpty() && !S3ClientConfiguration.assertionsDisabled) {
            throw new AssertionError((Object)"S3Client instance not available");
        }
        return S3ClientConfiguration.s3ClientMap.get(S3ClientConfiguration.s3ClientList.get(0));
    }
    
    private void addClient(final String region, final S3Client client) {
        S3ClientConfiguration.s3ClientMap.put(region, client);
        S3ClientConfiguration.s3ClientList.add(region);
    }
    
    private S3Client createS3Client(final String awsAccessKey, final String awsSecretKey, final String region) {
        final AwsCredentials credentials = (AwsCredentials)AwsBasicCredentials.create(awsAccessKey, awsSecretKey);
        final S3Client s3Client = (S3Client)((S3ClientBuilder)((S3ClientBuilder)S3Client.builder().region(Region.of(region))).credentialsProvider((AwsCredentialsProvider)StaticCredentialsProvider.create(credentials))).build();
        return s3Client;
    }
    
    private void removeS3Client(final String region) {
        S3ClientConfiguration.s3ClientMap.remove(region);
        S3ClientConfiguration.s3ClientList.remove(region);
    }
    
    public S3Client getClient(final String region) {
        if (S3ClientConfiguration.s3ClientMap.containsKey(region)) {
            return S3ClientConfiguration.s3ClientMap.get(region);
        }
        assert false : String.format("S3Client not available for given connection string: %s", region);
        return null;
    }
    
    static {
        s3ClientMap = new ConcurrentHashMap<String, S3Client>();
        s3ClientList = new LinkedList<String>();
        S3ClientConfiguration.logger = LoggerFactory.getLogger((Class)S3ClientConfiguration.class);
        instance = new S3ClientConfiguration();
    }
}
