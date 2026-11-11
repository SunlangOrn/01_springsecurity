package com.mentor.security.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 50) String sku,
        @NotBlank @Size(max = 50) String name,
        @NotNull @DecimalMin("0.00") @Digits(integer = 10, fraction = 2) BigDecimal price,
        @NotNull @PositiveOrZero Integer stock,
        @Pattern(regexp = "^(ACTIVE|INACTIVE)$")
        String status,           // optional, defaults to INACTIVE on create
        @NotNull Long categoryId
) {
}
