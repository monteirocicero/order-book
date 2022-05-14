package com.orecic.orderbook.application.controllers.data;

import java.math.BigDecimal;

public class WalletRequest {
    private String user;
    private BigDecimal balance;

    public WalletRequest(String user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }

    public WalletRequest(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
