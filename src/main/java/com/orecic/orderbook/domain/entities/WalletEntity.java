package com.orecic.orderbook.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@Getter @Setter @NoArgsConstructor
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal balance;

    @Column(name = "user_name")
    private String user;

    @Column(name = "qty_vibranium")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long vibraniumOwned;

    public WalletEntity(BigDecimal balance, String user, Long vibraniumOwned) {
        this.balance = balance;
        this.user = user;
        this.vibraniumOwned = vibraniumOwned;
    }

}
