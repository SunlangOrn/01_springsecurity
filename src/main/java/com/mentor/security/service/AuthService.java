package com.mentor.security.service;

import com.mentor.security.dto.LoginRequest;
import com.mentor.security.dto.RegisterRequest;
import jakarta.servlet.Registration;

public interface AuthService {

    void register (RegisterRequest registerRequest);

    void login (LoginRequest loginRequest);
}
