package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.WalletRequest;
import com.orecic.orderbook.domain.data.WalletResponse;
import com.orecic.orderbook.domain.data.WalletUpdate;
import com.orecic.orderbook.domain.entities.WalletEntity;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;
import com.orecic.orderbook.domain.repositories.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public void update(WalletUpdate walletUpdate) {
        logger.info("m=update UPDATE wallet={}", walletUpdate.getUser());
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

    @Override
    public void insert(WalletRequest walletRequest) {
        logger.info("m=insert CREATE user={}", walletRequest.getUser());

        walletRepository.save(new WalletEntity(walletRequest.getBalance(), walletRequest.getUser(), walletRequest.getVibranium()));
    }

    @Override
    public WalletResponse getWallet(WalletRequest walletRequest) {
        logger.info("m=getWallet SEARCH user={}", walletRequest.getUser());

        WalletEntity foundWallet = walletRepository.findByUser(walletRequest.getUser());
        return new WalletResponse(foundWallet.getUser(), foundWallet.getBalance(), foundWallet.getVibraniumOwned());
    }
}
