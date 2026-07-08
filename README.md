# Student Management API

A secure REST API built with Spring Boot, Spring Security (JWT + Refresh Tokens), Spring Data JPA and MySQL. Supports role-based access control — admins manage student records while students can only view their own data.

## Tech Stack

- Java 24
- Spring Boot 3.4.x
- Spring Security + JWT (jjwt 0.12.x)
- Spring Data JPA + Hibernate
- MySQL
- Lombok
- Bean Validation (Jakarta Validation)

## Features

- JWT-based authentication with refresh token support
- Access token expires in 24 hours; refresh token (7 days) silently issues new JWTs
- Refresh tokens stored in MySQL — supports forced logout/revocation
- Role-based authorization — `ROLE_ADMIN` and `ROLE_STUDENT`
- Admin registers via `/api/auth/register` (assigned `ROLE_ADMIN`)
- Admin creates student accounts via `POST /api/students` (atomically creates `User` + `Student` linked via OneToOne)
- Students restricted to their own record; admins have full access
- Pagination and sorting on student listing
- Custom JPQL search by student name (case-insensitive, partial match)
- Centralized exception handling with structured JSON error responses
- Request validation with field-level error messages
- DTO-based request/response contracts — entities never exposed directly
- Standardized `ApiResponse<T>` wrapper on all responses

## Auth Endpoints

| Method | URL | Access | Description |
|--------|-----|--------|-------------|
| POST | /api/auth/register | Public | Register a new admin account |
| POST | /api/auth/login | Public | Login — returns JWT + refresh token |
| POST | /api/auth/refresh | Public | Exchange refresh token for new JWT |

## Student Endpoints

| Method | URL | Access | Description |
|--------|-----|--------|-------------|
| GET | /api/students | Admin only | Get paginated list of all students |
| GET | /api/students/search?name= | Admin only | Search students by name |
| GET | /api/students/{id} | Admin or owning student | Get student by ID |
| POST | /api/students | Admin only | Create student + linked user account |
| PUT | /api/students/{id} | Admin only | Update student details |
| DELETE | /api/students/{id} | Admin only | Delete a student |

## Token Flow

```
POST /api/auth/login
→ returns { accessToken, refreshToken }

Every protected request:
Authorization: Bearer <accessToken>

When accessToken expires (24h):
POST /api/auth/refresh  { "refreshToken": "uuid" }
→ returns { accessToken (new), refreshToken }
```

## Example Requests

**Login:**
```json
POST /api/auth/login
{
    "username": "admin",
    "password": "admin123"
}
```
```json
{
    "success": true,
    "message": "Login Successfully",
    "data": {
        "accessToken": "eyJhbGci...",
        "refreshToken": "550e8400-e29b-41d4-a716-446655440000"
    }
}
```

**Create a student (Admin only):**
```json
POST /api/students
Authorization: Bearer <accessToken>

{
    "name": "Rishi Kumar",
    "email": "rishi@example.com",
    "age": 20,
    "branch": "CSE",
    "username": "rishi066",
    "password": "securepass123"
}
```

## Architecture

```
HTTP Request
     ↓
JwtFilter (validates access token, sets SecurityContext)
     ↓
Controller (@PreAuthorize role checks)
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
3. Create `src/main/resources/application.properties` (not committed — see `.gitignore`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
security.jwt.secret-key=your-256-bit-secret
security.jwt.expiration=86400000
server.port=8080
```
4. Run `./mvnw spring-boot:run`
5. API available at `http://localhost:8080`

## Roadmap

- Unit and integration tests (JUnit + Mockito)
- Dockerized deployment
