package com.bjoernkw.pactspringbootexample.consumer.rest;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.bjoernkw.pactspringbootexample.consumer.port.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@JsonTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "OrderProvider")
class OrderConsumerTest {

    private final Map<String, String> headers = Map.of("Content-Type", "application/json");

    @Autowired
    private ObjectMapper objectMapper;

    @Pact(consumer = "OrderConsumer")
    RequestResponsePact order(PactDslWithProvider pactDslWithProvider) {
        return pactDslWithProvider
                .given("Order exists")
                .uponReceiving("Retrieving order data")
                .path("/1")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(
                        Objects.requireNonNull(
                                new PactDslJsonBody()
                                        .stringType("orderId", "1")
                        )
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "order", pactVersion = PactSpecVersion.V3)
    void testOrder(MockServer mockServer) throws IOException {
        HttpResponse httpResponse = get(mockServer.getUrl() + "/1");
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(equalTo(200)));

        OrderDTO orderDTO = objectMapper.readValue(
                httpResponse.getEntity().getContent(),
                OrderDTO.class
        );

        Assertions.assertEquals("1", orderDTO.getOrderId());
    }

    private HttpResponse get(String url) throws IOException {
        return httpClient().execute(new HttpGet(url));
    }

    private HttpClient httpClient() {
        return HttpClientBuilder
                .create()
                .build();
    }
}
