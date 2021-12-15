package com.bjoernkw.pactspringbootexample.producer.messaging;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("worker")
public class SqsPublisher {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${sqs.queue}")
    private String queue;

    public void send(String message) {
        queueMessagingTemplate.convertAndSend(queue, message);
    }
}