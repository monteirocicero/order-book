package com.orecic.orderbook.application.controllers.data;

import java.math.BigDecimal;

public record OrderRequest(BigDecimal price, Long quantity, String user) {

}
