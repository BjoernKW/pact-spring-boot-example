package com.bjoernkw.pactspringbootexample.producer;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SNS;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@SpringBootTest
@Testcontainers
class ProducerApplicationTests {

	private static final String QUEUE_NAME = "test-queue";

	private static final String TOPIC_NAME = "test-topic";

	@Container
	static LocalStackContainer localStack =
			new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
					.withServices(SQS, SNS);

	@TestConfiguration
	static class AWSTestConfiguration {

		@Bean
		public AmazonSQSAsync amazonSQS() {
			return AmazonSQSAsyncClientBuilder.standard()
					.withEndpointConfiguration(localStack.getEndpointConfiguration(SQS))
					.withCredentials(localStack.getDefaultCredentialsProvider())
					.build();
		}

		@Bean
		public AmazonSNSAsync amazonSNS() {
			return AmazonSNSAsyncClientBuilder.standard()
					.withEndpointConfiguration(localStack.getEndpointConfiguration(SNS))
					.withCredentials(localStack.getDefaultCredentialsProvider())
					.build();
		}
	}

	@BeforeAll
	static void beforeAll() throws IOException, InterruptedException {
		localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", QUEUE_NAME);
		localStack.execInContainer("awslocal", "sns", "create-topic", "--name", TOPIC_NAME);
	}

	@DynamicPropertySource
	static void overrideConfiguration(DynamicPropertyRegistry registry) {
		registry.add("sqs.queue", () -> QUEUE_NAME);
		registry.add("sns.topic", () -> TOPIC_NAME);
		registry.add("cloud.aws.credentials.access-key", localStack::getAccessKey);
		registry.add("cloud.aws.credentials.secret-key", localStack::getSecretKey);
	}

	@Test
	void contextLoads() {
	}

}
