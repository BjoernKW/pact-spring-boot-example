package com.bjoernkw.pactspringbootexample.consumer.port.messaging;

import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SqsSubscriber {

    @SqsListener(value = "${sqs.queue}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void subscribeToSQS(final @NotificationMessage EventMessage eventMessage) {
        log.info("Event received: {}", eventMessage);
    }
}
