package com.mentor.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Email // name@gmail.com name@email.com //name@local.com
    private String email;

    @NotBlank @Size(min = 6, max = 100)
    private String password;
}
