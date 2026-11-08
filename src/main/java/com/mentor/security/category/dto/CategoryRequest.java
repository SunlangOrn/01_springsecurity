package com.mentor.security.category.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CategoryRequest(
         @NotBlank String name,

         @NotBlank
         @Pattern(regexp = "^(ACTIVE|INACTIVE)$")
         String status
) {
}
