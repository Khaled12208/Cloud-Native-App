# Eureka Server

The Eureka Server is the service discovery component of the cloud-native microservices application. It provides service registration and discovery capabilities for all microservices.

## Features

- Service registration and discovery
- Health monitoring
- Load balancing support
- High availability configuration
- Swagger/OpenAPI documentation
- Service status dashboard

## Configuration

### Application Properties

```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server
  security:
    user:
      name: admin
      password: admin

eureka:
  client:
    register-with-eureka: false
    fetch-registry: false
  server:
    wait-time-in-ms-when-sync-empty: 0
    enable-self-preservation: false

springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    path: /swagger-ui.html
```

## Service Registration

### Registered Services

1. **Auth Service**

   - Port: 8081
   - Service ID: auth-service
   - Health Check: /actuator/health

2. **Product Service**

   - Port: 8084
   - Service ID: product-service
   - Health Check: /actuator/health

3. **Inventory Service**

   - Port: 8082
   - Service ID: inventory-service
   - Health Check: /actuator/health

4. **API Gateway**
   - Port: 8083
   - Service ID: api-gateway
   - Health Check: /actuator/health

## Dashboard

### Eureka Dashboard

- URL: http://localhost:8761
- Username: admin
- Password: admin

### Features

- Service status overview
- Instance details
- Health status
- Metadata information
- Service statistics

## Running the Service

1. Build the service:

```bash
./mvnw clean install
```

2. Run the service:

```bash
./mvnw spring-boot:run
```

3. Access Eureka Dashboard:

- URL: http://localhost:8761

4. Access Swagger UI:

- URL: http://localhost:8761/swagger-ui.html

## Dependencies

- Spring Boot Starter Web
- Spring Cloud Netflix Eureka Server
- Spring Boot Starter Security
- SpringDoc OpenAPI UI

## Monitoring

Actuator endpoints:

- Health: http://localhost:8761/actuator/health
- Metrics: http://localhost:8761/actuator/metrics
- Environment: http://localhost:8761/actuator/env

## Troubleshooting

### Common Issues

1. Port Conflict

   - Check if port 8761 is available
   - Use `lsof -i :8761` to check port usage
   - Kill the process using the port: `kill -9 <PID>`

2. Service Registration Issues

   - Check service configuration
   - Verify network connectivity
   - Check service health status

3. Authentication Issues
   - Verify username and password
   - Check security configuration
   - Ensure proper credentials in service configurations

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
