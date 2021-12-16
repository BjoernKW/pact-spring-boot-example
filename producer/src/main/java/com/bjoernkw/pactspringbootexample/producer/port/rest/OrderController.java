package com.bjoernkw.pactspringbootexample.producer.port.rest;

import com.bjoernkw.pactspringbootexample.producer.port.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{orderId}")
    public OrderDTO getOrder(@PathVariable("orderId") String orderId) {
        log.info("HTTP request for placing a new order");

        return orderService.getOrder(orderId);
    }
}
