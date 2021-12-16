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

	public static DockerComposeContainer<?> environment =
			new DockerComposeContainer<>(new File("../infrastructure/docker-compose.yml"))
					.withExposedService("localstack", 4566, Wait.forListeningPort())
					.withLocalCompose(true);

	static {
		environment.start();
	}

	@Test
	void contextLoads() {
	}

}
