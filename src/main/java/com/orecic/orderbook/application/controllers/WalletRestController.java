package com.orecic.orderbook.application.controllers;

import com.orecic.orderbook.application.controllers.data.WalletRequest;
import com.orecic.orderbook.domain.data.WalletResponse;
import com.orecic.orderbook.domain.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("wallets")
public class WalletRestController {

    @Autowired
    private WalletService walletService;

    @PostMapping()
    public void insertMoney(@RequestBody WalletRequest walletRequest) {
        walletService.insert(walletRequest);
    }

    @GetMapping("/{user}")
    public WalletResponse showWallet(@PathVariable String user) {
        return walletService.getWallet(new WalletRequest(user));
    }
}
