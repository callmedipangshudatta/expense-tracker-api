# ZenFlow — AI-Powered Personal Expense Tracker API

> A production-oriented REST API built with Java and Spring Boot for managing expenses, parsing UPI transactions, and generating spending insights.

**Status:** 🚀 Production-Style Backend · Dockerized  
**Project Type:** Backend REST API · Applied AI

**GitHub:** https://github.com/callmedipangshudatta/expense-tracker-api

---

## Overview

ZenFlow is a backend application for managing and analyzing personal expenses.

It combines a conventional Spring Boot REST API with PostgreSQL persistence, validation, analytics, and an AI-assisted transaction parsing pipeline.

The system follows a **deterministic-first approach**: transaction data is processed using rules and regular expressions first, with an LLM used only when deterministic parsing is uncertain.

AI is treated as an assisting layer, while validation, business logic, financial calculations, and persistence remain under backend control.

---

## Features

- RESTful expense management
- PostgreSQL persistence
- Spring Data JPA / Hibernate
- DTO-based request handling
- Jakarta Bean Validation
- Pagination
- Global exception handling
- RFC 7807 Problem Details
- UPI / SMS transaction parsing
- Automatic transaction categorization
- Rule-based parsing with LLM fallback
- AI output validation
- Weekly spending analytics
- Category-based expense analysis
- Anomaly detection
- Automated testing
- Swagger / OpenAPI
- Docker / Docker Compose
- GitHub Actions CI/CD readiness

---

## Architecture

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
PostgreSQL
```

### Main Layers

| Layer | Responsibility |
|---|---|
| Controller | REST endpoints and HTTP requests |
| Service | Business logic and transaction processing |
| Repository | Database access through Spring Data JPA |
| Entity | Persistent domain models |
| DTO | API request / response models |
| Exception | Centralized error handling |
| AI Parser | UPI/SMS parsing with LLM fallback |

---

## AI Transaction Pipeline

ZenFlow uses AI as a fallback rather than the primary source of truth.

```text
UPI / SMS Text
      ↓
Rule-Based Parser
      ↓
   Confident?
   ↙       ↘
 Yes        No
 ↓          ↓
Validate    LLM
 ↓          ↓
Save     Validate
            ↓
       Save / Review
```

### AI Safety

LLM output is treated as **untrusted input**.

AI-generated transaction data must pass application validation before being persisted.

Invalid or incomplete AI responses are not silently stored.

---

## Tech Stack

| Area | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Database | PostgreSQL 15 |
| Persistence | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| Error Handling | RFC 7807 / ProblemDetail |
| API Documentation | Swagger / OpenAPI |
| Containerization | Docker / Docker Compose |
| API Testing | Postman |
| Build Tool | Maven |
| Version Control | Git / GitHub |
| CI/CD | GitHub Actions |
| AI | LLM API |

---

## API Endpoints

### Expenses

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/expenses` | Retrieve paginated expenses |
| `GET` | `/expenses/{id}` | Retrieve an expense |
| `POST` | `/expenses` | Create an expense |
| `PUT` | `/expenses/{id}` | Update an expense |
| `DELETE` | `/expenses/{id}` | Delete an expense |

### AI & Analytics

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/expenses/parse` | Parse UPI / SMS transaction text |
| `GET` | `/expenses/analytics` | Retrieve spending analytics |

---

## Example Request

### Create Expense

```http
POST /expenses
Content-Type: application/json
```

```json
{
  "amount": 450.00,
  "category": "FOOD",
  "description": "Dinner",
  "date": "2026-09-24"
}
```

### Get Expenses

```http
GET /expenses?page=0&size=10
```

---

## Run Locally

### Prerequisites

- Git
- Docker Desktop

### Clone

```bash
git clone https://github.com/callmedipangshudatta/expense-tracker-api.git
cd expense-tracker-api
```

### Start

```bash
docker-compose up -d
```

The application and PostgreSQL database will start through Docker Compose.

API:

```text
http://localhost:8080
```

Example:

```text
http://localhost:8080/expenses
```

---

## Stop

```bash
docker-compose down
```

To remove the database volume and reset the database:

```bash
docker-compose down -v
```

> **Warning:** Removing the volume deletes the locally persisted database data.

---

## API Documentation

Swagger / OpenAPI is available when the application is running.

Typical local URL:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Testing

The project includes automated tests covering application behavior such as:

- Controller behavior
- Service-layer logic
- Repository interactions
- Validation
- Exception handling
- Expense operations
- UPI parsing
- AI fallback behavior

### Run Tests

Linux / macOS:

```bash
./mvnw test
```

Windows:

```bash
mvnw.cmd test
```

---

## Data Integrity

ZenFlow keeps financial data processing under backend control.

### Validation

Incoming requests are validated using Jakarta Bean Validation.

### Error Handling

Application exceptions are handled centrally using RFC 7807 Problem Details.

### Financial Calculations

Authoritative financial calculations are performed by application logic and database operations rather than by the LLM.

### AI Validation

LLM responses are validated before they can affect persisted application data.

---

## Engineering Principles

### Deterministic Before AI

Use rules and regular expressions when the transaction can be reliably processed without AI.

### AI as an Assistant

The LLM helps interpret uncertain transaction data but does not bypass application validation or business logic.

### Backend as the Source of Truth

Critical financial data and calculations remain controlled by the backend.

### Fail Safely

Uncertain or invalid transaction data should be rejected or flagged for review instead of being silently persisted.

---

## Analytics

Current analytics capabilities include:

- Weekly spending aggregation
- Category-based spending analysis
- Expense totals
- Spending trends
- Anomaly detection

---

## Project Structure

```text
expense-tracker-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/snehadipangshu/expense_tracker_api/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       ├── dto/
│   │   │       └── exception/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
└── .gitignore
```

---

## Security

The application follows basic backend security and data-integrity practices including:

- Request validation
- Database constraints
- Centralized exception handling
- AI output validation
- Environment-based configuration
- Separation of application layers

Sensitive credentials such as database passwords and API keys should be provided through environment variables and should never be committed to Git.

---

## Future Improvements

- [ ] Authentication and authorization
- [ ] JWT / OAuth2
- [ ] Role-based access control
- [ ] Advanced UPI parsing
- [ ] Multiple LLM providers
- [ ] Budget management
- [ ] Monthly financial reports
- [ ] Advanced anomaly detection
- [ ] Redis caching
- [ ] API rate limiting
- [ ] Production monitoring
- [ ] Prometheus / Grafana
- [ ] Automated deployment
- [ ] Frontend dashboard

---

## Project Status

**Production-Style Backend Development**

ZenFlow currently combines:

**Java · Spring Boot · REST API · PostgreSQL · JPA/Hibernate · Docker · Testing · Applied AI**

The project is being developed toward a complete personal finance platform with intelligent transaction processing and financial analytics.

---

## Author

**Dipangshu V Datta**

**Focus:** Java · Spring Boot · Backend Development · REST APIs · PostgreSQL · Applied AI

---

> **Build it. Secure it. Test it. Deploy it. Prove it.**
