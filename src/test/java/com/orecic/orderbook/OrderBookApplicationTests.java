package com.orecic.orderbook;

import com.orecic.orderbook.domain.entities.OrderEntity;
import com.orecic.orderbook.domain.entities.WalletEntity;
import com.orecic.orderbook.domain.enums.OrderTypeEnum;
import com.orecic.orderbook.domain.repositories.OrderBookRepository;
import com.orecic.orderbook.domain.repositories.WalletRepository;
import com.orecic.orderbook.domain.services.OrderSchedule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;



@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderBookApplicationTests {

    @Autowired
    private OrderBookRepository orderBookRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private OrderSchedule orderBookSchedule;

    @BeforeEach
    void resetDatabase() {
        orderBookRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    void testMakeASimpleTrade() {
        // given
        prepareTrade(new BigDecimal(35.00), new BigDecimal(35.00));

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(2, executedOrders.size());

        WalletEntity alice = walletRepository.findByUser("alice");
        WalletEntity bob = walletRepository.findByUser("bob");

        assertThat(new BigDecimal(150.0), Matchers.comparesEqualTo(alice.getBalance()));
        assertThat(new BigDecimal(850.0), Matchers.comparesEqualTo(bob.getBalance()));

        Assertions.assertEquals(10L, alice.getVibraniumOwned());
        Assertions.assertEquals(10L, bob.getVibraniumOwned());
    }

    @Test
    void testDoesNotHaveTradeForAsk() {
        // given
        prepareTrade(new BigDecimal(34.00), new BigDecimal(35.00));

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(0, executedOrders.size());

        WalletEntity alice = walletRepository.findByUser("alice");
        WalletEntity bob = walletRepository.findByUser("bob");

        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(alice.getBalance()));
        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(bob.getBalance()));

        Assertions.assertEquals(0L, alice.getVibraniumOwned());
        Assertions.assertEquals(20L, bob.getVibraniumOwned());

    }

    @Test
    void testDoesNotHaveTradeForBid() {
        // given
        prepareTrade(new BigDecimal(34.00), new BigDecimal(35.00));

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(0, executedOrders.size());

        WalletEntity alice = walletRepository.findByUser("alice");
        WalletEntity bob = walletRepository.findByUser("bob");

        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(alice.getBalance()));
        assertThat(new BigDecimal(500.0), Matchers.comparesEqualTo(bob.getBalance()));

        Assertions.assertEquals(0L, alice.getVibraniumOwned());
        Assertions.assertEquals(20L, bob.getVibraniumOwned());

    }

    @Test
    void testMakeATradeByRespectingFIFO() {
        // given
        prepareTradeWithQueue();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(2, executedOrders.size());

        WalletEntity maria = walletRepository.findByUser("maria");
        WalletEntity bob = walletRepository.findByUser("bob");

        assertThat(new BigDecimal(150.0), Matchers.comparesEqualTo(maria.getBalance()));
        assertThat(new BigDecimal(850.0), Matchers.comparesEqualTo(bob.getBalance()));

        Assertions.assertEquals(10L, maria.getVibraniumOwned());
        Assertions.assertEquals(10L, bob.getVibraniumOwned());
    }

    @Test
    void testMakeATradeWithMoreThanOneAsksAndBids() {
        // given
        prepareTradeWithMoreThanOneAsksAndBids();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(4, executedOrders.size());

        WalletEntity maria = walletRepository.findByUser("maria");
        WalletEntity bob = walletRepository.findByUser("bob");
        WalletEntity john = walletRepository.findByUser("john");
        WalletEntity alice = walletRepository.findByUser("alice");


        assertThat(new BigDecimal(250.00).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(maria.getBalance()));
        assertThat(new BigDecimal(750.0).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(bob.getBalance()));
        assertThat(new BigDecimal(850.2).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(john.getBalance()));
        assertThat(new BigDecimal(250.0).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(alice.getBalance()));


        Assertions.assertEquals(17L, alice.getVibraniumOwned());
        Assertions.assertEquals(11L, maria.getVibraniumOwned());
        Assertions.assertEquals(22L, john.getVibraniumOwned());
        Assertions.assertEquals(10L, bob.getVibraniumOwned());
    }

    @Test
    void testMakeATradeWithMoreThanOneAsksAndSingleBid() {
        // given
        prepareTradeWithMoreThanOneAsksAndSingleBid();

        // when
        List<OrderEntity> executedOrders = orderBookSchedule.process();

        // then
        Assertions.assertEquals(2, executedOrders.size());
        WalletEntity maria = walletRepository.findByUser("maria");

        assertThat(new BigDecimal(250).setScale(4, RoundingMode.HALF_DOWN), Matchers.comparesEqualTo(maria.getBalance()));

        Assertions.assertEquals(20L, maria.getVibraniumOwned());
    }

    private void prepareTrade(BigDecimal askPrice, BigDecimal bidPrice) {
        orderBookRepository.save(new OrderEntity(10L, askPrice, OrderTypeEnum.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, bidPrice, OrderTypeEnum.BID.name(), "alice", LocalDateTime.now()));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob", 20L));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice",0L));
    }

    private void prepareTradeWithQueue() {
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.BID.name(), "alice", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.BID.name(), "maria", LocalDateTime.now().minusDays(1)));

        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob", 20L));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice", 0L));
        walletRepository.save(new WalletEntity(new BigDecimal(500.0), "maria", 0L));
    }

    private void prepareTradeWithMoreThanOneAsksAndSingleBid() {
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.ASK.name(), "john", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.BID.name(), "maria", LocalDateTime.now().minusDays(1)));

        walletRepository.save(new WalletEntity(new BigDecimal(400.0), "bob", 20L));
        walletRepository.save(new WalletEntity(new BigDecimal(500.2), "john", 22L));
        walletRepository.save(new WalletEntity(new BigDecimal(600.0), "maria", 10L));
    }

    private void prepareTradeWithMoreThanOneAsksAndBids() {
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.ASK.name(), "bob", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.ASK.name(), "john", LocalDateTime.now()));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.BID.name(), "maria", LocalDateTime.now().minusDays(1)));
        orderBookRepository.save(new OrderEntity(10L, new BigDecimal(35.0), OrderTypeEnum.BID.name(), "alice", LocalDateTime.now()));

        walletRepository.save(new WalletEntity(new BigDecimal(400.0), "bob", 20L));
        walletRepository.save(new WalletEntity(new BigDecimal(500.2), "john", 32L));
        walletRepository.save(new WalletEntity(new BigDecimal(600.0), "maria", 1L));
        walletRepository.save(new WalletEntity(new BigDecimal(600.0), "alice", 7L));
    }
}
