package com.orecic.orderbook.domain.entities;

import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class OrderEntity implements Comparable<OrderEntity> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int qty;
    private BigDecimal price;

    @Column(name = "order_type")
    @Enumerated(EnumType.ORDINAL)
    private OrderTypeEnum orderType;

    private String user;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatusEnum status;

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    public OrderEntity(int qty, BigDecimal price, OrderTypeEnum orderType, String user, LocalDateTime now) {
        this.qty = qty;
        this.price = price;
        this.orderType = orderType;
        this.user = user;
        this.status = OrderStatusEnum.OPEN;
        this.creationDate = now;
    }

    @Transient
    public BigDecimal getTotalOrder() {
        return BigDecimal.valueOf(getQty()).multiply(getPrice());
    }

    public OrderEntity(){}

    public void setId(Long id) {
        this.id = id;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public int getQty() {
        return qty;
    }

    public BigDecimal getPrice() {
        return price;
    }



    public String getUser() {
        return user;
    }


    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(OrderEntity o) {
        return this.price.compareTo(o.price);
    }
}
