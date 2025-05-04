# Inventory Service

The Inventory Service manages product inventory and stock levels in the cloud-native microservices application. It provides real-time stock management, reservation system, and integration with the Product Service.

## Features

- Real-time inventory management
- Stock level tracking
- Reservation system
- Integration with Product Service
- H2 database integration
- Swagger/OpenAPI documentation
- Service discovery integration

## Configuration

### Application Properties

```yaml
server:
  port: 8082

spring:
  application:
    name: inventory-service
  datasource:
    url: jdbc:h2:mem:inventorydb
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

### Inventory Management

#### Get All Inventory Items

- **GET** `/api/inventory`
- **Description**: Retrieve all inventory items
- **Query Parameters**:
  - `page` (optional): Page number (default: 0)
  - `size` (optional): Page size (default: 10)
  - `sort` (optional): Sort field (default: id)
- **Response**: List of inventory items with pagination

#### Get Inventory Item by ID

- **GET** `/api/inventory/{id}`
- **Description**: Retrieve an inventory item by its ID
- **Path Parameters**:
  - `id`: Inventory item ID
- **Response**: Inventory item details

#### Create Inventory Item

- **POST** `/api/inventory`
- **Description**: Create a new inventory item
- **Request Body**:
  ```json
  {
    "productId": "string",
    "quantity": 0,
    "location": "string",
    "sku": "string"
  }
  ```
- **Response**: Created inventory item details

#### Update Inventory Item

- **PUT** `/api/inventory/{id}`
- **Description**: Update an existing inventory item
- **Path Parameters**:
  - `id`: Inventory item ID
- **Request Body**: Same as Create Inventory Item
- **Response**: Updated inventory item details

#### Delete Inventory Item

- **DELETE** `/api/inventory/{id}`
- **Description**: Delete an inventory item
- **Path Parameters**:
  - `id`: Inventory item ID
- **Response**: Success message

### Stock Management

#### Get Available Stock

- **GET** `/api/inventory/{id}/stock`
- **Description**: Get available stock for an inventory item
- **Path Parameters**:
  - `id`: Inventory item ID
- **Response**: Current stock level

#### Create Reservation

- **POST** `/api/inventory/{id}/reserve`
- **Description**: Create a stock reservation
- **Path Parameters**:
  - `id`: Inventory item ID
- **Request Body**:
  ```json
  {
    "quantity": 0,
    "orderId": "string",
    "expiryMinutes": 30
  }
  ```
- **Response**: Reservation details

#### Confirm Reservation

- **POST** `/api/inventory/reservations/{id}/confirm`
- **Description**: Confirm a stock reservation
- **Path Parameters**:
  - `id`: Reservation ID
- **Response**: Confirmation status

#### Cancel Reservation

- **POST** `/api/inventory/reservations/{id}/cancel`
- **Description**: Cancel a stock reservation
- **Path Parameters**:
  - `id`: Reservation ID
- **Response**: Cancellation status

## Database

### H2 Console

- URL: http://localhost:8082/h2-console
- JDBC URL: jdbc:h2:mem:inventorydb
- Username: sa
- Password: password

### Schema

```sql
CREATE TABLE inventory_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    location VARCHAR(50),
    sku VARCHAR(50) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventory_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    order_id VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_item_id) REFERENCES inventory_items(id)
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

- URL: http://localhost:8082/swagger-ui.html

4. Access H2 Console:

- URL: http://localhost:8082/h2-console

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Cloud Netflix Eureka Client
- SpringDoc OpenAPI UI
- H2 Database
- Lombok

## Monitoring

Actuator endpoints:

- Health: http://localhost:8082/actuator/health
- Metrics: http://localhost:8082/actuator/metrics
- Environment: http://localhost:8082/actuator/env

## Troubleshooting

### Common Issues

1. Port Conflict

   - Check if port 8082 is available
   - Use `lsof -i :8082` to check port usage
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
