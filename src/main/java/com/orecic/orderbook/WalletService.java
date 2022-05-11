package com.orecic.orderbook;

import java.math.BigDecimal;

public interface WalletService {
    void update(String user, BigDecimal qty, String price);
}
