package com.orecic.orderbook.domain.entities;

import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor
public class OrderEntity implements Comparable<OrderEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long qty;
    private BigDecimal price;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "user_name")
    private String user;

    private String status;

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    public OrderEntity(Long qty, BigDecimal price, String orderType, String user, LocalDateTime now) {
        this.qty = qty;
        this.price = price;
        this.orderType = orderType;
        this.user = user;
        this.status = OrderStatusEnum.OPEN.name();
        this.creationDate = now;
    }

    @Transient
    public BigDecimal getTotalOrder() {
        return BigDecimal.valueOf(getQty()).multiply(getPrice());
    }

    @Override
    public int compareTo(OrderEntity o) {
        return this.price.compareTo(o.price);
    }
}
