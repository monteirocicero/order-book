package com.orecic.orderbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findOneByPriceAndOrderTypeAndQty(BigDecimal price, String ask, int qty);
    List<OrderEntity> findAllByStatusAndOrderType(String status, String orderType);
}

