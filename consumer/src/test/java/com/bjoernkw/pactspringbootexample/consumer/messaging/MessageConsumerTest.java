package com.bjoernkw.pactspringbootexample.consumer.messaging;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.bjoernkw.pactspringbootexample.consumer.port.OrderDTO;
import com.bjoernkw.pactspringbootexample.consumer.port.messaging.EventMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@JsonTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "MessageProvider", providerType = ProviderType.ASYNCH)
class MessageConsumerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Pact(consumer = "MessageConsumer")
    V4Pact message(PactBuilder pactBuilder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("eventId", "1");
        body.stringType("eventType", "order");
        body.stringType("data", "{\"orderId\":\"1\"}");

        return pactBuilder
                .usingLegacyMessageDsl()
                .expectsToReceive("an event")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "message")
    void testMessage(V4Interaction.AsynchronousMessage message) throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.readValue(
                new String(Objects.requireNonNull(message.getContents().component1().getValue()), StandardCharsets.UTF_8),
                ObjectNode.class
        );
        EventMessage eventMessage = EventMessage
                .builder()
                .eventId(objectNode.get("eventId").asText())
                .eventType(objectNode.get("eventType").asText())
                .data(objectNode.get("data").asText())
                .build();

        Assertions.assertEquals("1", eventMessage.getEventId());
        Assertions.assertEquals("order", eventMessage.getEventType());

        OrderDTO orderDTO = objectMapper.readValue(
                eventMessage.getData(),
                OrderDTO.class
        );

        Assertions.assertEquals("1", orderDTO.getOrderId());
    }
}
