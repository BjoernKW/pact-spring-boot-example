package com.bjoernkw.pactspringbootexample.producer.port.messaging;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqsPublisher {

    private final SqsTemplate sqsTemplate;

    public SqsPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    @Value("${sqs.queue}")
    private String queue;

    public void send(EventMessage eventMessage) {
        sqsTemplate.send(queue, eventMessage);
    }
}
