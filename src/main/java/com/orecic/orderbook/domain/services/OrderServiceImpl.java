package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Override
    public void update(OrderEntity bidOrder, OrderEntity askOrder) {
        walletService.update(new WalletUpdate(bidOrder.getUser(), bidOrder.getTotalOrder(), bidOrder.getOrderType(), bidOrder.getQty()));
        walletService.update(new WalletUpdate(askOrder.getUser(), askOrder.getTotalOrder(), askOrder.getOrderType(), askOrder.getQty()));

        bidOrder.setStatus(OrderStatusEnum.EXECUTED.name());
        askOrder.setStatus(OrderStatusEnum.EXECUTED.name());

        orderBookRepository.save(bidOrder);
        orderBookRepository.save(askOrder);
    }
}
