package com.orecic.orderbook.domain.services;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class WalletResponse {
    private final String user;
    private final BigDecimal balance;
    @JsonProperty("vibranium")
    private final Long vibraniumOwned;

    public WalletResponse(String user, BigDecimal balance, Long vibraniumOwned) {
        this.user = user;
        this.balance = balance;
        this.vibraniumOwned = vibraniumOwned;
    }

    public String getUser() {
        return user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getVibraniumOwned() {
        return vibraniumOwned;
    }
}
