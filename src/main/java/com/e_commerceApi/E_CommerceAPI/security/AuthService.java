package com.e_commerceApi.E_CommerceAPI.security;

import com.e_commerceApi.E_CommerceAPI.dto.AuthenticationResponse;
import com.e_commerceApi.E_CommerceAPI.dto.LoginRequest;
import com.e_commerceApi.E_CommerceAPI.dto.RegisterRequest;
import com.e_commerceApi.E_CommerceAPI.entity.Cart;
import com.e_commerceApi.E_CommerceAPI.entity.RefreshToken;
import com.e_commerceApi.E_CommerceAPI.entity.User;
import com.e_commerceApi.E_CommerceAPI.enums.Role;
import com.e_commerceApi.E_CommerceAPI.repository.RefreshTokenRepository;
import com.e_commerceApi.E_CommerceAPI.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    public AuthService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, AuthenticationManager authenticationManager, RefreshTokenRepository refreshTokenRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.authenticationManager = authenticationManager;
        this.refreshTokenRepository = refreshTokenRepository;
    }



    public String register(RegisterRequest register)  {
        if(userRepository.findByEmail(register.getEmail()).isPresent()){
            return "Email already exists";
        }
        User user = new  User();
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        Cart cart = new Cart();
        user.setCart(cart);
        userRepository.save(user);
        return "User registered successfully. Please login!";
    }


    public JwtPrincipal getCurrentPrincipal(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (JwtPrincipal) authentication.getPrincipal();
    }


    public AuthenticationResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user =  (User)authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken refreshTokenEntity = new RefreshToken();

        refreshTokenEntity.setToken(refreshToken);

        refreshTokenEntity.setCreatedAt(LocalDateTime.now());

        refreshTokenEntity.setUser(user);

        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshTokenEntity);

        return new AuthenticationResponse(token, refreshToken);



    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        RefreshToken savedToken = refreshTokenRepository.findByToken(refreshToken)
                        .orElseThrow(() ->
                                new RuntimeException("Refresh token not found")
                        ); // find refresh token

        if (savedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        } // check validity

        User user = savedToken.getUser();

        if (!jwtService.validateToken(refreshToken, user)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateToken(user);

        return new AuthenticationResponse(newAccessToken, refreshToken);
    }
    }
