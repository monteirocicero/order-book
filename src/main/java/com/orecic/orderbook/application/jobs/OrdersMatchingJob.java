package com.orecic.orderbook.application.jobs;

import com.orecic.orderbook.domain.services.OrderSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class OrdersMatchingJob {

    Logger logger = LoggerFactory.getLogger(OrdersMatchingJob.class);


    @Autowired
    private OrderSchedule orderSchedule;

    //@Scheduled(fixedRate = 60_000, initialDelay = 60_000)
    @Transactional
    public void findMatches() {
        logger.info("m=findMatches SEEKING_MATCHES");
        orderSchedule.process();
    }

}
