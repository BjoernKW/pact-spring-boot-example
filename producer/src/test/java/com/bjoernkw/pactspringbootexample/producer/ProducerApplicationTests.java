package com.bjoernkw.pactspringbootexample.producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class ProducerApplicationTests {

	@Test
	void contextLoads() {
	}

}
