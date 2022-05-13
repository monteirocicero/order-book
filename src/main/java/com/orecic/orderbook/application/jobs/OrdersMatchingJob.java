package com.orecic.orderbook.application.jobs;

import com.orecic.orderbook.domain.services.OrderSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersMatchingJob {

    @Autowired
    private OrderSchedule orderSchedule;

    public void findMatches() {
        orderSchedule.process();
    }

}
