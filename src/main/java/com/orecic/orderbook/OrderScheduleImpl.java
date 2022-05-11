package com.orecic.orderbook;

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

    private List<OrderEntity> ordersExecuteds = new ArrayList<>();

    @Override
    public List<OrderEntity> process() {

        List<OrderEntity> orders = orderBookRepository.findAllByOrderType;

        for (OrderEntity order: orders) {
            OrderEntity matchOrders = orderBookRepository.findOneByPriceAndOrderTypeAndQty(order.getPrice(), "ask", order.getQty());

            BigDecimal newBidBalance = BigDecimal.valueOf(order.getQty()).multiply(order.getPrice());
            BigDecimal newAskBalance = BigDecimal.valueOf(matchOrders.getQty()).multiply(matchOrders.getPrice());

            walletService.update(order.getUser(), newBidBalance, order.getOrderType());
            walletService.update(order.getUser(), newAskBalance, order.getOrderType());

            ordersExecuteds.add(order);
            ordersExecuteds.add(matchOrders);

        }

        return ordersExecuteds;
    }
}
