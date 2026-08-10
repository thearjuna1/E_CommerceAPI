package com.e_commerceApi.E_CommerceAPI.repository;

import com.e_commerceApi.E_CommerceAPI.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
