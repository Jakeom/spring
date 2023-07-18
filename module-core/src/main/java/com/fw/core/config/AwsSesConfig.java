package com.fw.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AwsSesConfig {

	@Value("${cloud.aws.ses.access-key}")
	private String accessKey;

	@Value("${cloud.aws.ses.secret-key}")
	private String secretKey;

	@Value("${cloud.aws.ses.region}")
	private String region;

	@Bean
	public AmazonSimpleEmailService amazonSimpleEmailService() {
		final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
		final AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
		return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(awsStaticCredentialsProvider).withRegion(region).build();
	}

}
