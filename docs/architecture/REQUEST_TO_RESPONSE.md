# HTTP Request → Response Flow

This document explains how a request travels through the Expense Tracker API in a Spring Boot application and how the final response is returned to the client.

---

## 1. Client Sends an HTTP Request

A client such as **Postman** or a browser sends an HTTP request to the API.

### Example Request

```http
POST /expenses HTTP/1.1
Host: localhost:8080
Content-Type: application/json
```

### Request Body

```json
{
  "amount": 500,
  "category": "Food"
}
```

The client is essentially saying:

> "Create a new expense with an amount of 500 and category Food."

---

## 2. Spring Boot Receives the Request

The request reaches the embedded **Tomcat server** running inside the Spring Boot application.

Tomcat receives the HTTP request and passes it into the Spring MVC request-handling pipeline.

The request is then mapped to the appropriate controller method.

```text
POST /expenses
      ↓
Spring Boot
      ↓
Controller
```

---

## 3. Spring Calls the Controller Method

Spring identifies the controller method mapped to:

```java
@PostMapping("/expenses")
public Expense addExpense(@RequestBody Expense expense) {
    return expense;
}
```

The `@PostMapping("/expenses")` annotation tells Spring:

> "When a POST request arrives at `/expenses`, execute this method."

---

## 4. Application Processes the Request

The controller receives the request data and passes it into the application's processing logic.

Depending on the application architecture, the flow can continue through:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
MySQL
```

The application may then save the new expense in the database.

---

## 5. Data Is Returned

After processing the request, the application produces the created expense.

For example:

```json
{
  "id": 1,
  "amount": 500,
  "category": "Food"
}
```

---

## 6. Spring Creates the HTTP Response

Spring converts the Java object into JSON and sends an HTTP response back to the client.

### Example Response

```http
HTTP/1.1 201 Created
Content-Type: application/json
```

### Response Body

```json
{
  "id": 1,
  "amount": 500,
  "category": "Food"
}
```

The client then receives this response.

---

# Complete Request → Response Flow

The complete flow can be visualized as:

```text
                     CLIENT
                Browser / Postman
                       │
                       │
                       │ HTTP REQUEST
                       │
                       ▼
            ┌───────────────────────┐
            │      SPRING BOOT      │
            │                       │
            │        Tomcat         │
            │          ↓            │
            │      Controller       │
            └───────────┬───────────┘
                        │
                        │ Java Method
                        ▼
                     Service
                        │
                        ▼
                   Repository
                        │
                        ▼
                      MySQL
                        │
                        │ Data Returned
                        ▼
            ┌───────────────────────┐
            │     HTTP RESPONSE     │
            │                       │
            │     Status Code       │
            │     Headers           │
            │     Body              │
            └───────────┬───────────┘
                        │
                        ▼
                      CLIENT
```

---

# Complete Example

For our Expense Tracker API:

```text
Client
  │
  │ POST /expenses
  │
  │ { "amount": 500, "category": "Food" }
  ▼
Tomcat
  │
  ▼
Spring MVC
  │
  ▼
ExpenseController
  │
  │ addExpense(...)
  ▼
Service
  │
  ▼
Repository
  │
  ▼
MySQL
  │
  │ Expense saved
  ▼
Repository
  │
  ▼
Service
  │
  ▼
Controller
  │
  │ Expense object
  ▼
Spring converts object → JSON
  │
  ▼
HTTP 201 Created
  │
  │ {
  │   "id": 1,
  │   "amount": 500,
  │   "category": "Food"
  │ }
  ▼
Client
```

---

# Key Idea

The entire process can be summarized as:

```text
HTTP Request
     ↓
Tomcat
     ↓
Spring MVC
     ↓
Controller
     ↓
Service
     ↓
Repository
     ↓
MySQL
     ↓
Repository
     ↓
Service
     ↓
Controller
     ↓
HTTP Response
     ↓
Client
```

The **request flows into the application**, the application **processes and potentially persists the data**, and the resulting data is returned to the client as an **HTTP response**.