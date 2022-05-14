package com.orecic.orderbook.application.controllers;

import com.orecic.orderbook.application.controllers.data.WalletRequest;
import com.orecic.orderbook.domain.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletRestController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/wallet")
    public void insertMoney(@RequestBody WalletRequest walletRequest) {
        walletService.insert(walletRequest);
    }
}
