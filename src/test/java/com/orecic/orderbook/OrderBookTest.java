package com.orecic.orderbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderBookTest {

    @Test
    public void testTradeAnOrder() {
        // given
        Order ask = new Order(10, new BigDecimal(35.00), "ask", "bob");
        Order bid = new Order(10, new BigDecimal(35.00), "bid", "alice");

        Wallet bob = new Wallet(new BigDecimal(500.0));
        Wallet alice = new Wallet(new BigDecimal(600.0));

        OrderBook orderBook = new OrderBook();
        orderBook.trade(ask);
        orderBook.trade(bid);

        // when
        List<Order> executedOrders = orderBook.process();

        for (Order order: executedOrders) {
            if (order.user().equals("bob")) {
                bob.update(BigDecimal.valueOf(order.qty()).multiply(order.price()), order.orderType());
            } else {
                alice.update(BigDecimal.valueOf(order.qty()).multiply(order.price()), order.orderType());
            }

        }

        // then
        Assertions.assertEquals(2, executedOrders.size());
        Assertions.assertEquals(new BigDecimal(250.0), alice.getBalance());
        Assertions.assertEquals(new BigDecimal(850.0), bob.getBalance());

    }


}
