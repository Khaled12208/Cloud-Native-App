package com.example.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ApiKeyValidationFilter extends AbstractGatewayFilterFactory<ApiKeyValidationFilter.Config> {

    @Value("${api.key}")
    private String apiKey;

    public ApiKeyValidationFilter() {
        super(Config.class);
    }

    @Override
    public String name() {
        return "ApiKeyValidation";
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String requestApiKey = exchange.getRequest().getHeaders().getFirst("X-API-Key");
            
            if (requestApiKey == null || !requestApiKey.equals(apiKey)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties if needed
    }
} 