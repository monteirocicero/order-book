package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderScheduleImpl implements OrderSchedule {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private OrderService orderService;

    private List<OrderEntity> executedOrders = new ArrayList<>();

    @Override
    public List<OrderEntity> process() {

        List<OrderEntity> orders = orderBookRepository.findAllByStatusAndOrderTypeOrderByCreationDateAsc(OrderStatusEnum.OPEN, OrderTypeEnum.BID);
        for (OrderEntity bidOrder: orders) {

            List<OrderEntity> matchesAskOrder = orderBookRepository.findAllByPriceAndOrderTypeAndQtyAndStatus(bidOrder.getPrice(), OrderTypeEnum.ASK, bidOrder.getQty(),
                    OrderStatusEnum.OPEN);

            if (matchesAskOrder.isEmpty()) {
                continue;
            }

            Optional<OrderEntity> matchAskOrder = matchesAskOrder.stream().findFirst();
            orderService.update(bidOrder, matchAskOrder.get());

            collectResult(bidOrder, matchAskOrder);
        }

        return executedOrders;
    }

    private void collectResult(OrderEntity bidOrder, Optional<OrderEntity> matchAskOrder) {
        executedOrders.add(bidOrder);
        executedOrders.add(matchAskOrder.get());
    }
}
