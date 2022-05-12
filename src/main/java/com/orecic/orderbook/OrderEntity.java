package com.orecic.orderbook;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderEntity implements Comparable<OrderEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int qty;
    private BigDecimal price;

    @Column(name = "order_type")
    private String orderType;

    private String user;

    private String status;

    public OrderEntity(int qty, BigDecimal price, String orderType, String user) {
        this.qty = qty;
        this.price = price;
        this.orderType = orderType;
        this.user = user;
        this.status = "PENDING";
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

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getOrderType() {
        return orderType;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int compareTo(OrderEntity o) {
        return this.price.compareTo(o.price);
    }
}
