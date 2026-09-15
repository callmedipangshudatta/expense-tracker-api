# ZenFlow — AI-Powered Personal Expense Tracker API

> A secure, multi-user REST API built with Java and Spring Boot to manage expenses, parse UPI transactions, and generate intelligent spending insights.

**Status:** 🚧 Actively Developed  
**Project Type:** Production-style Backend Application

**Live API:** `ADD_LIVE_URL_HERE` · **Swagger:** `ADD_SWAGGER_URL_HERE` · **GitHub:** `ADD_GITHUB_URL_HERE`

---

## 🚀 What is ZenFlow?

ZenFlow is a backend application designed to reduce the friction of manually recording and understanding everyday digital expenses.

It combines traditional backend engineering with applied AI:

- Expense management through REST APIs
- PostgreSQL persistence with JPA/Hibernate
- DTO-based validation and pagination
- RFC 7807 standardized error responses
- JWT authentication and user-level authorization
- UPI transaction parsing and auto-categorization
- Deterministic parsing with LLM fallback
- AI output validation before persistence
- Weekly spending analytics
- Deterministic anomaly detection
- AI-generated explanations of trusted financial data
- Automated unit and integration testing
- Dockerized development
- GitHub Actions CI
- Swagger/OpenAPI documentation
- Health checks and basic observability
- Cloud deployment

---

## 🏗️ Architecture

ZenFlow follows a layered modular-monolith architecture.

```text
Client
  │
  ▼
Controller
  │
  ▼
Service
  │
  ▼
Repository
  │
  ▼
PostgreSQL
```

Cross-cutting components:

```text
Security      → JWT + BCrypt + Authorization
Validation    → DTO + Bean Validation
Errors        → RFC 7807 Problem Details
AI            → Regex Parser + LLM Fallback
Analytics     → SQL/Java Aggregation + Anomaly Detection
Testing       → JUnit + Mockito + MockMvc + Testcontainers
Documentation → Swagger / OpenAPI
DevOps        → Docker + GitHub Actions
Observability → Actuator + Health + Logs + Metrics
```

---

## 🤖 AI Transaction Pipeline

AI is deliberately used as a **fallback**, not as the source of truth.

```text
UPI Text
   │
   ▼
Regex / Rules Parser
   │
   ├── SUCCESS ───────────────► Validate ──► Save
   │
   └── UNCERTAIN
          │
          ▼
         LLM
          │
      ┌───┴───┐
      ▼       ▼
   SUCCESS  FAILURE
      │       │
      ▼       ▼
  Validate  NEEDS_REVIEW
      │
      ▼
    Save
```

The LLM response is treated as **untrusted input**.

```text
LLM Response
     ↓
JSON Parsing
     ↓
DTO Validation
     ↓
Business Validation
     ↓
Allowed Category Check
     ↓
Trusted Data
     ↓
PostgreSQL
```

Invalid AI output is never silently persisted.

---

## 📊 Spending Analytics

Financial calculations remain deterministic and reproducible.

```text
PostgreSQL
    ↓
Expense Aggregation
    ↓
Java / SQL Statistics
    ↓
Anomaly Detection
    ↓
Trusted Metrics
    ↓
LLM Narration
    ↓
Human-readable Insight
```

The LLM explains verified numbers; it does not perform the application's critical arithmetic.

Example:

```text
Dining
This week: ₹4,200
Previous period: ₹3,300
Change: +27%

Groceries spending is significantly above its historical baseline.
```

---

## 🔐 Security

ZenFlow uses JWT-based authentication with BCrypt password hashing.

```text
Register
   ↓
BCrypt Password Hash
   ↓
Login
   ↓
JWT
   ↓
Bearer Token
   ↓
JWT Filter
   ↓
SecurityContext
   ↓
Authorization
```

Every expense belongs to a user.

```text
User A → Expense 1, Expense 2
User B → Expense 3, Expense 4
```

Authentication alone is not enough: read, update, and delete operations also verify **resource ownership**.

---

## 🧪 Testing

The test strategy focuses on both normal behavior and failure paths.

### Unit Testing

- UPI parser
- Business logic
- Analytics
- Anomaly detection

### Mockito

- Mock LLM responses
- No live AI calls in automated tests

### MockMvc

- REST endpoints
- Validation
- Error responses
- Authentication/authorization

### Testcontainers

- PostgreSQL integration testing

### AI Adversarial Testing

- Malformed JSON
- Missing fields
- Negative amounts
- Invalid categories
- Blank merchants
- Unexpected AI responses

Core principle:

> **AI output is untrusted input and must pass validation before reaching the database.**

---

## 🛠️ Tech Stack

| Area | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Database | PostgreSQL |
| Persistence | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| Error Handling | RFC 7807 |
| AI | Spring AI |
| Authentication | JWT + BCrypt |
| Documentation | Swagger / OpenAPI |
| Testing | JUnit + Mockito + MockMvc |
| Integration | Testcontainers |
| Containerization | Docker / Docker Compose |
| CI | GitHub Actions |
| Monitoring | Spring Boot Actuator |
| API Testing | Postman |

---

## 📡 API

### Authentication

```http
POST /auth/register
POST /auth/login
```

### Expenses

```http
GET    /expenses
GET    /expenses/{id}
POST   /expenses
PUT    /expenses/{id}
DELETE /expenses/{id}
```

### AI & Analytics

```http
GET /expenses/ai/weekly-summary
```

### Health

```http
GET /health
```

The complete API contract is available through Swagger/OpenAPI.

**Swagger UI:** `ADD_SWAGGER_URL_HERE`

---

## 🐳 Run Locally

### Prerequisites

- Java
- Maven
- Docker / Docker Compose
- PostgreSQL
- AI provider API key for AI features

### Environment Variables

```text
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
AI_API_KEY=
```

Never commit real credentials or API keys.

### Docker

```bash
docker compose up --build
```

### Maven

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

---

## ⚙️ CI/CD

GitHub Actions validates changes automatically:

```text
Push / Pull Request
        ↓
   Setup JDK
        ↓
    Run Tests
        ↓
 Build / Package
        ↓
Security / Dependency Check
```

No live AI key is required for the test suite because LLM behavior is mocked.

---

## 📡 Deployment & Observability

ZenFlow is designed to be publicly deployable with:

- Managed PostgreSQL
- Environment-based secrets
- Dockerized application
- Health checks
- Application logs
- Basic metrics
- Spring Boot Actuator

**Live API:** `ADD_LIVE_URL_HERE`

The definition of done is not simply "deployed":

> **A stranger should be able to access the application, register, authenticate, create an expense, use the AI functionality, and view analytics.**

---

## 🧠 Engineering Principles

### Deterministic before AI

Use reliable rules when a problem can be solved deterministically.

### AI as an assistant

LLM output is validated before becoming trusted application data.

### Backend as the source of truth

Critical financial calculations are performed by Java/SQL.

### Authorization beyond authentication

A logged-in user can access only resources they are authorized to access.

### Test failure paths

Production reliability depends heavily on handling invalid input, external-service failures, and authorization mistakes.

### Simple architecture

ZenFlow remains a modular monolith unless a real requirement justifies distributed architecture.

---

## 📈 Project Maturity

ZenFlow is designed to demonstrate the complete backend engineering lifecycle:

```text
Design
  ↓
Implementation
  ↓
Database
  ↓
Validation
  ↓
Security
  ↓
AI Integration
  ↓
Testing
  ↓
Docker
  ↓
CI
  ↓
Deployment
  ↓
Observability
```

The goal is not to maximize the number of technologies.

The goal is to build a backend system that is **secure, testable, explainable, reproducible, and actually usable**.

---

## 📜 License

This project is intended to use the **MIT License** if released as open source.

Third-party libraries, AI services, datasets, and external APIs may have separate licenses and terms.

---

## 👨‍💻 Author

**Dipangshu V Datta**

Java · Spring Boot · Backend Development · Applied AI

**GitHub:** `ADD_GITHUB_URL_HERE`  
**LinkedIn:** `ADD_LINKEDIN_URL_HERE`

---

### 🚀 ZenFlow

**Build it. Secure it. Test it. Deploy it. Prove it.**
