package com.bjoernkw.pactspringbootexample.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class ConsumerApplicationTests {

	private static final String LOCALSTACK_PORT = "4566";

	public static DockerComposeContainer<?> environment =
			new DockerComposeContainer<>(new File("docker-compose.yml"))
					.withEnv("LOCALSTACK_PORT", LOCALSTACK_PORT)
					.withExposedService("localstack", Integer.parseInt(LOCALSTACK_PORT), Wait.forListeningPort())
					.withLocalCompose(true);

	static {
		environment.start();
	}

	@Test
	void contextLoads() {
	}

}
