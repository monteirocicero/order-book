package com.orecic.orderbook.domain.services;

import java.math.BigDecimal;

public interface WalletService {
    void update(String user, BigDecimal qty, String price);
}
