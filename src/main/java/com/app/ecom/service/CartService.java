package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequestDTO;
import com.app.ecom.entity.CartItem;

import java.util.List;

public interface CartService {
    boolean addToCart(String userId, CartItemRequestDTO request);

    boolean deleteItemFromCart(String userId, Long productId);

    List<CartItem> getCartItems(String userId);
}