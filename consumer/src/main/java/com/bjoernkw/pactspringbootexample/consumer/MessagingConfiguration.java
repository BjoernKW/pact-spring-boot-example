package com.bjoernkw.pactspringbootexample.consumer;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.support.NotificationMessageArgumentResolver;
import io.awspring.cloud.messaging.support.converter.NotificationRequestConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MessagingConfiguration {
    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    @Value("${cloud.aws.sqs.endpoint}")
    private String localStackSqsEndpoint;

    @Bean
    public AmazonSQSAsync amazonSqs() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(localStackSqsEndpoint, awsRegion))
                .withCredentials(
                        new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
                .build();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
        return new QueueMessagingTemplate(amazonSqs);
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(AmazonSQSAsync amazonSQS, BeanFactory beanFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MappingJackson2MessageConverter jacksonMessageConverter =
                new MappingJackson2MessageConverter();
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setObjectMapper(objectMapper);
        jacksonMessageConverter.setStrictContentTypeMatch(true);

        List<MessageConverter> payloadArgumentConverters = new ArrayList<>();
        payloadArgumentConverters.add(jacksonMessageConverter);

        NotificationRequestConverter notificationRequestConverter =
                new NotificationRequestConverter(jacksonMessageConverter);

        payloadArgumentConverters.add(notificationRequestConverter);
        payloadArgumentConverters.add(new SimpleMessageConverter());

        CompositeMessageConverter compositeMessageConverter =
                new CompositeMessageConverter(payloadArgumentConverters);

        Assert.notNull(amazonSQS, "amazonSQS is null");
        Assert.notNull(beanFactory, "beanFactory is null");
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        factory.setAmazonSqs(amazonSQS);
        factory.setBeanFactory(beanFactory);

        factory.setArgumentResolvers(List.of(
                new NotificationMessageArgumentResolver(compositeMessageConverter)));

        return factory;
    }
}
