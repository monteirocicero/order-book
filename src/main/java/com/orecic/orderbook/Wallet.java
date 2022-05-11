package com.orecic.orderbook;

import java.math.BigDecimal;

public class Wallet {

    private BigDecimal balance;

    public Wallet(BigDecimal balance) {
        this.balance = balance;
    }

    public void update(BigDecimal updateBalance, String orderType) {
        if (orderType.equals("bid")) {
            this.balance = balance.subtract(updateBalance);
        } else {
            this.balance = balance.add(updateBalance);
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
