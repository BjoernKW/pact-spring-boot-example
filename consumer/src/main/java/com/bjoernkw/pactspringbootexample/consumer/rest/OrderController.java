package com.bjoernkw.pactspringbootexample.consumer.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OrderController.REQUEST_URL)
@Slf4j
public class OrderController {

    static final String REQUEST_URL = "/";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public OrderDTO getOrder() {
        log.info("HTTP request for placing a new order");

        return orderService.getOrder("1");
    }
}
