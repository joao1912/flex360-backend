# Flex360 API: Comprehensive E-commerce Solution for Office Chairs

The Flex360 API is a robust Spring Boot application designed to power an e-commerce platform specializing in ergonomic office chairs and accessories. It provides a complete backend solution for managing products, user accounts, shopping carts, and order processing.

## Repository Structure

The repository is organized as follows:

- `src/main/java/com/flex360/api_flex360/`: Main application code
  - `controllers/`: REST controllers for handling HTTP requests
  - `dto/`: Data Transfer Objects for API requests and responses
  - `exceptions/`: Custom exception classes
  - `infra/`: Infrastructure configurations (security, caching, CORS)
  - `models/`: Entity classes representing database tables
  - `repository/`: Spring Data JPA repositories
  - `services/`: Business logic implementation
- `src/main/resources/`: Application properties and static resources
- `src/test/`: Unit and integration tests
- `Dockerfile`: Instructions for building a Docker image of the application
- `docker-compose.yaml`: Docker Compose configuration for local development
- `pom.xml`: Maven project configuration file

## Usage Instructions

### Prerequisites

- Java 22
- Maven 3.9.4 or later
- Docker and Docker Compose (for local development with containerized services)

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   cd api-flex360
   ```

2. Build the application:
   ```
   mvn clean package -DskipTests
   ```

3. Run the application using Docker Compose:
   ```
   docker-compose up -d
   ```

This will start the Flex360 API, a PostgreSQL database, and pgAdmin for database management.

### Configuration

The application can be configured using environment variables or by modifying the `application.properties` file. Key configuration options include:

- `POSTGRES_DB`: Database name (default: devFlex360)
- `POSTGRES_USER`: Database username (default: postgres)
- `POSTGRES_PASSWORD`: Database password
- `HTTP_ORIGIN`: Allowed origins for CORS (default: http://*.us-east-1.elb.amazonaws.com)

### API Endpoints

The API provides the following main endpoints:

- `/auth`: Authentication and user registration
- `/usuario`: User management
- `/cadeira`: Chair product management
- `/acessorio`: Accessory product management
- `/carrinho`: Shopping cart operations
- `/cor`: Color management for products

For detailed API documentation, access the Swagger UI at `http://localhost:8081/swagger-ui.html` when running the application locally.

### Testing

Run the unit tests using:

```
mvn test
```

### Troubleshooting

- If you encounter connection issues with the database, ensure that the PostgreSQL container is running and healthy:
  ```
  docker-compose ps
  ```
- Check the application logs for any error messages:
  ```
  docker-compose logs flex360-api
  ```
- Verify that the environment variables in the `docker-compose.yaml` file match your local setup.

## Data Flow

1. Client sends a request to the Flex360 API.
2. The request is intercepted by the `SecurityFilter` for authentication.
3. If authenticated, the request is routed to the appropriate controller.
4. The controller calls the corresponding service method.
5. The service interacts with the repository to perform database operations.
6. The repository executes the database query.
7. Results are transformed into DTOs and returned to the client.

```
Client -> SecurityFilter -> Controller -> Service -> Repository -> Database
   ^                                                                  |
   |                                                                  |
   +------------------<-------------------<-------------------<-------+
```

## Deployment

The application is containerized and can be deployed to any Docker-compatible environment. For production deployment, consider the following steps:

1. Build the Docker image:
   ```
   docker build -t flex360-api .
   ```

2. Push the image to a container registry.

3. Deploy the image to your production environment, ensuring that environment variables are properly set for database connection and security configurations.

## Infrastructure

The application uses the following key infrastructure components:

- **PostgreSQL Database**: Persistent storage for product and user data.
- **Redis**: Used for caching to improve performance.
- **Spring Security**: Handles authentication and authorization.
- **Docker**: Containerization for consistent deployment across environments.