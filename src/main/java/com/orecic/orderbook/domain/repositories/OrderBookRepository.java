package com.orecic.orderbook.domain.repositories;

import com.orecic.orderbook.domain.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStatusAndOrderTypeOrderByCreationDateAsc(String status, String orderType);

    List<OrderEntity> findAllByPriceAndOrderTypeAndQtyAndStatus(BigDecimal price, String orderType, Long qty, String status);
}

