package com.e_commerceApi.E_CommerceAPI.service;

import com.e_commerceApi.E_CommerceAPI.dto.CartItemRequest;
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
import com.e_commerceApi.E_CommerceAPI.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartItemService {

    private final  CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartItem createCartItem(CartItemRequest cartItemRequest) {

        CartItem cartItem = new CartItem();
        JwtPrincipal jwtPrincipal = authService.getCurrentPrincipal(); // get current authenticated user
        Long userId = jwtPrincipal.userId();

        User user = userRepository.findById(userId).orElseThrow(()
                -> new UsernameNotFoundException("User not found"));

        Product product = productRepository.findById(
                cartItemRequest.getProductId()).orElseThrow(() ->
                new RuntimeException("product not found"));
        Cart cart = user.getCart();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setCart(cart); // link item to the cart
        cartItemRepository.save(cartItem);

        return cartItem;
    }


}
