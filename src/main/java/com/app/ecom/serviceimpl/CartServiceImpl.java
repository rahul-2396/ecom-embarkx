package com.app.ecom.serviceimpl;

import com.app.ecom.dto.CartItemRequestDTO;
import com.app.ecom.entity.CartItem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public boolean addToCart(String userId, CartItemRequestDTO request) {
        Optional<Product> product = productRepository.findById(request.getProductId());
        if (product.isEmpty()) {
            return false;
        }

        Product availableProduct = product.get();
        if (availableProduct.getStockQuantity() < request.getQuantity()) {
            return false;
        }

        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if (user.isEmpty()) {
            return false;
        }

        User availableUser = user.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(availableUser, availableProduct);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(availableProduct.getPrice()
                    .multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(availableUser);
            cartItem.setProduct(availableProduct);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.get().getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    @Override
    public boolean deleteItemFromCart(String userId, Long productId) {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        Optional<Product> product = productRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(user.get(), product.get());
            return true;
        }

        return false;
    }

    @Override
    public List<CartItem> getCartItems(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUser)
                .orElseGet(List::of);
    }
}