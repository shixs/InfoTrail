package stevens.edu.infotrail.amazonS3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Xingsheng
 * @version %I%,%G%
 */
@Stateless
public class AmazonS3Helper {

    private static final String bucketName = "infotrail-resources";
    private static final String folder = "resource/";
    private static final String accessKey = "your access key";
    private static final String secretKey = "your secret key";
    private static final Logger logger = Logger.getLogger(AmazonS3Helper.class.getCanonicalName());
    private AmazonS3 s3;

    /**
     * 
     */
    public AmazonS3Helper() {
        AWSCredentials credentials = null;
        try{
            credentials = new BasicAWSCredentials(accessKey,secretKey);
        }catch (Exception e){
            logger.info("==========Cannot specify the credentials for AWS access.========");
        }
        s3 = new AmazonS3Client(credentials);
        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
        s3.setRegion(usEast1);

        logger.info("======================================");
        logger.info("Getting Started with Amazon S3");
        logger.info("======================================");
        //ClientConfiguration clientConfig = new ClientConfiguration();
        //clientConfig.setProtocol(Protocol.HTTP);
       // clientConfig.setConnectionTimeout(5 * 1000);
        //clientConfig.setMaxErrorRetry(3);
        //clientConfig.setSocketTimeout(5 * 1000);
       // s3Client = new AmazonS3Client(credentials, clientConfig);
    }

    public void uploadObject(File file, String keyName) {
        logger.info("=== Uploading a new object to S3 from a file ===");
        try{
            s3.putObject(new PutObjectRequest(bucketName, folder + keyName, file));
        }catch (AmazonServiceException ase){
            logger.info("===================================================================");
            logger.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            logger.info("===================================================================");
        }catch(AmazonClientException ace){
            logger.info("===================================================================");
            logger.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ace.getMessage());
            logger.info("===================================================================");
        }
    }

    public S3Object getObject(String key){
        logger.info("=== Getting a object ===");
        try{
            return s3.getObject(new GetObjectRequest(bucketName,folder+key));
        }catch (AmazonServiceException ase){
            logger.info("===================================================================");
            logger.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            logger.info("===================================================================");
            return null;
        }catch(AmazonClientException ace){
            logger.info("===================================================================");
            logger.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ace.getMessage());
            logger.info("===================================================================");
            return null;
        }
    }

    public ObjectListing getObjects(){
        try{
            return s3.listObjects(new ListObjectsRequest()
                    .withBucketName(bucketName));
        }catch (AmazonServiceException ase){
            logger.info("===================================================================");
            logger.info("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
            logger.info("===================================================================");
            return null;
        }catch(AmazonClientException ace){
            logger.info("===================================================================");
            logger.info("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ace.getMessage());
            logger.info("===================================================================");
            return null;
        }
    }

    public Image getImage(String key) {
        try {
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, folder + key));
            Image image = ImageIO.read(object.getObjectContent());
            return image;
        } catch (IOException ex) {
            logger.info("IO error");
            return null;
        } catch (AmazonS3Exception ase) {
            logger.info("The specified key does not exist");
            return null;
        }
    }

}
