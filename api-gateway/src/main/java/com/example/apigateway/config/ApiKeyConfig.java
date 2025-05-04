package com.example.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class ApiKeyConfig {

    @Value("${api.key}")
    private String apiKey;

    @Bean
    public WebFilter apiKeyFilter() {
        return (exchange, chain) -> {
            String requestApiKey = exchange.getRequest().getHeaders().getFirst("X-API-Key");
            
            if (requestApiKey == null || !requestApiKey.equals(apiKey)) {
                return Mono.error(new RuntimeException("Invalid API Key"));
            }
            
            return chain.filter(exchange);
        };
    }
} 