# ZenFlow — AI-Powered Personal Expense Tracker API

> A secure, multi-user REST API built with Java and Spring Boot to manage expenses, parse transactions, and generate intelligent spending insights.

**Status:** ✅ MVP Complete (Phase 13)
**Project Type:** Production-style Backend Application

---

## 🚀 What is ZenFlow?

ZenFlow is a backend application designed to reduce the friction of manually recording and understanding everyday digital expenses. It combines traditional REST backend engineering with applied AI concepts.

- Expense management through REST APIs
- PostgreSQL persistence with Spring Data JPA/Hibernate
- DTO-based validation and pagination
- RFC 7807 standardized error responses
- Automated unit and integration testing
- Full-stack Dockerization 
- Deterministic parsing with LLM fallback concepts
- Ready for frontend ML integrations

---

## 🏗️ Architecture

ZenFlow follows a layered modular-monolith architecture containerized via Docker.

```text
Client (Frontend / Postman)
  │
  ▼
Controller (REST Endpoints)
  │
  ▼
Service (Business Logic & Validation)
  │
  ▼
Repository (Spring Data JPA)
  │
  ▼
PostgreSQL (Docker Container)
