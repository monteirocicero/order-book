package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.OrderRequest;

public interface ReceiverService {
    void receiveMessage(OrderRequest orderRequestMessage);
}
