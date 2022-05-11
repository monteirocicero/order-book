package com.orecic.orderbook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    WalletEntity findByUser(String user);
}

