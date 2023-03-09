package com.bjoernkw.pactspringbootexample.consumer.port.messaging;

import io.awspring.cloud.sns.annotation.handlers.NotificationMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SqsSubscriber {

    @SqsListener(value = "${sqs.queue}")
    public void subscribeToSQS(final @NotificationMessage EventMessage eventMessage) {
        log.info("Event received: {}", eventMessage);
    }
}
