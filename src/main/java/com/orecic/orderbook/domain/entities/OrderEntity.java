package com.orecic.orderbook.domain.entities;

import com.orecic.orderbook.domain.enums.OrderStatusEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
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

    public OrderEntity(){}

    public void setId(Long id) {
        this.id = id;
    }

    public void setQty(Long qty) {
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

    public Long getQty() {
        return qty;
    }

    public BigDecimal getPrice() {
        return price;
    }



    public String getUser() {
        return user;
    }


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
