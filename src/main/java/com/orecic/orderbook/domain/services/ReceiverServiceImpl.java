package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.OrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiverServiceImpl implements ReceiverService {

    Logger logger = LoggerFactory.getLogger(ReceiverServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void receiveMessage(OrderRequest orderRequestMessage) {
        logger.info("m=receiveMessage RECEIVING_ORDER orderRequestMessage={}", orderRequestMessage);

        orderService.createAsync(orderRequestMessage);
    }
}
