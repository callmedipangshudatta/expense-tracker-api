# ZenFlow: Personal Expense Tracker REST API

**Status: Actively In Development**

## Overview
ZenFlow is a backend REST API designed to automate daily cash-flow analysis for high-frequency digital (UPI) spenders. The core objective is to eliminate the friction of manual financial logging by securely capturing and structuring transaction data.

## Technical Foundation
This API is engineered to act as a robust data persistence layer. It is structured to handle granular transaction categorization, laying the groundwork for future integrations with AI logic to generate personalized weekly reports and cost-reduction insights.

## System Architecture
The application follows a strict N-Tier (Layered) architecture to maintain a clear separation of concerns, ensuring secure and scalable data flow from the client down to the database.

```text
                  CLIENT
           (Postman / Frontend)
                    │
                    │ HTTP Request (JSON)
                    ↓
           ┌──────────────────────┐
           │      CONTROLLER      │
           │                      │
           │ GET /expenses        │
           │ POST /expenses       │
           │ PUT /expenses/1      │
           │ DELETE /expenses/1   │
           └──────────┬───────────┘
                      │
                      ↓
           ┌──────────────────────┐
           │       SERVICE        │
           │                      │
           │    Business Logic    │
           └──────────┬───────────┘
                      │
                      ↓
           ┌──────────────────────┐
           │      REPOSITORY      │
           │                      │
           │   Database Access    │
           └──────────┬───────────┘
                      │
                      ↓
             ┌──────────────────┐
             │    PostgreSQL    │
             │     Database     │
             └──────────────────┘
                      │
                      ↓
                HTTP Response
                      │
                      ↓
                   CLIENT
```
## Tech Stack
* Language: Java

* Framework: Spring Boot

* Database: PostgreSQL

* Data Access: Spring Data JPA / Hibernate

* API Testing: Postman

* Version Control: Git & GitHub

## Current Development State
* REST Controller routing and HTTP method mapping configured.

* PostgreSQL driver integrated and database architecture mapping in progress.

* Next Steps: Finalizing the Expense Entity blueprint and establishing the Service layer business logic.