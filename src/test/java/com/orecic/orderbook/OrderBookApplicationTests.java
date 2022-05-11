package com.orecic.orderbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.processing.AbstractProcessor;
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


	@BeforeEach
	public void init() {
		orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.00), "ask", "bob"));
		orderBookRepository.save(new OrderEntity(10, new BigDecimal(35.00), "bid", "alice"));

		walletRepository.save(new WalletEntity(new BigDecimal(500.0), "bob"));
		walletRepository.save(new WalletEntity(new BigDecimal(500.0), "alice"));
	}

	@Test
	void contextLoads() {


			// given


			// when
			List<OrderEntity> executedOrders = orderBookSchedule.process();



			// then
			Assertions.assertEquals(2, executedOrders.size());
			Assertions.assertEquals(new BigDecimal(250.0), walletRepository.findByUser("bob"));
			Assertions.assertEquals(new BigDecimal(850.0), walletRepository.findByUser("alice"));

	}

}
