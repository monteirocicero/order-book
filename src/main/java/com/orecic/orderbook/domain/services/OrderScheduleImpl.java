package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderScheduleImpl implements OrderSchedule {

    Logger logger = LoggerFactory.getLogger(OrderScheduleImpl.class);


    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private OrderService orderService;

    private List<OrderEntity> executedOrders = new ArrayList<>();

    @Override
    public List<OrderEntity> process() {
        logger.info("m=process INITIALIZING");

        List<OrderEntity> orders = orderBookRepository.findAllByStatusAndOrderTypeOrderByCreationDateAsc(OrderStatusEnum.OPEN.name(), OrderTypeEnum.BID.name());
        for (OrderEntity bidOrder: orders) {

            List<OrderEntity> matchesAskOrder = orderBookRepository.findAllByPriceAndOrderTypeAndQtyAndStatus(bidOrder.getPrice(), OrderTypeEnum.ASK.name(), bidOrder.getQty(),
                    OrderStatusEnum.OPEN.name());

            if (matchesAskOrder.isEmpty()) {
                continue;
            }

            Optional<OrderEntity> matchAskOrder = matchesAskOrder.stream().findFirst();

            logger.info("m=process MATCH bidOrder={}, askOrder={}", bidOrder, matchAskOrder.get());
            orderService.update(bidOrder, matchAskOrder.get());

            collectResult(bidOrder, matchAskOrder);
        }

        logger.info("m=process FINISHED");


        return executedOrders;
    }

    private void collectResult(OrderEntity bidOrder, Optional<OrderEntity> matchAskOrder) {
        executedOrders.add(bidOrder);
        executedOrders.add(matchAskOrder.get());
    }
}
