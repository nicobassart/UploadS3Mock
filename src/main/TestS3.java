package main;

 /**
*
 * @author ravi.tyagi
*/
import java.io.File;
import java.io.FileInputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;

public class TestS3 {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String bucketName = "abc";
        String keyName = "provider"; // Also the name path from which you want save on S3 without Extension it will be same source file
        String filePath = "/home/nico/Desktop/Customers/customer1-2019-09-23-afae32/ablehealth-2019-09-23-afae32/provider.csv";
        try {
            //AmazonS3 s3Client = new AmazonS3Client(DefaultAWSCredentialsProviderChain.getInstance());
        	//ClientConfiguration a = new ClientConfiguration();
            //AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();
            
            
            
            BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
                    "333",
                    "3333",
                    "3333");

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
                    //.withRegion("lalal")
                    .withEndpointConfiguration(new EndpointConfiguration("http://127.0.0.1:9090/", "lalal"))
                    .build();
            
            TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3Client).build();
            
            
            File file=new File(filePath);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.length());
            metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, keyName,
                    new FileInputStream(file), metadata);

            s3Client.putObject(putRequest);
            s3Client.createBucket("prueba");
            
            
            
            
			/*
			 * Upload upload = tm.upload(bucketName, keyName, new File(filePath)); //
			 * System.out.println("---****************--->"+s3Client.getUrl(bucketName,
			 * "provider.csv").toString()); upload.waitForCompletion();
			 * //System.out.println("---*--->"+s3Client.getUrl(bucketName,
			 * "provider.csv").toString()); //
			 * System.out.println("--**->"+upload.getProgress()); //
			 * System.out.println("---***--->"+s3Client.getUrl(bucketName,
			 * "provider.csv").toString());
			 * System.out.println("--****->"+upload.getState());
			 * //System.out.println("---*****--->"+s3Client.getUrl(bucketName,
			 * "provider.csv").toString()); System.out.println("--******->0");
			 * System.out.println("Object upload complete");
			 * System.out.println("--**********-->1"); tm.shutdownNow();
			 */
        } catch (AmazonServiceException e) {
            System.out.println("Exception :- "+e);
        }
        System.out.println("----->2");

    }

}