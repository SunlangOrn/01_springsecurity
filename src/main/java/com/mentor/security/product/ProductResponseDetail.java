package com.mentor.security.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDetail (
        Long id,
        String stockKeepingUnit,
        String name,
        BigDecimal price,
        Integer stock,
        String status,
        Long categoryIds,
        String categoryName,
        LocalDateTime createdAt
){
}
