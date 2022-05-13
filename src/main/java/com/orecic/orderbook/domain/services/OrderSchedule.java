package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.OrderEntity;

import java.util.List;

public interface OrderSchedule {
    List<OrderEntity> process();
}
