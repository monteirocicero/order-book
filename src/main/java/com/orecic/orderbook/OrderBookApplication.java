package com.orecic.orderbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OrderBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderBookApplication.class, args);
	}

}
