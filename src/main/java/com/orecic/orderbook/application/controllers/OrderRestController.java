package com.orecic.orderbook.application.controllers;

import com.orecic.orderbook.application.controllers.data.OrderRequest;
import com.orecic.orderbook.domain.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderRestController {

    Logger logger = LoggerFactory.getLogger(OrderRestController.class);
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderRequest orderRequest) {
        logger.info("m=createOrder CREATE_ORDER orderRequest={}", orderRequest);
        orderService.createAsync(orderRequest);
        return ResponseEntity.ok().build();
    }

}
