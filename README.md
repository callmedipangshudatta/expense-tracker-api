# 💰 Spring Boot Expense Tracker REST API

A backend REST API built using Java, Spring Boot, Spring Data JPA, and MySQL.

## 🏗️ Architecture

```text
                    CLIENT
              (Postman / Frontend)
                       |
                       | HTTP Request
                       ↓
              ┌─────────────────┐
              │   CONTROLLER    │
              │                 │
              │ GET /expenses   │
              │ POST /expenses  │
              │ PUT /expenses/1 │
              │ DELETE /expenses/1
              └────────┬────────┘
                       |
                       ↓
              ┌─────────────────┐
              │     SERVICE     │
              │                 │
              │ Business Logic  │
              └────────┬────────┘
                       |
                       ↓
              ┌─────────────────┐
              │   REPOSITORY    │
              │                 │
              │ Database Access │
              └────────┬────────┘
                       |
                       ↓
                 ┌──────────┐
                 │  MySQL   │
                 │ Database │
                 └──────────┘
                       |
                       ↓
                  HTTP Response
                       |
                       ↓
                     CLIENT
