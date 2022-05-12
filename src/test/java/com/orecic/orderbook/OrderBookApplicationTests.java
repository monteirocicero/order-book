package com.orecic.orderbook;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;


import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class OrderBookApplicationTests {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private OrderSchedule orderBookSchedule;


    void prepareMakeASimpleTrade() {
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.00), "ask", "bob"));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.00), "bid", "alice"));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice"));
    }

    @Test
    void testMakeASimpleTrade() {
        // given
        prepareMakeASimpleTrade();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(2, executedOrders.size());

        BigDecimal aliceBalance = walletRepository.findByUser("alice").getBalance();
        BigDecimal bobBalance = walletRepository.findByUser("bob").getBalance();

        assertThat(new BigDecimal(150.0), Matchers.comparesEqualTo(aliceBalance) );
        assertThat(new BigDecimal(850.0), Matchers.comparesEqualTo(bobBalance) );
    }

    @Test
    void testDoesNotHaveTradeForAsk() {
        // given
        prepareDoesNotHaveTradeForAsk();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(0, executedOrders.size());

        BigDecimal aliceBalance = walletRepository.findByUser("alice").getBalance();
        BigDecimal bobBalance = walletRepository.findByUser("bob").getBalance();

        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(aliceBalance) );
        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(bobBalance) );

    }

    @Test
    void testDoesNotHaveTradeForBid() {
        // given
        prepareDoesNotHaveTradeBid();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(0, executedOrders.size());

        BigDecimal aliceBalance = walletRepository.findByUser("alice").getBalance();
        BigDecimal bobBalance = walletRepository.findByUser("bob").getBalance();

        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(aliceBalance) );
        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(bobBalance) );

    }

    private void prepareDoesNotHaveTradeForAsk() {
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(34.00), "ask", "bob"));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.00), "bid", "alice"));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice"));
    }

    private void prepareDoesNotHaveTradeBid() {
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.00), "ask", "bob"));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(34.00), "bid", "alice"));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice"));
    }

}
