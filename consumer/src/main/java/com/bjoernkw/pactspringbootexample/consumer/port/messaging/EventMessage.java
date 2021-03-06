package com.bjoernkw.pactspringbootexample.consumer.port.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMessage {

  private String eventId;

  private String eventType;

  private String data;
}
