package com.dynamodb_integration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.dynamodb_integration.repository.PersonRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;

@Profile("test")
@Configuration
@EnableDynamoDBRepositories(basePackageClasses = PersonRepository.class)
public class DynamoDBTestConfig {

  @Bean
  public LocalStackContainer localStackContainer() {
    LocalStackContainer container = new LocalStackContainer()
        .withServices(LocalStackContainer.Service.DYNAMODB);
    container.start();
    return container;
  }


  @Bean
  @Primary
  public AmazonDynamoDB amazonDynamoDB(LocalStackContainer localStackContainer) {
    return AmazonDynamoDBClientBuilder.standard()
        .withEndpointConfiguration(
            new EndpointConfiguration(
                localStackContainer.getEndpointOverride(Service.DYNAMODB).toString(),
                localStackContainer.getRegion()))
        .withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials("accesskey", "secretkey")))
        .build();
  }
}