package com.orecic.orderbook.application.controllers.data;

import java.io.Serializable;
import java.math.BigDecimal;

public record OrderRequest(BigDecimal price, Long quantity, String user, String orderType) implements Serializable {
    private static final long serialVersionUID = 1L;
}
