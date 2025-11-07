# Spring Security 6 - JWT Authentication System

A comprehensive Spring Boot application implementing JWT (JSON Web Token) based authentication and authorization with role-based access control (RBAC). This project demonstrates modern security practices using Spring Security 6 with stateless authentication.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Security Features](#security-features)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [Configuration](#configuration)
- [Usage Examples](#usage-examples)
- [Authentication Flow](#authentication-flow)
- [Database Schema](#database-schema)
- [Key Components](#key-components)

## 🎯 Overview

This application provides a secure REST API with JWT-based authentication, supporting three distinct user roles: **Principal**, **Teacher**, and **Student**. Each role has access to specific endpoints, ensuring proper authorization and security.

## ✨ Features

- **JWT Authentication**: Stateless token-based authentication
- **Role-Based Access Control (RBAC)**: Three roles (PRINCIPAL, TEACHER, STUDENT)
- **Password Encryption**: BCrypt password hashing with strength 12
- **Token Blacklisting**: Secure logout mechanism with token invalidation
- **Stateless Sessions**: No server-side session storage
- **CORS Configuration**: Cross-origin resource sharing support
- **MySQL Database**: Persistent user data storage
- **RESTful API**: Clean and well-structured API endpoints

## 🛠 Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Spring Security**: 6.x
- **Spring Data JPA**: For database operations
- **MySQL**: Database
- **JWT**: JSON Web Token (jjwt 0.12.6)
- **Lombok**: For reducing boilerplate code
- **Maven**: Build tool

## 🏗 Architecture

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       │ HTTP Request with JWT Token
       ▼
┌─────────────────────────────────────┐
│      JwtFilter (OncePerRequest)     │
│  - Extracts token from header        │
│  - Validates token                   │
│  - Checks blacklist                  │
│  - Sets SecurityContext              │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│      SecurityConfig                  │
│  - Configures security filter chain  │
│  - Role-based endpoint protection    │
│  - Authentication provider setup     │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────────────────────┐
│      Controllers                     │
│  - HomeController (Public)           │
│  - PrincipalController (PRINCIPAL)   │
│  - TeacherController (TEACHER)       │
│  - StudentController (STUDENT)       │
└──────────────────────────────────────┘
```

## 🔒 Security Features

### 1. **JWT Token Management**
   - Token expiration: 10 minutes
   - HMAC-SHA256 signing algorithm
   - Dynamic secret key generation
   - Token validation and extraction

### 2. **Password Security**
   - BCrypt hashing with strength 12
   - Passwords never stored in plain text
   - Secure password encoding during registration

### 3. **Token Blacklisting**
   - Logout functionality blacklists tokens
   - Prevents use of logged-out tokens
   - Memory-efficient token management

### 4. **Role-Based Authorization**
   - Endpoint-level role protection
   - Spring Security role-based access control
   - Automatic authority mapping from user roles

### 5. **Stateless Authentication**
   - No server-side session storage
   - JWT tokens carry authentication information
   - Scalable architecture

## 📡 API Endpoints

### Public Endpoints (No Authentication Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/api/home` | Home endpoint |
| POST | `/v1/api/home/register` | User registration |
| POST | `/v1/api/home/login` | User login (returns JWT token) |

### Protected Endpoints (Authentication Required)

#### Principal Endpoints (ROLE_PRINCIPAL)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/api/principal` | Principal dashboard |
| GET | `/v1/api/principal/**` | All principal resources |

#### Teacher Endpoints (ROLE_TEACHER)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/api/teacher` | Teacher dashboard |
| GET | `/v1/api/teacher/**` | All teacher resources |

#### Student Endpoints (ROLE_STUDENT)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/api/student` | Student dashboard |
| GET | `/v1/api/student/**` | All student resources |

### Logout Endpoint
| Method | Endpoint | Description | Headers Required |
|--------|----------|-------------|------------------|
| POST | `/v1/api/home/logout` | Logout user (blacklist token) | `Authorization: Bearer <token>` |

## 📁 Project Structure

```
Spring_Security6/
├── src/
│   ├── main/
│   │   ├── java/com/example/Spring_Security6/
│   │   │   ├── config/
│   │   │   │   ├── JwtFilter.java          # JWT authentication filter
│   │   │   │   └── SecurityConfig.java     # Security configuration
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java     # Public endpoints
│   │   │   │   ├── PrincipalController.java
│   │   │   │   ├── StudentController.java
│   │   │   │   └── TeacherController.java
│   │   │   ├── model/
│   │   │   │   ├── enums/
│   │   │   │   │   └── Roles.java          # User roles enum
│   │   │   │   ├── request/
│   │   │   │   │   └── LogoutRequest.java   # Logout request DTO
│   │   │   │   ├── response/
│   │   │   │   │   └── LoginResponse.java   # Login response DTO
│   │   │   │   ├── Users.java              # User entity
│   │   │   │   └── UserPrinciple.java      # UserDetails implementation
│   │   │   ├── repository/
│   │   │   │   └── UserRepo.java           # User repository
│   │   │   ├── service/
│   │   │   │   ├── JwtService.java         # JWT operations
│   │   │   │   ├── MyUserDetailService.java # UserDetailsService
│   │   │   │   └── UserService.java        # User business logic
│   │   │   └── SpringSecurity6Application.java
│   │   └── resources/
│   │       └── application.yaml            # Configuration
│   └── test/
└── pom.xml
```

## 🚀 Setup Instructions

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd Spring_Security6
```

### Step 2: Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE springsecurity;
```

2. Update database credentials in `application.yaml` if needed:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springsecurity
    username: root
    password: root
```

### Step 3: Build the Project

```bash
mvn clean install
```

### Step 4: Run the Application

```bash
mvn spring-boot:run
```

Or run the main class `SpringSecurity6Application` from your IDE.

The application will start on **http://localhost:9090**

## ⚙️ Configuration

### Application Configuration (`application.yaml`)

```yaml
spring:
  application:
    name: Spring_Security6
  
  # Database Configuration
  datasource:
    url: jdbc:mysql://localhost:3306/springsecurity
    username: [ Enter your username here ]
    password: [ Enter your password here ]
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  # JPA Configuration
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect
  
  # CORS Configuration
  web:
    cors:
      allowed-origins: "http://localhost:2025"
      allowed-methods: "GET,POST,PUT,DELETE,PATCH,OPTIONS"
      allowed-headers: "Authorization,Content-Type,X-Requested-With,Accept,Origin"
      allow-credentials: true
      max-age: 3600

server:
  port: 9090
```

### Security Configuration

- **CSRF**: Disabled (stateless API)
- **Session Management**: STATELESS
- **Password Encoder**: BCrypt (strength 12)
- **Token Expiration**: 10 minutes

## 💡 Usage Examples

### 1. Register a New User

**Request:**
```bash
POST http://localhost:9090/v1/api/home/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123",
  "email": "john@example.com",
  "role": "STUDENT"
}
```

**Response:**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "john_doe",
  "password": "$2a$12$...",
  "email": "john@example.com",
  "role": "STUDENT"
}
```

### 2. Login

**Request:**
```bash
POST http://localhost:9090/v1/api/home/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

**Response:**
```json
{
  "username": "john_doe",
  "password": "$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewY5GyY5Y5Y5Y5Y",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTYzODk2NzIwMCwiZXhwIjoxNjM4OTY3ODAwfQ..."
}
```

### 3. Access Protected Endpoint (Student)

**Request:**
```bash
GET http://localhost:9090/v1/api/student
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```
student
```

### 4. Access Protected Endpoint (Teacher)

**Request:**
```bash
GET http://localhost:9090/v1/api/teacher
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```
teacher controller
```

### 5. Access Protected Endpoint (Principal)

**Request:**
```bash
GET http://localhost:9090/v1/api/principal
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response:**
```
principal controller
```

### 6. Logout

**Request:**
```bash
POST http://localhost:9090/v1/api/home/logout
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTYzODk2NzIwMCwiZXhwIjoxNjM4OTY3ODAwfQ...
```

**Response:**
```
Logout successful
```

**Or if logout fails:**
```
Logout not successful
```

## 🔄 Authentication Flow

```
1. User Registration
   └─> POST /v1/api/home/register
       └─> Password encrypted with BCrypt
       └─> User saved to database

2. User Login
   └─> POST /v1/api/home/login
       └─> AuthenticationManager validates credentials
       └─> JwtService generates JWT token
       └─> User details retrieved from database
       └─> LoginResponse DTO returned with username, password (hashed), and token

3. Accessing Protected Resource
   └─> Client sends request with JWT in Authorization header
       └─> JwtFilter intercepts request
           └─> Extracts token from header
           └─> Validates token (not expired, not blacklisted)
           └─> Loads user details from database
           └─> Sets SecurityContext with authentication
       └─> SecurityConfig checks role authorization
       └─> Request proceeds to controller if authorized

4. User Logout
   └─> POST /v1/api/home/logout
       └─> Token extracted from Authorization header
       └─> Token added to blacklist
       └─> Returns "Logout successful" or "Logout not successful"
       └─> Token cannot be used for future requests
```

## 🗄 Database Schema

### Users Table

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | VARCHAR(36) | PRIMARY KEY, UUID | Unique user identifier |
| username | VARCHAR | NOT NULL, UNIQUE | User login name |
| password | VARCHAR | NOT NULL | BCrypt hashed password |
| email | VARCHAR | NULLABLE | User email address |
| role | ENUM | NOT NULL | User role (PRINCIPAL, TEACHER, STUDENT) |

**Example Data:**
```sql
INSERT INTO users (id, username, password, email, role) 
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'admin', '$2a$12$...', 'admin@school.com', 'PRINCIPAL');
```

## 🔧 Key Components

### 1. **JwtFilter**
- Extends `OncePerRequestFilter`
- Intercepts all HTTP requests
- Extracts and validates JWT tokens
- Sets Spring Security context
- Checks token blacklist

### 2. **SecurityConfig**
- Configures Spring Security filter chain
- Defines endpoint authorization rules
- Sets up authentication provider
- Configures stateless session management

### 3. **JwtService**
- Generates JWT tokens
- Validates token signatures
- Extracts claims from tokens
- Manages token blacklist
- Handles token expiration

### 4. **UserService**
- Handles user registration
- Manages user authentication
- Implements logout functionality
- Password encoding

### 5. **MyUserDetailService**
- Implements `UserDetailsService`
- Loads user details from database
- Converts `Users` entity to `UserDetails`
- Throws `UsernameNotFoundException` for invalid users

### 6. **UserPrinciple**
- Implements `UserDetails`
- Wraps `Users` entity
- Provides authorities based on user role
- Implements account status methods

## 🔐 Security Best Practices Implemented

1. ✅ **Password Hashing**: BCrypt with strength 12
2. ✅ **JWT Token Expiration**: 10-minute validity
3. ✅ **Token Blacklisting**: Secure logout
4. ✅ **Stateless Authentication**: No session storage
5. ✅ **Role-Based Access Control**: Endpoint-level protection
6. ✅ **CSRF Protection**: Disabled for stateless API
7. ✅ **CORS Configuration**: Controlled cross-origin access

## 📝 Notes

- **Token Expiration**: JWT tokens expire after 10 minutes. Users need to re-login after expiration.
- **Default Role**: If no role is specified during registration, the default role is `STUDENT`.
- **Secret Key**: A new secret key is generated on each application startup. In production, use a fixed secret key stored securely.
- **Token Blacklist**: Currently stored in memory. For production, consider using Redis or a database for distributed systems.


## 📄 License

This project is open source and available for educational purposes.

---

