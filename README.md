# Spring Kafka Microservices

This project is a demonstration of a microservices architecture using Spring Boot and Apache Kafka. It consists of two modules: `user-service` and `notification-service`. 

## Project Structure

```
spring-kafka-microservices
├── user-service
├── notification-service
├── docker-compose.yml
└── pom.xml
```

### Modules

1. **user-service**: 
   - A Spring Boot application that exposes a REST endpoint to sign up users.
   - Sends user emails to a Kafka topic named `user-signups`.

2. **notification-service**: 
   - A Spring Boot application that consumes messages from the `user-signups` Kafka topic.
   - Logs the received emails to the console.

## Prerequisites

- Java 11 or higher
- Maven
- Docker and Docker Compose

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd spring-kafka-microservices
   ```

2. **Start Kafka using Docker**:
   Run the following command to start Kafka and Zookeeper:
   ```bash
   docker-compose up -d
   ```

3. **Build the project**:
   Navigate to the root directory of the project and run:
   ```bash
   mvn clean install
   ```

4. **Run the services**:
   You can run each service separately:
   - For `user-service`:
     ```bash
     cd user-service
     mvn spring-boot:run
     ```
   - For `notification-service`:
     ```bash
     cd notification-service
     mvn spring-boot:run
     ```

## Usage

- To sign up a user, send a POST request to the `user-service`:
  ```
  POST http://localhost:8080/signup
  Content-Type: application/json

  {
      "email": "user@example.com"
  }
  ```

- The `notification-service` will log the email received from the Kafka topic.

## Notes

- Ensure that Docker is running before starting the services.
- You can check the logs of the `notification-service` to see the emails being logged.

## License

This project is licensed under the MIT License.