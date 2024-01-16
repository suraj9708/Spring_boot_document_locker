package com.demo.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3config {
	@Value("${bucket.access-key}")
	private String bktAccessKey;

	@Value("${bucket.secret-access-key}")
	private String bktSecretKey;

	@Value("${bucket.region}")
	private String bktRegion;

	@Bean
	public AmazonS3 createAmazonS3Client() {

		AWSCredentials credentials = new BasicAWSCredentials(bktAccessKey, bktSecretKey);

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.fromName(bktRegion))
				.build();

		return s3Client;

	}

}
