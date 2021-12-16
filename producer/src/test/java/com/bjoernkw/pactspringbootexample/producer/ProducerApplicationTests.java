package com.bjoernkw.pactspringbootexample.producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class ProducerApplicationTests {

	private static final String POSTGRESQL_PORT = "5433";

	private static final String PACT_PORT = "8082";

	private static final String LOCALSTACK_PORT = "4567";

	public static DockerComposeContainer<?> environment =
			new DockerComposeContainer<>(new File("../infrastructure/docker-compose.yml"))
					.withEnv("POSTGRESQL_PORT", POSTGRESQL_PORT)
					.withEnv("PACT_PORT", PACT_PORT)
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
