# 🧠 Smart Schedule - AI Task Management System

## 🚀 Overview

**Smart Schedule** is an intelligent task management system built using a **microservices architecture**.  
It combines **AI (OpenAI API)** with **Spring Boot** to help users:

- Manage personal or team tasks and schedules  
- Receive **push notifications** and reminders  
- Get **AI-generated suggestions** for optimal time management  

> 💡 Goal: Not just to "list tasks", but to **optimize personal productivity** using AI.

---

## 🏗️ System Architecture

Smart Schedule consists of **7 microservices**, connected through **API Gateway** and **Eureka Discovery Server**.

| Service | Description |
|----------|-------------|
| **API Gateway** | Handles JWT authentication and routes requests to backend services. |
| **Auth Service** | Manages user login, registration, token generation, and validation. |
| **User Service** | Manages user profiles, roles (ROLE_USER, ROLE_ADMIN), and permissions. |
| **Task Service** | Handles task CRUD operations and reminder notifications. |
| **AI Service** | Integrates with **OpenAI API** via Spring AI to suggest optimized schedules. |
| **Product / Order Service** | Demonstrates extensibility and inter-service communication. |
| **Discovery Server** | Registers and discovers all microservices (Eureka Server). |

All services communicate via **REST APIs** and use **JWT (JSON Web Token)** for authentication.

---

## 🧩 Technologies Used

| Component | Technology |
|------------|-------------|
| **Backend Framework** | Spring Boot 3, Spring WebFlux, Spring Security, Spring Cloud |
| **Authentication** | JWT (JSON Web Token) with HMAC SHA512 |
| **Service Discovery** | Spring Cloud Netflix Eureka |
| **API Gateway** | Spring Cloud Gateway |
| **AI Integration** | Spring AI (OpenAI API) |
| **Frontend (Optional)** | Next.js + TailwindCSS |
| **Database** | PostgreSQL |
| **Containerization** | Docker & Docker Compose |

---

