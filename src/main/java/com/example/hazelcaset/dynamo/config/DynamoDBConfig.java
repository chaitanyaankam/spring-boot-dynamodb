package com.example.hazelcaset.dynamo.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.hazelcaset.dynamo.model.Music;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.hazelcaset.dynamo.repository")
public class DynamoDBConfig {

    @Value("${aws.access-key}")
    private String amazonAWSAccessKey;

    @Value("${aws.secret-key}")
    private String amazonAWSSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Autowired
    private ApplicationContext applicationContext;

    Logger LOGGER = LoggerFactory.getLogger(DynamoDBConfig.class);

    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDBClientBuilder builder = configDBClientBuilder();
        return builder.build();
    }

    private AmazonDynamoDBClientBuilder configDBClientBuilder() {
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
        builder.setEndpointConfiguration(amazonEndpointConfiguration());
        builder.withCredentials(amazonAWSCredentialsProvider());
        return builder;
    }

    private AwsClientBuilder.EndpointConfiguration amazonEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, awsRegion);
    }

    @EventListener(classes = {ApplicationReadyEvent.class})
    public void init() throws Exception {
        LOGGER.info("creating tables ..............");
        AmazonDynamoDB amazonDynamoDB = (AmazonDynamoDB) applicationContext.getBean("amazonDynamoDB");
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(Music.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);
    }

    @EventListener(classes = {ContextClosedEvent.class})
    public void destroy() throws Exception {
        LOGGER.info("deleting tables ..............");
        AmazonDynamoDB amazonDynamoDB = (AmazonDynamoDB) applicationContext.getBean("amazonDynamoDB");
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        DeleteTableRequest deleteTableRequest = dynamoDBMapper.generateDeleteTableRequest(Music.class);
        amazonDynamoDB.deleteTable(deleteTableRequest);
    }
}
