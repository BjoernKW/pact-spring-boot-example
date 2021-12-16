package com.bjoernkw.pactspringbootexample.consumer.port.messaging;

import lombok.Data;

@Data
public class EventMessage {

  private String eventId;

  private String eventType;

  private String data;
}
