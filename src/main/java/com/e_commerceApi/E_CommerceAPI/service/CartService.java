package com.e_commerceApi.E_CommerceAPI.service;

import com.e_commerceApi.E_CommerceAPI.dto.CartItemRequest;
import com.e_commerceApi.E_CommerceAPI.dto.CartRequest;
import com.e_commerceApi.E_CommerceAPI.entity.Cart;
import com.e_commerceApi.E_CommerceAPI.entity.CartItem;
import com.e_commerceApi.E_CommerceAPI.entity.Product;
import com.e_commerceApi.E_CommerceAPI.entity.User;
import com.e_commerceApi.E_CommerceAPI.repository.CartItemRepository;
import com.e_commerceApi.E_CommerceAPI.repository.CartRepository;
import com.e_commerceApi.E_CommerceAPI.repository.ProductRepository;
import com.e_commerceApi.E_CommerceAPI.repository.UserRepository;
import com.e_commerceApi.E_CommerceAPI.security.AuthService;
import com.e_commerceApi.E_CommerceAPI.security.JwtPrincipal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, AuthService authService, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.authService = authService;
        this.userRepository = userRepository;
    }


//    public Cart createCart(CartRequest cartRequest) {
//
//        JwtPrincipal principal = authService.getCurrentPrincipal();
//
//        Long userId = principal.userId();
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Cart cart = new Cart();
//
//        cart.setUser(user);
//
//        for (CartItemRequest itemRequest : cartRequest.getItems()) {
//            Product product = productRepository.findById(itemRequest.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found"));
//            CartItem cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(itemRequest.getQuantity());
//
//            cart.getCartItems().add(cartItem);
//        }
//
//        return cartRepository.save(cart);
//    }
}
