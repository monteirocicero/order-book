package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.enums.OrderType;
import com.orecic.orderbook.domain.repositories.WalletRepository;
import com.orecic.orderbook.domain.entities.WalletEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void update(String user, BigDecimal newBalance, String orderType) {
        WalletEntity wallet = walletRepository.findByUser(user);

        if (OrderType.ASK.name().equals(orderType)) {
            wallet.setBalance(wallet.getBalance().add(newBalance));
            walletRepository.save(wallet);
        } else {
            wallet.setBalance(wallet.getBalance().subtract(newBalance));
            walletRepository.save(wallet);
        }
    }
}
