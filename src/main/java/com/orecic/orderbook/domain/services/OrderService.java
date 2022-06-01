package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.OrderRequest;
import com.orecic.orderbook.domain.entities.OrderEntity;

public interface OrderService {

    void update(OrderEntity bidOrder, OrderEntity askOrder);

    void createAsync(OrderRequest orderRequest);

    void enqueue(OrderRequest orderRequest);
}
