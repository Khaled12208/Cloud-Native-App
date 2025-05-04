# Cloud Native Application

This is a cloud-native application built with Spring Boot, Spring Cloud, and Spring Security. The application is designed to be scalable and highly available, following cloud-native principles and best practices.

## Technology Stack

- Java 17
- Spring Boot 3.x
- Spring Cloud
- Spring Security
- Maven
- Docker (for containerization)
- Kubernetes (for orchestration)

## Project Structure

```
cloud-native-app/
├── api-gateway/                 # API Gateway Service
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── pom.xml                      # Root POM file
└── README.md
```

## Features

- Microservices Architecture
- Service Discovery
- API Gateway
- Circuit Breaker Pattern
- Distributed Configuration
- Security with OAuth2/JWT
- Containerization
- Kubernetes Deployment

## Prerequisites

- JDK 17 or later
- Maven 3.8.x or later
- Docker
- Kubernetes (for local development, you can use Minikube or Docker Desktop with Kubernetes enabled)

## Getting Started

1. Clone the repository

```bash
git clone [repository-url]
cd cloud-native-app
```

2. Build the project

```bash
mvn clean install
```

3. Run the API Gateway

```bash
cd api-gateway
mvn spring-boot:run
```

## Development

This project follows a microservices architecture. Each service is developed as a separate module with its own configuration and dependencies.

### Available Services

1. API Gateway (Port: 8080)
   - Entry point for all client requests
   - Route management
   - Request/Response transformation
   - Security

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
