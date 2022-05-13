package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.enums.OrderTypeEnum;

import java.math.BigDecimal;

public class WalletUpdate {
    private String user;
    private BigDecimal amount;
    private OrderTypeEnum balanceType;

    public WalletUpdate(String user, BigDecimal amount, OrderTypeEnum orderType) {
        this.user = user;
        this.amount = amount;
        this.balanceType = orderType;
    }

    public String getUser() {
        return this.user;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public OrderTypeEnum getBalanceType() {
        return this.balanceType;
    }
}
