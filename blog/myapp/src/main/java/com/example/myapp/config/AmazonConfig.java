package com.example.myapp.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  @Bean
  public AmazonS3 s3(){
    AWSCredentials awsCredentials = new BasicAWSCredentials(
        "AKIAJ6PWFOTAB5IG75IQ",
        "5PEbiaA1kkb14JEijBNk0ntDFaOQE9mbXzvUmYwG"
    );
    return AmazonS3ClientBuilder.standard().withCredentials(
        new AWSStaticCredentialsProvider(awsCredentials)
    ). withRegion(Regions.US_EAST_2)
        .build();
  }
}
