package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.WalletRequest;
import com.orecic.orderbook.domain.data.WalletResponse;
import com.orecic.orderbook.domain.data.WalletUpdate;

public interface WalletService {
    void update(WalletUpdate walletUpdate);

    void insert(WalletRequest walletRequest);

    WalletResponse getWallet(WalletRequest walletRequest);
}
