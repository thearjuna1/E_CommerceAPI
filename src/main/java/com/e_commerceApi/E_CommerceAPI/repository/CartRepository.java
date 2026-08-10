package com.e_commerceApi.E_CommerceAPI.repository;

import com.e_commerceApi.E_CommerceAPI.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
