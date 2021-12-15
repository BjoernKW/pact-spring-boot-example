package com.bjoernkw.pactspringbootexample.producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SNS;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@SpringBootTest
@Testcontainers
class ProducerApplicationTests {

	@Container
	static LocalStackContainer localStack =
			new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
					.withServices(SQS, SNS);

	@Test
	void contextLoads() {
	}

}
