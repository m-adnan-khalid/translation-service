# Translation Management Service

![Java](https://img.shields.io/badge/java-17-blue)
![Spring Boot](https://img.shields.io/badge/spring%20boot-3.2-green)
![Maven](https://img.shields.io/badge/maven-3.8%2B-orange)
![EhCache](https://img.shields.io/badge/ehcache-3.10-lightgrey)
![Swagger](https://img.shields.io/badge/swagger-3.0-lightgreen)

## Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [API Documentation](#api-documentation)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Design Decisions](#design-decisions)
- [License](#license)

## Overview

A high-performance API for managing multilingual translations with support for multiple locales and tags, designed for scalability and performance.

## Key Features

- **Multi-locale Support**: Store translations for en, fr, es, etc.
- **Tagging System**: Organize translations by context (web, mobile, desktop)
- **High Performance**:
  - Response times <200ms for all endpoints
  - <500ms for JSON exports
  - EhCache caching layer
  - Optimized SQL queries with proper indexing
- **Security**:
  - JWT Authentication
  - BCrypt password encoding
  - Role-based access control
- **Developer Friendly**:
  - Complete OpenAPI 3.0 documentation
  - Docker support
  - 95%+ test coverage
  - Pre-loaded with 100k+ test records

## Project Structure

```text
📦translation-service
├── .gitignore
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── app
│   │   │           └── translationservice
│   │   │               ├── config/
│   │   │               ├── controller/
│   │   │               ├── dto/
│   │   │               ├── exception/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               ├── security/
│   │   │               └── service/
│   │   └── resources
│   │       ├── application.yml
│   │       ├── ehcache.xml
│   │      
│   └── test
│       └── java
│           └── com
│               └── app
│                   └── translationservice
│                       ├── controller/
│                       └── service/
└── README.md
```

## Installation

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker 20.10+ (optional)

### Running Locally

Clone the repository:

```bash
git clone https://github.com/your-username/translation-service.git
cd translation-service
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

### Docker Setup

Build and start containers:

```bash
docker-compose up --build
```

Access the application at:

```
http://localhost:8080
```

## API Documentation

Interactive API documentation available via:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI Spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## API Endpoints

### Authentication

**POST /api/v1/auth/authenticate** — Get JWT token for API access

```bash
curl -X POST http://localhost:8080/api/v1/auth/authenticate   -H "Content-Type: application/json"   -d '{"email":"api-user@example.com","password":"password123"}'
```

### Translation Management

**POST /api/v1/translations** — Create Translation

```bash
curl -X POST http://localhost:8080/api/v1/translations   -H "Authorization: Bearer YOUR_JWT_TOKEN"   -H "Content-Type: application/json"   -d '{
    "locale": "en",
    "key": "welcome.message",
    "content": "Welcome to our platform",
    "tags": ["web", "mobile"]
  }'
```

**GET /api/v1/translations/{id}** — Get Translation

```bash
curl -X GET http://localhost:8080/api/v1/translations/1   -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**GET /api/v1/translations/search?keyword={keyword}&tags={tag1}&tags={tag2}** — Search Translations

```bash
curl -X GET "http://localhost:8080/api/v1/translations/search?keyword=welcome&tags=web"   -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**GET /api/v1/translations/export/{locale}** — Export Translations

```bash
curl -X GET http://localhost:8080/api/v1/translations/export/en   -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Testing

Run unit and integration tests:

```bash
mvn test
```

Generate test coverage report:

```bash
mvn jacoco:report
```

## Design Decisions

### Architecture

- Clean Architecture with clear separation of concerns
- Repository-Service-Controller pattern
- Interface-based service implementations

### Performance

- EhCache with 2-level caching (heap + offheap)
- Optimized queries with proper indexing
- Pagination for large result sets
- Bulk operations for initial data loading

### Security

- JWT Authentication with stateless sessions
- BCrypt password hashing
- Role-based access control
- Secure headers configuration

### Maintainability

- Swagger documentation
- Dockerized environment
- CI/CD ready structure

## License

This project is licensed under the MIT License.
