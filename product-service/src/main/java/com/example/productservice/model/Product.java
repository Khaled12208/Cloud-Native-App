package com.example.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String brand;
    
    @Column(length = 1000)
    private String imageUrls;
    
    private String sku;
    private Long inventoryId; // Reference to the inventory item ID
    
    @Transient
    private Integer availableStock; // Not stored in DB, fetched from Inventory Service
} 