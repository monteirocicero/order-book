package com.orecic.orderbook;

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

        if (orderType.equals("ask")) {
            wallet.setBalance(wallet.getBalance().add(newBalance));
            walletRepository.save(wallet);
        } else {
            wallet.setBalance(wallet.getBalance().subtract(newBalance));
            walletRepository.save(wallet);
        }
    }
}
