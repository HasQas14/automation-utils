// 

// 

package qa.s3;

import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;
import java.util.ListIterator;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import java.io.File;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import java.util.Collection;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import java.util.Arrays;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import java.util.ArrayList;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.Bucket;
import java.util.List;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import java.util.Locale;
import org.slf4j.Logger;

public class S3BucketOperations
{
    private static Logger logger;
    
    S3BucketOperations() {
    }
    
    public static S3BucketOperations getInstance() {
        return new S3BucketOperations();
    }
    
    public void createS3Bucket(final String bucketName) {
        final String bucket = bucketName.toLowerCase(Locale.ROOT);
        final CreateBucketRequest bucketRequest = (CreateBucketRequest)CreateBucketRequest.builder().bucket(bucket).build();
        this.getS3Client().createBucket(bucketRequest);
        S3BucketOperations.logger.info(bucket + "Bucket is Created........");
    }
    
    public List<Bucket> listAllBuckets() {
        final ListBucketsRequest listBucketsRequest = (ListBucketsRequest)ListBucketsRequest.builder().build();
        final ListBucketsResponse listBucketsResponse = this.getS3Client().listBuckets(listBucketsRequest);
        return listBucketsResponse.buckets();
    }
    
    public void deleteBuckets(final String... buckets) {
        for (final String bucket : buckets) {
            try {
                if (this.getS3Objects(bucket) != null) {
                    final List<S3Object> listObjects = this.getS3Objects(bucket);
                    listObjects.stream().forEach(x -> this.deleteSingleObject(bucket, x.key()));
                }
                this.getS3Client().deleteBucket((DeleteBucketRequest)DeleteBucketRequest.builder().bucket(bucket).build());
                S3BucketOperations.logger.info(String.format("%s Bucket and Objects are deleted........", bucket));
            }
            catch (final S3Exception e) {
                S3BucketOperations.logger.info(String.format("%s Bucket does not exist", bucket));
                e.printStackTrace();
            }
        }
    }
    
    public List<S3Object> getS3Objects(final String bucketName) {
        final ListObjectsRequest listObjectsRequest = (ListObjectsRequest)ListObjectsRequest.builder().bucket(bucketName).build();
        final ListObjectsResponse listObjectsResponse = this.getS3Client().listObjects(listObjectsRequest);
        final List<S3Object> s3ObjectList = listObjectsResponse.contents();
        return s3ObjectList;
    }
    
    public DeleteObjectResponse deleteSingleObject(final String bucketName, final String objectName) {
        if (this.checkObjectIsPresentInBucket(bucketName, objectName)) {
            final DeleteObjectRequest deleteObjectRequest = (DeleteObjectRequest)DeleteObjectRequest.builder().bucket(bucketName).key(objectName).build();
            final DeleteObjectResponse deleteObjectResponse = this.getS3Client().deleteObject(deleteObjectRequest);
            S3BucketOperations.logger.info(String.format("%s object deleted in %s s3 bucket", objectName, bucketName));
            return deleteObjectResponse;
        }
        assert false : String.format("%s object not deleted in %s s3 bucket", objectName, bucketName);
        return null;
    }
    
    public DeleteObjectsResponse deleteMultipleObjects(final String bucketName, final String... objectsNames) {
        final List<ObjectIdentifier> listObjects = new ArrayList<ObjectIdentifier>();
        Arrays.stream(objectsNames).forEach(x -> {
            if (this.checkObjectIsPresentInBucket(bucketName, x)) {
                listObjects.add(ObjectIdentifier.builder().key(x).build());
            }
            return;
        });
        final DeleteObjectsRequest deleteObjectsRequest = (DeleteObjectsRequest)DeleteObjectsRequest.builder().bucket(bucketName).delete((Delete)Delete.builder().objects((Collection)listObjects).build()).build();
        final DeleteObjectsResponse deleteObjectsResponse = this.getS3Client().deleteObjects(deleteObjectsRequest);
        if (!deleteObjectsResponse.hasDeleted()) {
            S3BucketOperations.logger.info(String.format("%s objects are deleted from %s bucket", objectsNames, bucketName));
            return deleteObjectsResponse;
        }
        assert false : "Objects are not deleted";
        return null;
    }
    
    public CopyObjectResponse copyObject(final String srcBucketName, final String srcObjectName, final String destbucketName, final String destObjectName) {
        final CopyObjectRequest copyObjectRequest = (CopyObjectRequest)CopyObjectRequest.builder().sourceBucket(srcBucketName).sourceKey(srcObjectName).destinationBucket(destbucketName).destinationKey(destObjectName).build();
        CopyObjectResponse copyObjectResponse = null;
        try {
            copyObjectResponse = this.getS3Client().copyObject(copyObjectRequest);
            S3BucketOperations.logger.info(String.format("%s object copied from %s bucket to %s bucket by named as %s", srcObjectName, srcBucketName, destbucketName, destObjectName));
        }
        catch (final NoSuchKeyException | NoSuchBucketException e) {
            assert false : String.format("Given %s Object os not preset in source object %", srcObjectName, srcBucketName);
            e.printStackTrace();
        }
        return copyObjectResponse;
    }
    
    public PutObjectResponse uploadObjectToS3Bucket(final String bucketName, final String filePath, final String fileName) {
        final PutObjectRequest uploadObjectRequest = (PutObjectRequest)PutObjectRequest.builder().bucket(bucketName).key(fileName).build();
        final PutObjectResponse uploadObjectResponse = this.getS3Client().putObject(uploadObjectRequest, RequestBody.fromFile(new File(filePath + fileName)));
        final S3Waiter waiter = this.getS3Client().waiter();
        final HeadObjectRequest requestWait = (HeadObjectRequest)HeadObjectRequest.builder().bucket(bucketName).key(fileName).build();
        waiter.waitUntilObjectExists(requestWait);
        S3BucketOperations.logger.info(String.format("%s object is uploaded to %s bucket", fileName, bucketName));
        return uploadObjectResponse;
    }
    
    public GetObjectResponse downloadObjectFromS3Bucket(final String bucketName, final String objectName, final String pathToDownload) {
        final GetObjectRequest downloadObjectRequest = (GetObjectRequest)GetObjectRequest.builder().bucket(bucketName).key(objectName).build();
        final GetObjectResponse downloadObjectResponseResponse = (GetObjectResponse)this.getS3Client().getObject(downloadObjectRequest, ResponseTransformer.toFile(new File(pathToDownload + objectName)));
        return downloadObjectResponseResponse;
    }
    
    public boolean checkObjectIsPresentInBucket(final String bucketName, final String objectName) {
        try {
            final ListIterator<S3Object> listIterator = this.getS3Objects(bucketName).listIterator();
            while (listIterator.hasNext()) {
                final S3Object object = listIterator.next();
                if (object.key().equals(objectName)) {
                    S3BucketOperations.logger.info(String.format("%s object is present in %s S3 bucket", objectName, bucketName));
                    return true;
                }
            }
            assert false : String.format("%s object is not present in % S3 bucket", objectName, bucketName);
        }
        catch (final NullPointerException e) {
            assert false : String.format("No objects are present in %s S3 bucket", bucketName);
        }
        return false;
    }
    
    private S3Client getS3Client() {
        return S3ClientConfiguration.getInstance().getClient();
    }
    
    private S3Client getS3ClientWithSpecificRegion(final String region) {
        return S3ClientConfiguration.getInstance().getClient(region);
    }
    
    static {
        S3BucketOperations.logger = LoggerFactory.getLogger((Class)S3BucketOperations.class);
    }
}
