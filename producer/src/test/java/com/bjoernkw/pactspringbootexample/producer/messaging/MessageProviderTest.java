package com.bjoernkw.pactspringbootexample.producer.messaging;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.bjoernkw.pactspringbootexample.producer.port.OrderDTO;
import com.bjoernkw.pactspringbootexample.producer.port.messaging.EventMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

@JsonTest
@Provider("MessageProvider")
@PactFolder("../consumer/target/pacts")
@Slf4j
class MessageProviderTest {

    @Autowired
    private ObjectMapper objectMapper;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext pactVerificationContext) {
        log.info("testTemplate called: {}, {}", pact.getProvider().getName(), interaction);

        pactVerificationContext.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext pactVerificationContext) {
        pactVerificationContext.setTarget(new MessageTestTarget());
    }

    @State("MessageProvider")
    public void messageProviderState() {
        log.info("MessageProviderState callback");
    }

    @PactVerifyProvider("an event")
    String verifyMessage() throws JsonProcessingException {
        OrderDTO orderDTO = OrderDTO
                .builder()
                .orderId("1")
                .build();
        EventMessage eventMessage = EventMessage
                .builder()
                .eventId("1")
                .eventType("order")
                .data(objectMapper.writeValueAsString(orderDTO))
                .build();

        return objectMapper.writeValueAsString(eventMessage);
    }
}
