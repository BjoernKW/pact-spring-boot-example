package com.bjoernkw.pactspringbootexample.producer.port.messaging;

import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

  private final SqsPublisher sqsPublisher;

  public EventPublisher(SqsPublisher sqsPublisher) {
    this.sqsPublisher = sqsPublisher;
  }

  public void publishEvent(EventMessage eventMessage) {
    sqsPublisher.send(eventMessage);
  }
}
