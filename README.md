# 🧠 Smart Schedule - AI Task Management System

---

## 📝 I. Description

**Smart Schedule** is an intelligent **AI-powered task management web application** developed with **Spring Boot microservices** architecture.  
It helps users and teams efficiently manage tasks, schedules, and time through automation and AI suggestions.

**Main Objectives:**
- ✅ Organize and track daily or team tasks  
- 🔔 Receive real-time reminders and push notifications  
- 🤖 Get AI-based schedule optimization and productivity insights  
- 💬 Support multi-service communication and modular scaling  

> 💡 The system doesn’t just “list tasks” — it uses **AI to optimize your time** and improve personal productivity.

---

## ⚙️ II. Technologies, Architecture & Features

### 🧩 1. Technologies Used

| Layer | Technology |
|-------|-------------|
| **Backend Framework** | Spring Boot 3, Spring WebFlux |
| **Service Discovery** | Spring Cloud Netflix Eureka |
| **API Gateway** | Spring Cloud Gateway |
| **Authentication** | JWT (HMAC SHA512) |
| **AI Integration** | Spring AI + OpenAI API |
| **Database** | PostgreSQL |
| **Media Storage** | Cloudinary |
| **Frontend (Optional)** | Next.js + TailwindCSS |
| **Notifications** | Web Push |
| **Containerization** | Docker & Docker Compose |

---

### 🏗️ 2. System Architecture

Smart Schedule is built with **7 main microservices**, connected through **API Gateway** and **Eureka Discovery Server**.

| Service | Description |
|----------|-------------|
| **API Gateway** | Central entry point; handles authentication & routing. |
| **Auth Service** | Login, registration, token management (JWT). |
| **User Service** | User profiles, roles, permissions. |
| **Task Service** | Task CRUD, scheduling, reminder logic | Push notifications
| **AI Service** | Connects to OpenAI for task/schedule suggestions. |
| **Discovery Server** | Eureka registry for all services. |

---

### 🧱 3. Project Structure

smart-schedule/
│
├── api-gateway/
├── auth-service/
├── user-service/
├── task-service/
├── ai-service/
├── discovery-server/
└── docker-compose.yml

yaml
Sao chép mã

Each service is an independent **Spring Boot module** with its own configuration, database, and Docker container.

---

### ⚡ 4. Main Features

| Category | Feature |
|-----------|----------|
| 👤 **User Management** | Registration, login, JWT-based authentication, role-based access control |
| 📅 **Task Management** | Create, update, delete, and view personal or team tasks |
| 🔔 **Notifications** | Web push notifications for task deadlines and reminders |
| 🤖 **AI Assistant** | Smart scheduling suggestions using OpenAI (via Spring AI) |
| 💾 **Media Storage** | Upload user avatars or task attachments to Cloudinary |
| 🧩 **Microservices Architecture** | Each service runs independently and communicates via REST |
| 🔒 **Security** | Spring Security + JWT validation at the API Gateway |
| 📊 **Scalability** | Easily deployable using Docker Compose and scalable via Kubernetes (future-ready) |

---

## 🧰 III. Installation & Setup Guide

### 🐳 1. Prerequisites
Before running the project, make sure you have:
- Java 17+  
- Maven or Gradle  
- PostgreSQL (local or Docker)  
- Docker & Docker Compose  
- Node.js (if using the Next.js frontend)  
- OpenAI API key (for AI service)  

---

### ⚙️ 2. Configuration

Create an `.env` file or set environment variables:

```env
JWT_SECRET=your_jwt_secret
OPENAI_API_KEY=your_openai_api_key
CLOUDINARY_URL=cloudinary://<api_key>:<api_secret>@<cloud_name>
POSTGRES_URL=jdbc:postgresql://localhost:5432/smart_schedule
POSTGRES_USER=postgres
POSTGRES_PASSWORD=123456
SPRING_PROFILES_ACTIVE=dev
🧩 3. Run the Project
Option 1: Using Docker Compose (recommended)

docker-compose up --build
Option 2: Run each service manually

cd discovery-server && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd auth-service && mvn spring-boot:run
cd user-service && mvn spring-boot:run
cd task-service && mvn spring-boot:run
cd ai-service && mvn spring-boot:run
🔐 4. Authentication Flow
User logs in → Auth Service verifies credentials

Auth Service returns a JWT token

API Gateway validates the token

Requests are forwarded to internal microservices (Task, User, AI, etc.)

Example JWT payload:

{
  "sub": "123",
  "roles": ["ROLE_USER"],
  "scope": ["read", "write"]
}
🤖 5. Example AI Endpoint

POST /api/ai/suggest-schedule
Request:
{
  "tasks": [
    { "title": "Finish report", "priority": "HIGH", "deadline": "2025-10-23T17:00:00" },
    { "title": "Workout", "priority": "MEDIUM" }
  ],
  "availableHours": "09:00-18:00"
}
Response:

{
  "suggestion": "Focus on completing the report before 2PM, then take a break and schedule your workout at 5PM."
}
📈 Roadmap
 Core microservices (Auth, User, Task, AI)

 JWT + API Gateway integration

 AI schedule recommendation

 Web push notifications

 Frontend (Next.js) integration

 Calendar & timeline visualization

 CI/CD pipeline with Docker

👥 Contributors
Name	Role
Tài	Lead Developer
Mentor / Supervisor	System Architect

📄 License
This project is licensed under the MIT License.

🌟 Acknowledgments
Spring Boot

Spring Cloud

Spring AI

OpenAI API

Cloudinary

Docker

Next.js

