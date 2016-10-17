package com.jira.model.connections;

import java.io.File;
import org.springframework.stereotype.Component;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jira.WebInitializer;
@Component
public class S3Connection {
	public String s3Upload(String imageName, String imageKey) {
		String uploadPath = WebInitializer.LOCATION + imageName;
		String s3bucketName = "avatars-jira";

		AmazonS3 s3client = new AmazonS3Client(
				new BasicAWSCredentials("", ""));
		try {
			System.out.println("Uploading a new object to S3 from a file\n");
			File file = new File(uploadPath);
			s3client.putObject(new PutObjectRequest(s3bucketName, imageKey, file)
					.withCannedAcl(CannedAccessControlList.PublicRead));

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
					+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which " + "means the client encountered "
					+ "an internal error while trying to " + "communicate with S3, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}

		return "https://s3.amazonaws.com/" + s3bucketName + "/" + imageKey;
	}

}
