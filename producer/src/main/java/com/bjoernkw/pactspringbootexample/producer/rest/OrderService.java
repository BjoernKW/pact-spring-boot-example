package com.bjoernkw.pactspringbootexample.producer.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    public OrderDTO getOrder(String orderId) {
        log.info("Sending order {} to consumer", orderId);

        return new OrderDTO(orderId);
    }
}
