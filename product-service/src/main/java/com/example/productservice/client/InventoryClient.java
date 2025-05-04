package com.example.productservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "api-gateway")
public interface InventoryClient {
    @GetMapping("/api/v1/inventory/{id}/stock")
    Map<String, Integer> getAvailableStock(
        @PathVariable("id") Long id,
        @RequestHeader("X-API-Key") String apiKey,
        @RequestHeader("Authorization") String bearerToken
    );
} 