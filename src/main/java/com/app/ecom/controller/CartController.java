package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequestDTO;
import com.app.ecom.entity.CartItem;
import com.app.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    private ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                             @RequestBody CartItemRequestDTO request) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Product out of stock or User/Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    private ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,
                                                @PathVariable Long productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/items")
    private ResponseEntity<List<CartItem>> fetchCart(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}