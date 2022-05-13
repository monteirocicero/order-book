package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.enums.OrderType;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderScheduleImpl implements OrderSchedule {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private WalletService walletService;

    private List<OrderEntity> executedOrders = new ArrayList<>();

    @Override
    public List<OrderEntity> process() {

        List<OrderEntity> orders = orderBookRepository.findAllByStatusAndOrderTypeOrderByCreationDateAsc(OrderStatusEnum.OPEN.name(), OrderType.BID.name());
        for (OrderEntity bidOrder: orders) {

            List<OrderEntity> matchesAskOrder = orderBookRepository.findAllByPriceAndOrderTypeAndQtyAndStatus(bidOrder.getPrice(), OrderType.ASK.name(), bidOrder.getQty(), OrderStatusEnum.OPEN.name());

            if (matchesAskOrder.isEmpty()) {
                continue;
            }

            OrderEntity matchAskOrder = matchesAskOrder.get(0);

            // TODO move this calcule to another class
            BigDecimal debitBalance = BigDecimal.valueOf(bidOrder.getQty()).multiply(bidOrder.getPrice());
            BigDecimal creditBalance = BigDecimal.valueOf(matchAskOrder.getQty()).multiply(matchAskOrder.getPrice());

            // TODO move this for a single method in another class
            walletService.update(bidOrder.getUser(), debitBalance, bidOrder.getOrderType());
            walletService.update(matchAskOrder.getUser(), creditBalance, matchAskOrder.getOrderType());

            // TODO move this to another class
            bidOrder.setStatus(OrderStatusEnum.EXECUTED.name());
            matchAskOrder.setStatus(OrderStatusEnum.EXECUTED.name());

            orderBookRepository.save(bidOrder);
            orderBookRepository.save(matchAskOrder);

            executedOrders.add(bidOrder);
            executedOrders.add(matchAskOrder);
        }

        return executedOrders;
    }
}
