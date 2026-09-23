package com.app.ecom.serviceimpl;

import com.app.ecom.dto.ProductRequestDTO;
import com.app.ecom.dto.ProductResponseDTO;
import com.app.ecom.entity.Product;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToResponseFromProduct)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponseFromProduct)
                .orElseThrow(() -> new RuntimeException("Product with id not found " + id));
    }

    @Override
    public List<ProductResponseDTO> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToResponseFromProduct)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequest) {
        Product product = new Product();
        mapToProductFromRequest(product, productRequest);
        Product savedProduct = productRepository.save(product);
        return mapToResponseFromProduct(savedProduct);
    }

    @Override
    public Optional<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    mapToProductFromRequest(existingProduct, updatedProduct);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToResponseFromProduct(savedProduct);
                });
    }

    @Override
    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    private ProductResponseDTO mapToResponseFromProduct(Product savedProduct) {
        ProductResponseDTO response = new ProductResponseDTO();
        response.setId(savedProduct.getId());
        response.setActive(savedProduct.getActive());
        response.setCategory(savedProduct.getCategory());
        response.setDescription(savedProduct.getDescription());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setName(savedProduct.getName());
        response.setPrice(savedProduct.getPrice());
        return response;
    }

    private void mapToProductFromRequest(Product product, ProductRequestDTO requestDTO) {
        product.setName(requestDTO.getName());
        product.setCategory(requestDTO.getCategory());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setImageUrl(requestDTO.getImageUrl());
        product.setStockQuantity(requestDTO.getStockQuantity());
    }
}