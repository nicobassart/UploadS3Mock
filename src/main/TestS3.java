package main;

/**
*
* @author ravi.tyagi
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

	public static String host = "http://127.0.0.1:9090/";
	public static String bucketName = "abc";

	public static void main(String[] args) throws Exception {

		String path = "/home/nico/Desktop/Customers/customer1-2019-09-23-afae32/ablehealth-2019-09-23-afae32";
		final File folder = new File(path);
		listFilesForFolder(folder, path, TestS3.bucketName);

	}

	public static void listFilesForFolder(final File folder, String path, String bucketname)
			throws FileNotFoundException {
		for (final File fileEntry : folder.listFiles()) {
			System.out.println(fileEntry.getName());
			extracted(bucketname, fileEntry.getName(), fileEntry.getAbsolutePath());
		}
	}

	private static void extracted(String bucketName, String keyName, String filePath) throws FileNotFoundException {
		try {

			BasicSessionCredentials sessionCredentials = new BasicSessionCredentials("333", "3333", "3333");

			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(sessionCredentials))
					.withEndpointConfiguration(new EndpointConfiguration(TestS3.host, "lalal")).build();

			TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3Client).build();

			File file = new File(filePath);
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.length());
			metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
			PutObjectRequest putRequest = new PutObjectRequest(bucketName, keyName, new FileInputStream(file),
					metadata);

			s3Client.putObject(putRequest);
			s3Client.createBucket("prueba");
		} catch (AmazonServiceException e) {
			System.out.println("Exception :- " + e);
		}
		System.out.println("Done!");
	}

}