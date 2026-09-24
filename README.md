# ZenFlow — High-Throughput UPI Expense Tracker & Parsing Engine

> A production-style Spring Boot REST API built to ingest UPI/SMS transactions, deterministically parse and categorize financial data, calculate spending analytics, and provide a validated data foundation for future AI pipelines.

**Status:** 🚀 Completed · Dockerized  
**Project Type:** Backend REST API · Production-Style Application

**GitHub:** https://github.com/callmedipangshudatta/expense-tracker-api

---

## Overview

ZenFlow is a backend application for managing, parsing, validating, and analyzing personal expense transactions.

The current implementation is intentionally **deterministic and backend-driven**. Raw UPI/SMS transaction text is processed through a rule-based and Regex parsing engine, validated through Jakarta Bean Validation, processed through the service layer, and persisted using Spring Data JPA with PostgreSQL.

The system is designed as a **layered modular monolith**, providing clear separation between API handling, business logic, validation, persistence, and database operations.

The architecture also provides a reliable foundation for future AI-assisted processing without making the current production codebase dependent on an LLM.

---

## Core Features

- RESTful expense management
- Deterministic UPI/SMS transaction parsing
- Regex-based transaction extraction
- Rule-based transaction categorization
- PostgreSQL persistence
- Spring Data JPA / Hibernate
- DTO-based request handling
- Jakarta Bean Validation
- Pagination
- Centralized exception handling
- RFC 7807 Problem Details
- Custom JPQL aggregation queries
- Spending analytics
- Automated testing
- Docker / Docker Compose orchestration
- Git / GitHub version control

---

## Architecture

ZenFlow follows a layered modular-monolith architecture.

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
| Service | Business logic and transaction processing |
| Repository | Database access through Spring Data JPA |
| Entity | Persistent domain models |
| DTO | Validated API request / response models |
| Exception | Centralized error handling |
| Parser | Deterministic UPI/SMS transaction extraction |

### Cross-Cutting Components

| Component | Implementation |
|---|---|
| Validation | Jakarta Bean Validation |
| Error Handling | RFC 7807 Problem Details |
| Persistence | PostgreSQL + Hibernate |
| Analytics | Custom JPQL aggregation |
| Containerization | Docker + Docker Compose |
| Testing | Automated unit / integration testing |

---

## Transaction Parsing Engine

The current parsing engine is **fully deterministic** and does not depend on an LLM.

ZenFlow processes structured UPI/SMS transaction messages using predefined rules and regular expressions to extract relevant financial information.

### Processing Flow

```text
UPI / SMS Transaction
        ↓
Deterministic Rule / Regex Parser
        ↓
Extract Structured Data
        ↓
DTO / Bean Validation
        ↓
Service Layer
        ↓
PostgreSQL Persistence
```

The deterministic approach keeps the current transaction-processing pipeline predictable, testable, and independent of external AI services.

### Parsing Characteristics

- Regex-based extraction
- Rule-based categorization
- Predictable output
- Low processing overhead
- No external AI dependency
- Reproducible results
- Easy to test and debug
- Extensible for additional transaction formats

The parser is designed to provide a reliable foundation that can later be extended with AI-assisted processing for ambiguous transaction formats.

---

## Data Validation & Integrity

Financial transaction data requires strict validation before persistence.

ZenFlow uses DTOs and Jakarta Bean Validation to validate incoming requests before they reach the core business logic.

### Validation Includes

- Positive transaction amounts
- Required fields
- Valid field formats
- Controlled category values
- Request-level constraints

This prevents malformed or invalid data from being blindly persisted.

---

## Global Exception Handling

ZenFlow uses centralized exception handling with **RFC 7807 Problem Details**.

Instead of exposing inconsistent internal server errors, application exceptions are translated into structured responses suitable for API consumers.

This provides a consistent error contract across the REST API.

---

## Analytics Engine

ZenFlow includes a backend analytics layer for transforming persisted expense data into structured financial information.

Analytics are calculated by the backend using database data and custom JPQL aggregation queries rather than external AI-generated calculations.

### Current Analytics

- Weekly spending aggregation
- Category-based spending analysis
- Expense totals
- Spending trends
- Anomaly detection

The analytics layer is designed to keep financial calculations deterministic and reproducible.

---

## Tech Stack

| Area | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Database | PostgreSQL 15 |
| Persistence | Spring Data JPA / Hibernate |
| Validation | Jakarta Bean Validation |
| Querying | JPQL |
| Error Handling | RFC 7807 / ProblemDetail |
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

Pagination allows clients to retrieve expense records in controlled result sets rather than loading the entire dataset at once.

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

Docker Compose starts the Spring Boot application and PostgreSQL database.

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

## Testing

The project includes automated tests covering core backend behavior.

Testing includes:

- Controller behavior
- Service-layer business logic
- Repository interactions
- DTO validation
- Exception handling
- Expense CRUD operations
- Transaction parsing
- Analytics behavior

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

## Engineering Principles

### 1. Deterministic First

When transaction information can be reliably extracted using predefined rules or regular expressions, ZenFlow processes it deterministically.

This provides predictable behavior, low processing overhead, reproducible results, and straightforward testing.

### 2. Backend as the Source of Truth

Critical transaction data, financial calculations, validation rules, and persistence remain under backend control.

### 3. Validate Before Persisting

Incoming data must pass DTO and Bean Validation before reaching the persistence layer.

### 4. Fail Explicitly

Invalid or incomplete transaction data should be rejected or handled explicitly rather than silently creating incorrect financial records.

### 5. Separation of Concerns

Controllers, services, repositories, DTOs, entities, parsers, and exception handling have distinct responsibilities.

This keeps the codebase maintainable and makes individual components easier to test and extend.

---

## Security & Configuration

ZenFlow follows backend data-integrity and configuration practices including:

- Request validation
- Database constraints
- Centralized exception handling
- Environment-based configuration
- Separation of application layers
- Controlled database persistence

Sensitive credentials such as database passwords and API keys should be provided through environment variables and should never be committed to Git.

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

# Future Roadmap

The current implementation is intentionally deterministic.

The architecture provides a stable backend foundation that can be extended with AI capabilities without making the core transaction-processing pipeline dependent on an LLM.

## AI-Assisted Transaction Processing

Future architecture:

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

### Planned AI Capabilities

- Spring AI integration
- LLM fallback for ambiguous transaction formats
- Natural-language transaction interpretation
- AI-assisted transaction categorization
- Natural-language spending summaries
- AI-powered financial insights

### Future Backend Improvements

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

**Java · Spring Boot · REST API · PostgreSQL · JPA/Hibernate · Jakarta Validation · JPQL · Docker · Testing · Deterministic Parsing**

The current release focuses on building a reliable backend and validated data foundation first, with AI capabilities intentionally reserved for future iterations.

---

## Author

**Dipangshu V Datta**

Computer Science Engineer

**Focus:** Java · Spring Boot · Backend Development · REST APIs · PostgreSQL · Applied AI

**GitHub:** https://github.com/callmedipangshudatta/expense-tracker-api

---

> **Build it. Validate it. Test it. Containerize it. Extend it.**

---

# Development Progress

```text
PHASE 1 —
Spring Boot Foundation (Aug 23)
████████████████████ 100% [DONE]

PHASE 2 —
REST API Fundamentals (Aug 23)
████████████████████ 100% [DONE]

PHASE 3 —
Expense Entity & Model (Aug 23)
████████████████████ 100% [DONE]
· Configured the Spring Boot Project
· Mapped out REST API Design
· Built the core Expense entity with attributes like ID, Description, amount, category, and timestamps.

PHASE 4 —
PostgreSQL + JPA Config
████████████████████ 100% [DONE]

PHASE 5 —
Repository Layer
████████████████████ 100% [DONE]
· Established the connection to PostgreSQL using Spring Data JPA
· Built the "ExpenseRepository" interface to handle Direct database queries

PHASE 6 —
Service Layer & Business Logic (Aug 26)
████████████████████ 100% [DONE]
· Developed the "ExpenseService" class to house the Business logic
· Bridged HTTP Requests and Database operations

PHASE 7 —
CRUD Controller & End-to-End Testing
████████████████████ 100% [DONE]
· Integrated the controller with the service layer
· Successfully tested all CRUD operations via POSTMAN, and verified data persistence inside PostgreSQL through pgAdmin 4

Before PHASE 8 – "If someone sends a POST request to our API With an amount of -5000 or a blank description, our dB will save it Blindly. That’s danger"

After PHASE 8 – "To fix this, we are going to introduce DTOs (Data Transfer Objects) and Bean Validation. Think of a DTO as a strict bouncer at a club. It checks the incoming JSON data, verifies all the rules (e.g., 'amount must be positive', 'description cannot be empty'), and only lets the data into your database if it passes."

PHASE 8 —
DTOs, Bean Validation & Pagination
████████████████████ 100% [DONE]

PHASE 9 —
Global Exception Handling (RFC 7807)
████████████████████ 100% [DONE]
· Implemented DTOs and Bean Validation to protect your database from bad data.
· Added Pagination so your app can handle millions of records without crashing.
· Built a Global Exception Handler to translate ugly server errors into clean, frontend-friendly JSON responses.

PHASE 10 —
Deterministic UPI Parser & Auto-Categorizer
████████████████████ 100% [DONE]
· Implemented robust regex-based extraction for standard UPI and SMS transactions.
· Architected the parsing pipeline to securely support future AI/LLM fallback integrations.

PHASE 11 —
Spending Analytics & Aggregation Engine
████████████████████ 100% [DONE]
· Engineered custom JPQL queries to aggregate category-based spending.
· Built deterministic analytical endpoints to serve clean, reliable financial data.

PHASE 12 —
Testing, Verification & Deadline Execution
████████████████████ 100% [DONE]
· Verified all REST endpoints via comprehensive Postman execution.
· Strategically bypassed local Maven caching roadblocks to guarantee deployment deadlines.

PHASE 13 —
Dockerization, Production Polish & Final GitHub Release
████████████████████ 100% [DONE]
· Authored Dockerfile and docker-compose.yml for zero-config database and API orchestration.
· Cleaned the codebase and successfully shipped the fully containerized architecture to GitHub.
```
