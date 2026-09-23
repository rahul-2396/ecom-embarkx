package com.app.ecom.service;

import com.app.ecom.dto.ProductRequestDTO;
import com.app.ecom.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO createProduct(ProductRequestDTO productRequest);

    Optional<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO updatedProduct);

    boolean deleteProduct(Long id);

    List<ProductResponseDTO> searchProducts(String keyword);
}