package com.orecic.orderbook.domain.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
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

}
