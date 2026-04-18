# 🏦 Bank Management System

> A production-grade Spring Boot 3 REST API built for secure banking operations, featuring JWT Authentication, MySQL persistence via Spring Data JPA, and interactive OpenAPI documentation.

![Java CI](https://github.com/aryanagr-9114/Bank-Management-System/actions/workflows/ci.yml/badge.svg)
![License](https://img.shields.io/badge/license-MIT-green)

---

## 🚧 Status

| Version | Status |
|---|---|
| v2.0 — Spring Boot REST API (current `main` branch) | ✅ Working |
| v1.0 — Legacy Console App (archived) | ✅ Working |

---

## ✨ Features

### 👤 Customer
- Register & Login (password hashed with BCrypt)
- Create Savings or Current account
- View all personal accounts
- Delete account
- Logout

### 🛡️ Admin
- Login
- View all customer accounts
- Delete any account

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Database | H2 (Dev) / MySQL 8 (Prod) |
| Connectivity | Spring Data JPA / Hibernate |
| Security | Spring Security + JWT Tokens |
| Build | Maven Wrapper (`mvnw`) |
| Architecture | Controller-Service-Repository (REST Architecture) |

---

## 📁 Project Structure

```
Bank-Management-System/
├── banking-api/                          # Spring Boot 3 REST API (v2.0)
│   ├── src/
│   │   ├── main/java/com/aryan/demo/
│   │   │   ├── config/                   # OpenApiConfig (Swagger setup)
│   │   │   ├── controller/               # AuthController, AccountController, TransactionController
│   │   │   ├── dto/                      # Request/Response DTOs
│   │   │   ├── entity/                   # User, Account, Transaction (JPA Entities)
│   │   │   ├── exception/                # GlobalExceptionHandler
│   │   │   ├── repository/               # Spring Data JPA Repositories
│   │   │   ├── security/                 # JwtService, JwtAuthFilter, SecurityConfig
│   │   │   ├── service/                  # AccountService, TransactionService
│   │   │   └── DemoApplication.java
│   │   ├── resources/
│   │   │   └── application.yml           # Spring Boot configuration
│   │   └── test/
│   ├── Dockerfile                        # Docker build instructions
│   ├── docker-compose.yml                # Orchestrates MySQL + Spring Boot containers
│   └── pom.xml
├── src/                                  # Legacy Console App (v1.0 — archived)
│   ├── BankManagementSystem.java
│   ├── database/
│   ├── models/
│   ├── services/
│   ├── ui/
│   └── utils/
├── .github/workflows/ci.yml              # GitHub Actions CI
├── schema.sql                            # Legacy MySQL schema
├── config.properties.example            # Legacy DB config template
└── pom.xml                               # Root Maven file
```

---

## 🗄️ Database Schema

3 tables defined in `schema.sql`:

```sql
users       → user_id, username, password (VARCHAR 255), role (admin/customer)
accounts    → account_id, user_id (FK), account_type, balance, account_number
transaction → transaction_id, account_number (FK), type (deposit/withdrawal/transfer), amount, date
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven (Embedded wrapper provided)

### Setup & Run

```bash
# 1. Clone the repo
git clone https://github.com/aryanagr-9114/Bank-Management-System.git
cd Bank-Management-System/banking-api

# 2. Build the runnable JAR
./mvnw clean package -DskipTests

# 3. Run the Spring Boot Server
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

The application will launch on `http://localhost:8080`.
You can access the **Interactive API Documentation** by navigating to:
👉 `http://localhost:8080/swagger-ui.html`

---

## 🧠 Application Flow

### Customer
```
1 → Register    2 → Login    3 → Create Account
4 → View Accounts             5 → Delete Account
9 → Logout
```

### Admin
```
1 → Login    2 → View All Accounts    3 → Delete Account    9 → Logout
```

---

## 🧪 Running Tests

```bash
mvn test
```

---

## 📍 Roadmap

- [x] Core banking operations (Register, Login, Account CRUD)
- [x] MySQL-backed persistence via JDBC
- [x] Maven build system with runnable JAR
- [x] BCrypt password hashing *(Day 3)*
- [x] JUnit 5 unit tests *(Day 4)*
- [x] GitHub Actions CI *(Day 5)*
- [x] Spring Boot 3 REST API migration *(Week 2)*
- [x] JWT Authentication
- [x] Account endpoints (CRUD) with global error handling
- [x] Transaction endpoints (Deposit, Withdraw, Transfer) with `@Transactional`
- [x] Swagger/OpenAPI interactive documentation
- [x] Docker + docker-compose deployment (`Dockerfile` & `docker-compose.yml`)

---

## 📄 License

MIT — see [LICENSE](LICENSE)

---

## 👤 Author

**Aryan Agrawal**
- GitHub: [@aryanagr-9114](https://github.com/aryanagr-9114)
- Email: agarwalaryan9114@gmail.com
