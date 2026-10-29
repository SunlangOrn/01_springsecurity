package com.mentor.security.user.service;

import com.mentor.security.user.dto.AuthResponse;
import com.mentor.security.user.dto.LoginRequest;
import com.mentor.security.user.dto.RefreshTokenRequest;
import com.mentor.security.user.dto.RegisterRequest;
import com.mentor.security.user.entity.RefreshToken;
import com.mentor.security.user.entity.Role;
import com.mentor.security.user.entity.User;
import com.mentor.security.user.repository.RefreshTokenRepository;
import com.mentor.security.user.repository.RoleRepository;
import com.mentor.security.user.repository.UserRepository;
import com.mentor.security.user.security.CustomUserDetails;
import com.mentor.security.user.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${app.jwt.refresh-expireation}")
    private long refreshExpiration;

    @Override
   public void register(RegisterRequest registerRequest) {



        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UsernameNotFoundException("Email Already Exists");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new UsernameNotFoundException("Role Not Found"));

        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isVerified(true)
                .isDeleted(false)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .roles(Set.of(role))
                .build();

        userRepository.save(user);
   }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
        assert details != null;
        User user = details.getUser();

        String accessToken = jwtService.generateToken(details);
        String refreshToken = createRefreshToken(user).getToken();

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken saved = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // ពិនិត្យមើលថាតើ Refresh Token ផុតកំណត់ហើយឬនៅ
        if (saved.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(saved);
            throw new RuntimeException("Refresh token expired");
        }

        // ✅ ប្រើប្រាស់ UserDetailsService ដើម្បីទាញយក User ព្រមទាំង Roles/Authorities របស់វាឲ្យបានត្រឹមត្រូវ
        String email = saved.getUser().getEmail();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // ✅ Generate Token ថ្មីដោយផ្អែកលើ userDetails ពេញលេញ
        String newJwtToken = jwtService.generateToken(userDetails);

        return new AuthResponse(newJwtToken, saved.getToken());
    }

    private RefreshToken createRefreshToken(User user) {
        return refreshTokenRepository.save(RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshExpiration))
                .build());
    }


}
