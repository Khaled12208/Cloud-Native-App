package com.example.productservice.service;

import com.example.productservice.client.InventoryClient;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;

    @Value("${api.key}")
    private String apiKey;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(this::enrichWithStockInfo);
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::enrichWithStockInfo);
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        products.forEach(this::enrichWithStockInfo);
        return products;
    }

    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        products.forEach(this::enrichWithStockInfo);
        return products;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product enrichWithStockInfo(Product product) {
        if (product.getInventoryId() != null) {
            try {
                String bearerToken = getBearerToken();
                Map<String, Integer> stockInfo = inventoryClient.getAvailableStock(
                    product.getInventoryId(),
                    apiKey,
                    bearerToken
                );
                product.setAvailableStock(stockInfo.get("availableStock"));
            } catch (Exception e) {
                product.setAvailableStock(0);
            }
        } else {
            product.setAvailableStock(0);
        }
        return product;
    }

    private String getBearerToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String authHeader = attributes.getRequest().getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader;
            }
        }
        throw new RuntimeException("No bearer token found in request");
    }
} 