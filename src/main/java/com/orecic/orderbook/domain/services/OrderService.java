package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.OrderEntity;

public interface OrderService {

    void update(OrderEntity bidOrder, OrderEntity askOrder);
}
