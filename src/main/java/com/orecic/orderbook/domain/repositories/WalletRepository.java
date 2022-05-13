package com.orecic.orderbook.domain.repositories;

import com.orecic.orderbook.domain.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    WalletEntity findByUser(String user);
}

