package com.app.ecom.repository;

import com.app.ecom.entity.CartItem;
import com.app.ecom.entity.Product;
import com.app.ecom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User availableUser, Product availableProduct);

    void deleteByUserAndProduct(User availableUser, Product availableProduct);

    List<CartItem> findByUser(User user);
}