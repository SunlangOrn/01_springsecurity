package com.mentor.security.user.service;

import com.mentor.security.user.dto.AuthResponse;
import com.mentor.security.user.dto.LoginRequest;
import com.mentor.security.user.dto.RefreshTokenRequest;
import com.mentor.security.user.dto.RegisterRequest;

public interface AuthService {

    void register (RegisterRequest registerRequest);

    AuthResponse login (LoginRequest loginRequest);

    AuthResponse refreshToken (RefreshTokenRequest refreshTokenRequest);
}
