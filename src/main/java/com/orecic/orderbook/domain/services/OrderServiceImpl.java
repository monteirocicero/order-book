package com.orecic.orderbook.domain.services;

import com.orecic.orderbook.application.controllers.data.OrderRequest;
import com.orecic.orderbook.domain.data.WalletUpdate;
import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.enums.OrderStatusEnum;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import com.orecic.orderbook.infraestructure.config.rabbitmq.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void update(OrderEntity bidOrder, OrderEntity askOrder) {
        logger.info("m=update UPDATE_ORDER bidOrder={} askOrder={}", bidOrder, askOrder);
        walletService.update(new WalletUpdate(bidOrder.getUser(), bidOrder.getTotalOrder(), bidOrder.getOrderType(), bidOrder.getQty()));
        walletService.update(new WalletUpdate(askOrder.getUser(), askOrder.getTotalOrder(), askOrder.getOrderType(), askOrder.getQty()));

        bidOrder.setStatus(OrderStatusEnum.EXECUTED.name());
        askOrder.setStatus(OrderStatusEnum.EXECUTED.name());

        orderBookRepository.save(bidOrder);
        orderBookRepository.save(askOrder);
    }

    @Async
    @Override
    public void createAsync(OrderRequest orderRequest) {
        logger.info("m=createAsync CREATE_ORDER_DOMAIN orderRequest={}", orderRequest);
        orderBookRepository.save(new OrderEntity(orderRequest.quantity(), orderRequest.price(), orderRequest.orderType(), orderRequest.user(), LocalDateTime.now()));
    }

    @Override
    public void enqueue(OrderRequest orderRequest) {
        logger.info("m=enqueue ENQUEUE_ORDER orderRequest={}", orderRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, "order.book.baz", orderRequest);
    }
}
