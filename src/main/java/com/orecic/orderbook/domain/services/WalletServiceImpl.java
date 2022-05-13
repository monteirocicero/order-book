package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.WalletEntity;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;
import com.orecic.orderbook.domain.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void update(WalletUpdate walletUpdate) {
        WalletEntity wallet = walletRepository.findByUser(walletUpdate.getUser());

        if (OrderTypeEnum.ASK.name().equals(walletUpdate.getBalanceType())) {
            wallet.setBalance(wallet.getBalance().add(walletUpdate.getAmount()));
            wallet.setVibraniumOwned(wallet.getVibraniumOwned() - walletUpdate.getQtyVibranium());
        } else {
            wallet.setBalance(wallet.getBalance().subtract(walletUpdate.getAmount()));
            wallet.setVibraniumOwned(wallet.getVibraniumOwned() + walletUpdate.getQtyVibranium());
        }
        walletRepository.save(wallet);
    }
}
