package com.e_commerceApi.E_CommerceAPI.repository;

import com.e_commerceApi.E_CommerceAPI.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Long>{
    Optional<RefreshToken> findByToken(String token);
}
