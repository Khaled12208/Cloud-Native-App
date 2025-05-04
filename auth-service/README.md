# Auth Service

The Auth Service is responsible for user authentication and authorization in the cloud-native microservices application. It provides JWT-based authentication and role-based access control.

## Features

- User registration and authentication
- JWT token generation and validation
- Role-based access control
- Password encryption using BCrypt
- H2 database integration
- Swagger/OpenAPI documentation

## Configuration

### Application Properties

```yaml
server:
  port: 8081

spring:
  application:
    name: auth-service
  datasource:
    url: jdbc:h2:mem:authdb
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

jwt:
  secret: your-secret-key
  expiration: 86400000 # 24 hours

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

## API Endpoints

### Authentication

#### Register User

- **POST** `/api/v1/auth/register`
- **Description**: Register a new user
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string",
    "email": "string",
    "role": "USER"
  }
  ```
- **Response**: JWT token and user details

#### Authenticate User

- **POST** `/api/v1/auth/authenticate`
- **Description**: Authenticate a user and get JWT token
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response**: JWT token and user details

#### Validate Token

- **GET** `/api/v1/auth/validate`
- **Description**: Validate a JWT token
- **Headers**:
  - `Authorization: Bearer <token>`
- **Response**: Token validation status

## Security

### JWT Configuration

- Token expiration: 24 hours
- Secret key: Configurable through application properties
- Token format: `Bearer <token>`

### Password Security

- BCrypt encryption
- Salt rounds: 10

### Role-Based Access

- USER: Basic access
- ADMIN: Administrative access

## Database

### H2 Console

- URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:authdb
- Username: sa
- Password: password

### Schema

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL
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

- URL: http://localhost:8081/swagger-ui.html

4. Access H2 Console:

- URL: http://localhost:8081/h2-console

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- Spring Cloud Netflix Eureka Client
- SpringDoc OpenAPI UI
- H2 Database
- JWT

## Monitoring

Actuator endpoints:

- Health: http://localhost:8081/actuator/health
- Metrics: http://localhost:8081/actuator/metrics
- Environment: http://localhost:8081/actuator/env

## Troubleshooting

### Common Issues

1. Port Conflict

   - Check if port 8081 is available
   - Use `lsof -i :8081` to check port usage
   - Kill the process using the port: `kill -9 <PID>`

2. Database Connection

   - Verify H2 console connection
   - Check database configuration
   - Ensure schema is created correctly

3. JWT Issues
   - Verify secret key configuration
   - Check token expiration
   - Validate token format

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
