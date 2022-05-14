package com.orecic.orderbook.domain.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
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

    public WalletEntity() {}

    public WalletEntity(String user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
        this.vibraniumOwned = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getVibraniumOwned() {
        return vibraniumOwned;
    }

    public void setVibraniumOwned(Long vibraniumOwned) {
        this.vibraniumOwned = vibraniumOwned;
    }
}
