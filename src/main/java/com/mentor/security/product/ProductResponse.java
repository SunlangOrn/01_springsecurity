package com.mentor.security.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        BigDecimal price,
        Integer stock
) {
}
