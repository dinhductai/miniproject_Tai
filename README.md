# 📦 Order & User Management Microservices System

This project is a demo system built using a **Microservices architecture**, simulating a simple e-commerce application that includes **user** and **order** management functionalities.

---

## 1. Project Purpose 🎯

The main objective of this project is to demonstrate and apply the core principles of **Microservices architecture**, including:

* **Service Independence**: Each business service (User, Order) is developed, deployed, and scaled independently.
* **Data Decentralization**: Each service owns and manages its own database.
* **Service Discovery**: Services can dynamically find and communicate with each other in a distributed environment.
* **Centralized Entry Point**: An **API Gateway** is used as a single entry point for all external requests.

---

## 2. Overview 📝

The system consists of **four primary services** that work together, containerized using **Docker**:

* **User Service**: Manages all user-related operations (create, read, update, delete).
* **Order Service**: Manages all order-related operations. This service communicates with the User Service to retrieve customer information.
* **Discovery Server (Eureka)**: Acts as a "phone book" for the services. All other services register with it to be discoverable.
* **API Gateway**: Serves as the single entry point to the system, responsible for routing, authentication, and other cross-cutting concerns.

---

## 3. Technology Stack 🛠️

* **Backend**: Java, Spring Boot, Spring Cloud
* **Database**: PostgreSQL 16-alpine
* **Service Discovery**: Spring Cloud Netflix Eureka
* **API Gateway**: Spring Cloud Gateway
* **Containerization**: Docker, Docker Compose
* **ORM**: Spring Data JPA, Hibernate

---

## 4. Rationale for Technology Choices 🤔

* **Spring Boot & Spring Cloud**: Simplify configuration and provide a powerful ecosystem for microservices (Service Discovery, API Gateway, inter-service communication).
* **Eureka Discovery Server**: Services dynamically register and discover each other without hardcoding IPs, enhancing resilience and flexibility.
* **Spring Cloud Gateway**:

  * Creates a **Single Facade**: Clients only need to know a single entry point.
  * Handles **Security**: Centralized authentication/authorization.
  * Enables **Intelligent Routing**: Routes requests to the correct service.
* **Docker & Docker Compose**: Provide consistent runtime environments and simple deployment with one command.
* **PostgreSQL (Database-per-Service)**: Ensures **loose coupling** with one database per service (`user_db` for User Service, `order_db` for Order Service).

---

## 5. System Architecture 🏛️
<img width="412" height="620" alt="image" src="https://github.com/user-attachments/assets/9fcf2af9-e17e-4d89-a71b-000a96895cde" />

The system follows a **containerized Microservices pattern**.

### Component Details

#### 1. API Gateway

* **Role**: Single entry point for all client requests.
* **Port**: 8080.
* **Routing**:

  * `/api/users/**` → `user-service`
  * `/api/orders/**` → `order-service`
* **Service Discovery Integration**: Uses `lb://` scheme to resolve services via Eureka.

#### 2. Discovery Server (Eureka)

* **Role**: Central registry of services.
* **Name**: discovery-server.
* **Port**: 8761.
* **Config**: `register-with-eureka: false`, `fetch-registry: false`.

#### 3. User Service

* **Role**: Manages user business logic.
* **Name**: user-service.
* **Port**: 9001.
* **Database**: `postgres-user-db:5432`, database: `user_db`.
* **Eureka Registration**: `http://discovery-server:8761/eureka/`.

#### 4. Order Service

* **Role**: Manages order business logic.
* **Name**: order-service.
* **Port**: 9002.
* **Database**: `postgres-order-db:5432`, database: `order_db`.
* **Eureka Registration**: `http://discovery-server:8761/eureka/`.

---

### Workflows

#### Flow 1: Client Request

1. Client sends HTTP request → API Gateway (e.g., `GET http://localhost:8080/api/users/123`).
2. Gateway checks routing rules → identifies `user-service`.
3. Gateway queries Eureka → gets address of a healthy `user-service`.
4. Gateway forwards request → User Service.
5. User Service queries `user_db` → returns result.
6. Response travels back via Gateway → Client.

#### Flow 2: Inter-Service Communication (Order ↔ User)

1. Order Service receives request with `userId`.
2. Needs user info → makes a call to `http://user-service/api/users/{userId}` (using OpenFeign).
3. Eureka resolves `user-service` to IP\:port.
4. Request goes directly → User Service.
5. User Service queries `user_db` → returns info.
6. Order Service combines data (order + user) → returns response.

---

## 6. Setup & Run 🚀

### Prerequisites

* Docker & Docker Compose
* Java JDK (for local builds)
* Maven or Gradle (for local builds)

### Running the System

```bash
git clone <your-repository-url>
cd <your-repository-directory>

# Build JAR files for each service
mvn clean package   # or ./gradlew build

# Run the system with Docker Compose
docker-compose up -d --build
```

### Verifying the System

* **Eureka Dashboard**: Open [http://localhost:8761](http://localhost:8761). You should see `API-GATEWAY`, `ORDER-SERVICE`, and `USER-SERVICE` registered.
* **API Endpoints**: Send requests via API Gateway → `http://localhost:8080`.

Examples:

```bash
curl http://localhost:8080/api/users/1
curl http://localhost:8080/api/orders/1
```

---

✅ This project demonstrates a complete **Microservices system** with **Spring Boot, Spring Cloud, Eureka, Gateway, PostgreSQL, and Docker Compose**. 

docker-compose down -v
docker-compose up --build
