package com.orecic.orderbook;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.entities.WalletEntity;
import com.orecic.orderbook.domain.enums.OrderType;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import com.orecic.orderbook.domain.repositories.WalletRepository;
import com.orecic.orderbook.domain.services.OrderSchedule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.assertThat;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderBookApplicationTests {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private OrderSchedule orderBookSchedule;

    @Test
    void testMakeASimpleTrade() {
        // given
        prepareTrade(new BigDecimal(35.00), new BigDecimal(35.00));

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
        prepareTrade(new BigDecimal(34.00), new BigDecimal(35.00));

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
        prepareTrade(new BigDecimal(34.00), new BigDecimal(35.00));

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
    void testMakeATradeByRespectingFIFO() {
        // given
        prepareTradeWithQueue();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(2, executedOrders.size());

        BigDecimal aliceBalance = walletRepository.findByUser("maria").getBalance();
        BigDecimal bobBalance = walletRepository.findByUser("bob").getBalance();

        assertThat(new BigDecimal(150.0), Matchers.comparesEqualTo(aliceBalance));
        assertThat(new BigDecimal(850.0), Matchers.comparesEqualTo(bobBalance));
    }

    @Test
    void testMakeATradeWithMoreThanOneAsksAndBids() {
        // given
        prepareTradeWithMoreThanOneAsksAndBids();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(4, executedOrders.size());

        BigDecimal mariaBalance = walletRepository.findByUser("maria").getBalance();
        BigDecimal bobBalance = walletRepository.findByUser("bob").getBalance();
        BigDecimal johnBalance = walletRepository.findByUser("john").getBalance();
        BigDecimal aliceBalance = walletRepository.findByUser("alice").getBalance();


        assertThat(new BigDecimal(250.00).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(mariaBalance));
        assertThat(new BigDecimal(750.0).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(bobBalance));
        assertThat(new BigDecimal(850.2).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(johnBalance));
        assertThat(new BigDecimal(250.0).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(aliceBalance));
    }

    @Test
    void testMakeATradeWithMoreThanOneAsksAndSingleBid() {
        // given
        prepareTradeWithMoreThanOneAsksAndSingleBid();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(2, executedOrders.size());

        BigDecimal mariaBalance = walletRepository.findByUser("maria").getBalance();


        assertThat(new BigDecimal(250).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(mariaBalance));

    }

    private void prepareTrade(BigDecimal askPrice, BigDecimal bidPrice) {
        orderBookRepository.save(new OrderEntity(10, askPrice, OrderType.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, bidPrice, OrderType.BID.name(), "alice", LocalDateTime.now()));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice"));
    }

    private void prepareTradeWithQueue() {
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.BID.name(), "alice", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.BID.name(), "maria", LocalDateTime.now().minusDays(1)));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "maria"));
    }

    private void prepareTradeWithMoreThanOneAsksAndSingleBid() {
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.ASK.name(), "john", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.BID.name(), "maria", LocalDateTime.now().minusDays(1)));

        walletRepository.save(new WalletEntity(new BigDecimal(400.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.2), "john"));
        walletRepository.save(new WalletEntity(new BigDecimal(600.0), "maria"));
    }

    private void prepareTradeWithMoreThanOneAsksAndBids() {
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.ASK.name(), "john", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.BID.name(), "maria", LocalDateTime.now().minusDays(1)));
        orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.0), OrderType.BID.name(), "alice", LocalDateTime.now()));

        walletRepository.save(new WalletEntity(new BigDecimal(400.0), "bob"));
        walletRepository.save(new WalletEntity(new BigDecimal(500.2), "john"));
        walletRepository.save(new WalletEntity(new BigDecimal(600.0), "maria"));
        walletRepository.save(new WalletEntity(new BigDecimal(600.0), "alice"));
    }


}
