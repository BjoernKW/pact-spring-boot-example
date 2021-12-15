package com.bjoernkw.pactspringbootexample.producer.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

    public OrderDTO getOrder(String orderID) {
        log.info("Sending order {} to consumer", orderID);

        return new OrderDTO(orderID);
    }
}
