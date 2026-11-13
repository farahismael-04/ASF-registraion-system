# ASF Registration System

Backend service for the **ASF Registration System**, built using Spring Boot and Kotlin.

## Tech Stack

- **Language:** Kotlin
- **Framework:** Spring Boot
- **Build Tool:** Gradle
- **Database:** PostgreSQL
- **Testing Database:** H2
- **ORM:** Spring Data JPA / Hibernate
- **Authentication:** JWT (Bearer Token)
- **Password Hashing:** Argon2id

## Features

- User registration
- User login and JWT authentication
- Authenticated user information
- Logout and token revocation
- Security question management
- Password recovery
- Password reset

## Project Structure

```text
src/main/kotlin/com/reg/regestration_system/
├── config/
├── controllers/
├── dto/
├── exception/
├── models/
├── repository/
└── service/
```

## Getting Started

### Prerequisites

- JDK 21
- IntelliJ IDEA
- Git
- PostgreSQL (for the main database)

### 1. Clone the repository

```bash
git clone https://github.com/farahismael-04/ASF-registraion-system.git
cd ASF-registraion-system
```

### 2. Configure JWT Secret

Set an environment variable named `JWT_SECRET` with a random secret of at least 32 bytes.

Never commit secrets or database credentials to GitHub.

### 3. Run with H2 (Testing)

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=test"
```

Application URL:

`http://localhost:8080`

### 4. PostgreSQL Configuration

Configure your local PostgreSQL database connection before running with the main database.

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register a user |
| POST | `/api/auth/login` | Login and generate JWT |
| POST | `/api/auth/logout` | Logout |
| GET | `/api/auth/me` | Get current user |
| GET | `/api/security-questions` | Get all security questions |
| GET | `/api/auth/recovery-questions/{username}` | Get user's recovery questions |
| POST | `/api/auth/forgot-password` | Reset password |

Protected endpoints require a Bearer JWT token in the Authorization header.

## Team Development Guidelines

- Create a separate feature branch for each task.
- Avoid pushing directly to `main`.
- Use meaningful commit messages.
- Submit Pull Requests before merging.
- Review code before approving Pull Requests.
- Never commit secrets or credentials.

### Branch Naming

```text
feature/authentication
feature/supplier-management
bugfix/login-validation
```

## Development Status

Authentication functionality has been implemented and tested using Postman with H2.

PostgreSQL integration and further development are ongoing.

---

**Project:** ASF Registration System  
**Repository:** https://github.com/farahismael-04/ASF-registraion-system
