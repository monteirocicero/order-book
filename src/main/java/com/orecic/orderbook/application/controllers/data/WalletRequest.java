package com.orecic.orderbook.application.controllers.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class WalletRequest {
    private String user;
    private BigDecimal balance;

    public WalletRequest(String user) {
        this.user = user;
    }

}
