package com.orecic.orderbook.application.controllers;

import com.orecic.orderbook.application.controllers.data.OrderRequest;
import com.orecic.orderbook.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public void createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.create(orderRequest);
    }

}
