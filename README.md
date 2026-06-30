# Student Management API

A secure REST API built with Spring Boot, Spring Security (JWT), Spring Data JPA and MySQL. Supports role-based access control so admins manage student records while students can only view their own data.

## Tech Stack

- Java 24
- Spring Boot 3.4.x
- Spring Security + JWT (jjwt 0.12.x)
- Spring Data JPA + Hibernate
- MySQL
- Lombok
- Bean Validation (Jakarta Validation)

## Features

- JWT-based authentication (register/login)
- Role-based authorization — `ROLE_ADMIN` and `ROLE_STUDENT`
- One-to-one `User` ↔ `Student` relationship for resource-level access control
- Students can only access their own record; admins have full access
- Pagination and sorting on student listing
- Custom JPQL search by student name (case-insensitive, partial match)
- Centralized exception handling with structured JSON error responses
- Request validation with detailed field-level error messages
- DTO-based request/response contracts — entities never exposed directly
- Standardized `ApiResponse<T>` wrapper for all responses

## Auth Endpoints

| Method | URL | Access | Description |
|--------|-----|--------|-------------|
| POST | /api/auth/register | Public | Register a new admin/auth user |
| POST | /api/auth/login | Public | Login and receive a JWT token |

## Student Endpoints

| Method | URL | Access | Description |
|--------|-----|--------|-------------|
| GET | /api/students | Admin only | Get paginated list of students |
| GET | /api/students/search?name= | Admin only | Search students by name |
| GET | /api/students/{id} | Admin or owning student | Get student by ID |
| POST | /api/students | Admin only | Create a student (creates linked User account) |
| PUT | /api/students/{id} | Admin only | Update student details |
| DELETE | /api/students/{id} | Admin only | Delete a student |

## Example Request

**Create a student (Admin only):**
```json
POST /api/students
Authorization: Bearer <admin_jwt_token>

{
    "name": "Rishi Kumar",
    "email": "rishi@example.com",
    "age": 20,
    "branch": "CSE",
    "username": "rishi066",
    "password": "securepass123"
}
```

**Response:**
```json
{
    "success": true,
    "message": "Student Created Successfully",
    "data": {
        "name": "Rishi Kumar",
        "email": "rishi@example.com",
        "age": 20
    }
}
```

## Architecture

```
HTTP Request
     ↓
JwtFilter (validates token, sets SecurityContext)
     ↓
Controller (maps requests, enforces @PreAuthorize)
     ↓
Service (business logic, @Transactional)
     ↓
Repository (Spring Data JPA)
     ↓
MySQL
```

## Setup

1. Clone the repo
2. Create a MySQL database named `student_db`
3. Configure `src/main/resources/application.properties` with your DB credentials and JWT secret (not committed — see `.gitignore`)
4. Run `./mvnw spring-boot:run`
5. API available at `http://localhost:8080`

## Roadmap

- Refresh token support for seamless session renewal
- Unit and integration tests (JUnit + Mockito)
- Dockerized deployment
