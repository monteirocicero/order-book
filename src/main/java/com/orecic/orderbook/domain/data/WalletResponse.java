package com.orecic.orderbook.domain.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WalletResponse {
    private String user;
    private BigDecimal balance;
    @JsonProperty("vibranium")
    private Long vibraniumOwned;

    public WalletResponse(String user, BigDecimal balance, Long vibraniumOwned) {
        this.user = user;
        this.balance = balance;
        this.vibraniumOwned = vibraniumOwned;
    }

}
