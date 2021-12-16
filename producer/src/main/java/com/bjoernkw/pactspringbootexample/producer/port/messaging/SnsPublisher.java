package com.bjoernkw.pactspringbootexample.producer.port.messaging;

import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SnsPublisher {

    private final NotificationMessagingTemplate notificationMessagingTemplate;

    @Autowired
    public SnsPublisher(NotificationMessagingTemplate notificationMessagingTemplate) {
        this.notificationMessagingTemplate = notificationMessagingTemplate;
    }

    @Value("${sns.topic}")
    private String snsTopic;

    public void send(String message) {
        this.notificationMessagingTemplate.sendNotification(snsTopic, message, message);
    }
}
