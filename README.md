# 🏦 Bank Management System

> A console-based Java banking application backed by MySQL — **currently being upgraded to a production-grade Spring Boot REST API.**

![Java CI](https://github.com/aryanagr-9114/Bank-Management-System/actions/workflows/ci.yml/badge.svg)
![License](https://img.shields.io/badge/license-MIT-green)

---

## 🚧 Status

| Version | Status |
|---|---|
| v1.0 — Console App (current `main` branch) | ✅ Working |
| v2.0 — Spring Boot REST API (`dev` branch) | 🔄 In Progress |

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
| Language | Java 8 |
| Database | MySQL 8 |
| Connectivity | JDBC (`mysql-connector-java 8.1.0`) |
| Build | Apache Maven 3 + maven-shade-plugin |
| Architecture | Layered: `database` / `models` / `services` / `ui` / `utils` |

---

## 📁 Project Structure

```
Bank-Management-System/
├── banking-api/                       # Spring Boot 3 REST API (v2.0)
│   ├── src/main/java/com/aryan/demo/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── security/
│   └── pom.xml
├── src/                               # Legacy Console App (v1.0)
│   ├── BankManagementSystem.java
│   └── services/
├── pom.xml                            # Root Maven file
└── .github/workflows/ci.yml           # GitHub Actions
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
- Java 8+
- MySQL 8
- Maven 3

### Setup

```bash
# 1. Clone the repo
git clone https://github.com/aryanagr-9114/Bank-Management-System.git
cd Bank-Management-System

# 2. Create the database
mysql -u root -p < schema.sql

# 3. Configure DB credentials
cp config.properties.example config.properties
# Edit config.properties — set your MySQL host, username, password

# 4. Build the runnable JAR
mvn clean package

# 5. Run
java -jar target/banking-management-system-1.0.0.jar
```

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
- [ ] Docker + docker-compose deployment
- [ ] React dashboard frontend

---

## 📄 License

MIT — see [LICENSE](LICENSE)

---

## 👤 Author

**Aryan Agrawal**
- GitHub: [@aryanagr-9114](https://github.com/aryanagr-9114)
- Email: agarwalaryan9114@gmail.com
