package com.e_commerceApi.E_CommerceAPI.controller;

import com.e_commerceApi.E_CommerceAPI.dto.AuthenticationResponse;
import com.e_commerceApi.E_CommerceAPI.dto.LoginRequest;
import com.e_commerceApi.E_CommerceAPI.dto.RegisterRequest;
import com.e_commerceApi.E_CommerceAPI.security.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

   @PostMapping("/register")
   public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
       return  new ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);
   }

   @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
       System.out.println("LOGIN METHOD REACHED");
       return new ResponseEntity<>(authService.login(loginRequest),  HttpStatus.OK);
   }

    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestParam String refreshToken) {
        return authService.refreshToken(refreshToken);
    }
}
