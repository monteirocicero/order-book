package com.orecic.orderbook.domain;

import java.math.BigDecimal;

public record Order(int qty, BigDecimal price, String orderType, String user) implements Comparable<Order> {

    @Override
    public int compareTo(Order o) {
        return this.price.compareTo(o.price);
    }
}
