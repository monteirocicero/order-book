package com.orecic.orderbook.domain;

import com.orecic.orderbook.domain.Order;

import java.math.BigDecimal;
import java.util.*;

public class OrderBook {
    private List<Order> asks = new ArrayList<>();
    private LinkedList<Order> bids = new LinkedList<>();

    private List<Order> executedOrders = new ArrayList<>();

    public void trade(Order order) {
        if (order.orderType().equals("ask")) {
            this.asks.add(order);
            return;
        }
        this.bids.add(order);
    }

    public List<Order> process() {
       for (Order order: this.bids) {
           Optional<Order> askMatch = matchPrice(order);
           if (askMatch.isPresent()) {
               executedOrders.add(askMatch.get());
               executedOrders.add(order);
           }
       }
       return executedOrders;
    }

    private Optional<Order> matchPrice(Order o) {
        Collections.sort(this.asks);
        return priceSearch(o.price());
    }

    private Optional<Order> priceSearch(BigDecimal price) {
        // Repeat until the pointers low and high meet each other
        int low = 0;
        int high = asks.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (asks.get(mid).price().compareTo(price) == 0)
                return Optional.of(asks.get(mid));

            if (asks.get(mid).price().compareTo(price) == 1)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return Optional.empty();
    }
}
