package com.bjoernkw.pactspringbootexample.consumer.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class OrderService {

    @Value("${producer.service.url}")
    private String producerServiceURL;

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderDTO getOrder(String orderID) {
        log.info("Getting order {} from producer", orderID);

        ResponseEntity<OrderDTO> orderDTOResponseEntity
                = restTemplate.getForEntity(producerServiceURL + "/" + orderID, OrderDTO.class);

        return new OrderDTO(Objects.requireNonNull(orderDTOResponseEntity.getBody()).getOrderID());
    }
}
