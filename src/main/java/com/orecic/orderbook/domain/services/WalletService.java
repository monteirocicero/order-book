package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.WalletRequest;

public interface WalletService {
    void update(WalletUpdate walletUpdate);

    void insert(WalletRequest walletRequest);
}
