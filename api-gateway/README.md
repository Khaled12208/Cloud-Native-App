# API Gateway

The API Gateway is the entry point for all client requests in the cloud-native microservices application. It provides routing, security, and request/response transformation capabilities.

## Features

- Request routing
- Security integration
- Load balancing
- Circuit breaking
- Request/response transformation
- Swagger/OpenAPI documentation
- Service discovery integration

## Configuration

### Application Properties

```yaml
server:
  port: 8083

spring:
  application:
    name: api-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
          lower-case-service-id: true
      routes:
        - id: auth-service
          uri: lb://auth-service
          predicates:
            - Path=/api/v1/auth/**
          filters:
            - StripPrefix=1
        - id: product-service
          uri: lb://product-service
          predicates:
            - Path=/api/v1/products/**
          filters:
            - StripPrefix=1
        - id: inventory-service
          uri: lb://inventory-service
          predicates:
            - Path=/api/v1/inventory/**
          filters:
            - StripPrefix=1

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

## API Routes

### Auth Service Routes

- **Base Path**: `/api/v1/auth`
- **Service**: auth-service
- **Endpoints**:
  - POST /register - Register a new user
  - POST /authenticate - Authenticate a user
  - GET /validate - Validate a JWT token

### Product Service Routes

- **Base Path**: `/api/v1/products`
- **Service**: product-service
- **Endpoints**:
  - GET / - Get all products
  - GET /{id} - Get product by ID
  - GET /category/{category} - Get products by category
  - GET /brand/{brand} - Get products by brand
  - POST / - Create product
  - PUT /{id} - Update product
  - DELETE /{id} - Delete product

### Inventory Service Routes

- **Base Path**: `/api/v1/inventory`
- **Service**: inventory-service
- **Endpoints**:
  - GET / - Get all inventory items
  - GET /{id} - Get inventory item by ID
  - POST / - Create inventory item
  - PUT /{id} - Update inventory item
  - DELETE /{id} - Delete inventory item
  - GET /{id}/stock - Get available stock
  - POST /{id}/reserve - Create reservation
  - POST /reservations/{id}/confirm - Confirm reservation
  - POST /reservations/{id}/cancel - Cancel reservation

## Security

### API Key Validation

- Required for all requests
- Header: `X-API-Key`
- Value: Configured in application properties

### JWT Token Validation

- Required for protected endpoints
- Header: `Authorization: Bearer <token>`
- Token validation through Auth Service

### IP-based Access Control

- Whitelist configuration
- Rate limiting per IP
- Request throttling

## Running the Service

1. Build the service:

```bash
./mvnw clean install
```

2. Run the service:

```bash
./mvnw spring-boot:run
```

3. Access Swagger UI:

- URL: http://localhost:8083/swagger-ui.html

## Dependencies

- Spring Boot Starter Web
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka Client
- SpringDoc OpenAPI UI
- Spring Security

## Monitoring

Actuator endpoints:

- Health: http://localhost:8083/actuator/health
- Metrics: http://localhost:8083/actuator/metrics
- Environment: http://localhost:8083/actuator/env
- Gateway: http://localhost:8083/actuator/gateway

## Troubleshooting

### Common Issues

1. Port Conflict

   - Check if port 8083 is available
   - Use `lsof -i :8083` to check port usage
   - Kill the process using the port: `kill -9 <PID>`

2. Routing Issues

   - Check route configurations
   - Verify service discovery
   - Check service health status

3. Security Issues
   - Verify API key configuration
   - Check JWT token validation
   - Verify IP whitelist

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
