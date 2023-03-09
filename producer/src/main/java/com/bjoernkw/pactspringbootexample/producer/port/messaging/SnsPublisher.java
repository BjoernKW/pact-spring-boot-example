package com.bjoernkw.pactspringbootexample.producer.port.messaging;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnsPublisher {

    private final SnsTemplate snsTemplate;

    public SnsPublisher(SnsTemplate snsTemplate) {
        this.snsTemplate = snsTemplate;
    }

    @Value("${sns.topic}")
    private String snsTopic;

    public void send(EventMessage eventMessage) {
        this.snsTemplate.sendNotification(snsTopic, eventMessage.getEventType(), eventMessage.getEventId());
    }
}
