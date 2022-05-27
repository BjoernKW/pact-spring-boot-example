package com.bjoernkw.pactspringbootexample.producer.rest;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import com.bjoernkw.pactspringbootexample.producer.port.OrderDTO;
import com.bjoernkw.pactspringbootexample.producer.port.rest.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Provider("OrderProvider")
@PactFolder("../consumer/target/pacts")
@Slf4j
class OrderProviderTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext pactVerificationContext) {
        pactVerificationContext.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext pactVerificationContext) {
        Mockito
                .when(orderService.getOrder("1"))
                .thenReturn(
                        OrderDTO
                                .builder()
                                .orderId("1")
                                .build()
                );

        // Set the MockMvc context as the Pact test target (instead of an actual application context).
        pactVerificationContext.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @State("Order exists")
    public void orderExistsState() {
        log.info("Order exists callback");
    }
}
