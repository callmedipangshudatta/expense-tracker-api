# ZenFlow — Deterministic UPI Expense Tracker & Parsing Engine

> A production-oriented Spring Boot backend for expense management, deterministic UPI/SMS transaction parsing, validation, and PostgreSQL persistence.

**Status:** 🚀 Completed · Dockerized  
**Project Type:** Backend REST API · Production-Style Application

**GitHub:** https://github.com/callmedipangshudatta/expense-tracker-api

---

## Overview

ZenFlow is a backend application for managing and processing personal expense transactions.

The current implementation is intentionally **deterministic and backend-driven**. UPI/SMS transaction data is parsed using rule-based logic and regular expressions, validated using Jakarta Bean Validation, and persisted through Spring Data JPA into PostgreSQL.

The architecture is designed to provide a reliable foundation for future AI-assisted transaction processing without making the current system dependent on an LLM.

### Core Capabilities

- RESTful expense management
- Deterministic UPI/SMS transaction parsing
- Rule-based transaction categorization
- PostgreSQL persistence
- Spring Data JPA / Hibernate
- DTO-based request handling
- Jakarta Bean Validation
- Pagination
- Centralized exception handling
- RFC 7807 Problem Details
- Expense analytics
- Automated testing
- Swagger / OpenAPI documentation
- Docker / Docker Compose containerization
- Git / GitHub version control

---

## Architecture

ZenFlow follows a layered modular-monolith architecture with clear separation of API handling, business logic, persistence, and database operations.

### Request Flow

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
| Controller | REST endpoints and HTTP request handling |
| Service | Business logic, transaction processing, and validation flow |
| Repository | Database access through Spring Data JPA |
| Entity | Persistent domain models |
| DTO | API request and response models |
| Exception | Centralized exception and error handling |
| Parser | Deterministic UPI/SMS transaction extraction |

### Cross-Cutting Components

| Component | Responsibility |
|---|---|
| Validation | Jakarta Bean Validation |
| Error Handling | RFC 7807 Problem Details |
| Persistence | PostgreSQL + Hibernate |
| Documentation | Swagger / OpenAPI |
| Containerization | Docker + Docker Compose |
| Testing | Unit / integration testing |

---

## Transaction Parsing Engine

The current transaction parsing pipeline does **not depend on an LLM**.

ZenFlow uses deterministic rules and regular expressions to extract structured information from UPI/SMS transaction text.

### Processing Flow

```text
UPI / SMS Transaction
        ↓
Rule-Based Parser
        ↓
Extract Transaction Data
        ↓
DTO / Bean Validation
        ↓
Business Logic
        ↓
PostgreSQL Persistence
```

This approach provides predictable behavior and keeps transaction processing under direct application control.

### Why Deterministic Parsing?

For structured transaction messages, rule-based parsing provides:

- Predictable output
- Low processing overhead
- No external AI dependency
- Easier testing and debugging
- Reproducible results
- Direct control over parsing rules

The parser can be extended as new UPI/SMS formats are identified.

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

---

## API Endpoints

### Expense Management

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/expenses` | Retrieve paginated expenses |
| `GET` | `/expenses/{id}` | Retrieve an expense by ID |
| `POST` | `/expenses` | Create a new expense |
| `PUT` | `/expenses/{id}` | Update an existing expense |
| `DELETE` | `/expenses/{id}` | Delete an expense |

### Parsing & Analytics

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/expenses/parse` | Parse UPI / SMS transaction text |
| `GET` | `/expenses/analytics` | Retrieve spending analytics |

---

## Example Request

### Create an Expense

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

### Retrieve Expenses

```http
GET /expenses?page=0&size=10
```

Pagination allows clients to retrieve expense records in controlled result sets.

---

## Run Locally with Docker

ZenFlow is fully containerized using Docker and Docker Compose.

The local environment runs the Spring Boot application together with PostgreSQL.

### Prerequisites

- Git
- Docker Desktop

### Clone the Repository

```bash
git clone https://github.com/callmedipangshudatta/expense-tracker-api.git
cd expense-tracker-api
```

### Start the Application

```bash
docker-compose up -d
```

The Spring Boot application and PostgreSQL database will start through Docker Compose.

### API

```text
http://localhost:8080
```

Example:

```text
http://localhost:8080/expenses
```

---

## Stop the Application

```bash
docker-compose down
```

---

## Reset the Database

To remove the PostgreSQL Docker volume and start with a clean database:

```bash
docker-compose down -v
```

> **Warning:** Removing the volume deletes the locally persisted database data.

---

## API Documentation

ZenFlow uses Swagger / OpenAPI for API documentation.

When running locally, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides an interactive interface for exploring and testing the REST API.

---

## Testing

The project includes automated tests covering core backend behavior.

Testing includes areas such as:

- Controller behavior
- Service-layer business logic
- Repository interactions
- DTO validation
- Exception handling
- Expense operations
- Transaction parsing

### Run Tests

#### Linux / macOS

```bash
./mvnw test
```

#### Windows

```bash
mvnw.cmd test
```

---

## Data Integrity

Financial transaction data is processed and persisted under backend control.

### DTO Validation

Incoming requests are validated using Jakarta Bean Validation before reaching the application's business logic.

### Centralized Error Handling

Application exceptions are handled centrally and returned using RFC 7807 Problem Details.

This provides API consumers with a consistent error-response structure.

### Database Persistence

Expense data is persisted using Spring Data JPA / Hibernate with PostgreSQL as the underlying relational database.

### Backend as the Source of Truth

Transaction records and financial calculations are controlled by application logic and database operations rather than external AI-generated results.

---

## Engineering Principles

### 1. Deterministic First

When transaction information can be reliably extracted using application rules or regular expressions, ZenFlow processes it deterministically.

This keeps the current system predictable and testable.

### 2. Backend as the Source of Truth

Critical transaction data, validation, business rules, and persistence remain under backend control.

### 3. Validate Before Persisting

Incoming data passes through validation before it reaches the persistence layer.

### 4. Fail Explicitly

Invalid or incomplete transaction data should be rejected or handled explicitly rather than silently creating incorrect financial records.

### 5. Separation of Concerns

Controllers, services, repositories, DTOs, entities, and exception handling have distinct responsibilities.

This keeps the codebase maintainable and makes individual components easier to test and extend.

---

## Analytics

ZenFlow provides backend analytics for processing stored expense data.

Current capabilities include:

- Weekly spending aggregation
- Category-based expense analysis
- Expense totals
- Spending trends
- Anomaly detection

Financial calculations are performed by backend application logic and database data.

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

## Security & Configuration

ZenFlow follows basic backend security and data-integrity practices.

Current considerations include:

- Request validation
- Database constraints
- Centralized exception handling
- Environment-based configuration
- Separation of application layers
- Controlled database persistence

Sensitive credentials such as database passwords and API keys should be provided through environment variables and should never be committed to Git.

---

## Future Roadmap

The current implementation is intentionally deterministic. The architecture provides a foundation for adding AI capabilities without making the core transaction-processing system dependent on them.

### AI-Assisted Transaction Processing

Potential future integration:

```text
UPI / SMS Transaction
        ↓
Deterministic Parser
        ↓
   Confident?
    ↙     ↘
  Yes      No
   ↓        ↓
Validate   Spring AI
   ↓        ↓
Persist   Validate
             ↓
          Persist / Review
```

Potential AI capabilities include:

- Spring AI integration
- LLM fallback for ambiguous transaction formats
- Natural-language transaction interpretation
- AI-assisted transaction categorization
- Natural-language spending summaries
- AI-powered financial insights

### Backend Improvements

Future backend improvements may include:

- [ ] Authentication and authorization
- [ ] JWT / OAuth2
- [ ] Role-based access control
- [ ] Advanced UPI parsing rules
- [ ] Additional transaction formats
- [ ] API rate limiting
- [ ] Redis caching
- [ ] Production monitoring
- [ ] Prometheus / Grafana
- [ ] Automated deployment
- [ ] Frontend dashboard

---

## Project Status

**Completed:** Core backend implementation

ZenFlow currently combines:

**Java · Spring Boot · REST API · PostgreSQL · JPA/Hibernate · Jakarta Validation · Docker · Testing · Deterministic Parsing**

The current release focuses on building a reliable backend foundation first, with AI capabilities intentionally reserved for future iterations.

---

## Author

**Dipangshu V Datta**

Computer Science Engineer

**Focus:** Java · Spring Boot · Backend Development · REST APIs · PostgreSQL · Applied AI

**GitHub:** https://github.com/callmedipangshudatta/expense-tracker-api

---

> **Build it. Validate it. Test it. Containerize it. Extend it.**
