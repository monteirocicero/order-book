package com.orecic.orderbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface OrderBookRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findOneByPriceAndOrderTypeAndQty(BigDecimal price, String ask, int qty);
}

