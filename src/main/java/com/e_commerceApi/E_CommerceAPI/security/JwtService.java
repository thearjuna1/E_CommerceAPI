package com.e_commerceApi.E_CommerceAPI.security;

import com.e_commerceApi.E_CommerceAPI.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt_secret}")
    private String SECRET_KEY;

    private SecretKey getSecretKey() {
        byte [] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(User userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .claim("role", userDetails.getAuthorities())
                .claim("userId", userDetails.getId())
                .expiration(new Date(System.currentTimeMillis() + 60 *1000 * 60))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaims(String token , Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(getSecretKey())
                .compact();
    }
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean validateToken(String token , UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername())
                && extractExpiration(token).after(new Date());
    }
}
