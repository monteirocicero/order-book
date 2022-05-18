package com.orecic.orderbook.domain.repositories;

import com.orecic.orderbook.domain.entities.OrderEntity;
import org.hibernate.LockOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.math.BigDecimal;
import java.util.List;


public interface OrderBookRepository extends JpaRepository<OrderEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = (LockOptions.SKIP_LOCKED + ""))
    })
    List<OrderEntity> findAllByStatusAndOrderTypeOrderByCreationDateAsc(String status, String orderType);

    List<OrderEntity> findAllByPriceAndOrderTypeAndQtyAndStatus(BigDecimal price, String orderType, Long qty, String status);
}

