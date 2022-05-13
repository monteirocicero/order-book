package com.orecic.orderbook.domain.repositories;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStatusAndOrderTypeOrderByCreationDateAsc(OrderStatusEnum status, OrderTypeEnum orderType);

    List<OrderEntity> findAllByPriceAndOrderTypeAndQtyAndStatus(BigDecimal price, OrderTypeEnum orderType, int qty, OrderStatusEnum status);
}

