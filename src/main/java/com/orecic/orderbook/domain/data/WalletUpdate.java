package com.orecic.orderbook.domain.data;

import java.math.BigDecimal;

public class WalletUpdate {
    private Long qtyVibranium;
    private String user;
    private BigDecimal amount;
    private String balanceType;

    public WalletUpdate(String user, BigDecimal amount, String orderType, Long qtyVibranium) {
        this.user = user;
        this.amount = amount;
        this.balanceType = orderType;
        this.qtyVibranium = qtyVibranium;
    }

    public String getUser() {
        return this.user;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getBalanceType() {
        return this.balanceType;
    }

    public Long getQtyVibranium() {
        return qtyVibranium;
    }
}
