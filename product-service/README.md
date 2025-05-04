# Product Service

The Product Service manages product information in the cloud-native microservices application. It provides CRUD operations for products and integrates with the Inventory Service for stock management.

## Features

- Product management (CRUD operations)
- Category and brand filtering
- Integration with Inventory Service
- H2 database integration
- Swagger/OpenAPI documentation
- Service discovery integration

## Configuration

### Application Properties

```yaml
server:
  port: 8084

spring:
  application:
    name: product-service
  datasource:
    url: jdbc:h2:mem:productdb
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
      path: /h2-console

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

## API Endpoints

### Products

#### Get All Products

- **GET** `/products`
- **Description**: Retrieve all products
- **Query Parameters**:
  - `page` (optional): Page number (default: 0)
  - `size` (optional): Page size (default: 10)
  - `sort` (optional): Sort field (default: id)
- **Response**: List of products with pagination

#### Get Product by ID

- **GET** `/products/{id}`
- **Description**: Retrieve a product by its ID
- **Path Parameters**:
  - `id`: Product ID
- **Response**: Product details

#### Get Products by Category

- **GET** `/products/category/{category}`
- **Description**: Retrieve products by category
- **Path Parameters**:
  - `category`: Product category
- **Response**: List of products in the category

#### Get Products by Brand

- **GET** `/products/brand/{brand}`
- **Description**: Retrieve products by brand
- **Path Parameters**:
  - `brand`: Product brand
- **Response**: List of products by the brand

#### Create Product

- **POST** `/products`
- **Description**: Create a new product
- **Request Body**:
  ```json
  {
    "name": "string",
    "description": "string",
    "price": 0.0,
    "category": "string",
    "brand": "string",
    "sku": "string"
  }
  ```
- **Response**: Created product details

#### Update Product

- **PUT** `/products/{id}`
- **Description**: Update an existing product
- **Path Parameters**:
  - `id`: Product ID
- **Request Body**: Same as Create Product
- **Response**: Updated product details

#### Delete Product

- **DELETE** `/products/{id}`
- **Description**: Delete a product
- **Path Parameters**:
  - `id`: Product ID
- **Response**: Success message

## Database

### H2 Console

- URL: http://localhost:8084/h2-console
- JDBC URL: jdbc:h2:mem:productdb
- Username: sa
- Password: password

### Schema

```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    category VARCHAR(50),
    brand VARCHAR(50),
    sku VARCHAR(50) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

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

- URL: http://localhost:8084/swagger-ui.html

4. Access H2 Console:

- URL: http://localhost:8084/h2-console

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Cloud Netflix Eureka Client
- SpringDoc OpenAPI UI
- H2 Database
- Lombok

## Monitoring

Actuator endpoints:

- Health: http://localhost:8084/actuator/health
- Metrics: http://localhost:8084/actuator/metrics
- Environment: http://localhost:8084/actuator/env

## Troubleshooting

### Common Issues

1. Port Conflict

   - Check if port 8084 is available
   - Use `lsof -i :8084` to check port usage
   - Kill the process using the port: `kill -9 <PID>`

2. Database Connection

   - Verify H2 console connection
   - Check database configuration
   - Ensure schema is created correctly

3. Service Discovery
   - Ensure Eureka Server is running
   - Check service registration
   - Verify network connectivity

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
