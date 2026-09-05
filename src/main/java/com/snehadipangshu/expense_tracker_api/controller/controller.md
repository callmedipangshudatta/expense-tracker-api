# Doubt Questions

Questions that came up while building this project, along with my understanding of what we did.

## Topic: `ExpenseController.java` — REST Controller, Routing, Dependency Injection & CRUD

---

### 1. What is `@RestController`, and what happens when Spring finds `ExpenseController`?

**Answer:**

`@RestController` is a Spring annotation that tells Spring:

> "Treat this class as a REST Controller that can receive HTTP requests and return HTTP responses."

When Spring Boot starts:

```text
SpringApplication.run()
        ↓
@SpringBootApplication
        ↓
@ComponentScan
        ↓
Finds ExpenseController
        ↓
Sees @RestController
        ↓
Registers ExpenseController as a Spring Bean
        ↓
Spring can now use this class to handle HTTP requests
```

`@RestController` is effectively a combination of:

```java
@Controller
@ResponseBody
```

The important distinction is:

```text
@RestController
        ↓
Identifies the class as a REST controller

@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
        ↓
Identify which HTTP requests each method handles
```

So `@RestController` alone does **not** tell Spring which URL should call which method. The mapping annotations do that.

**Code Reference:**

```java
@RestController
public class ExpenseController {
}
```

---

### 2. How does `@RequestMapping("/expenses")` work, and how does it combine with `@GetMapping`, `@PostMapping`, etc.?

**Answer:**

`@RequestMapping("/expenses")` defines the **base URL for the entire controller**.

```java
@RequestMapping("/expenses")
public class ExpenseController {
```

Then the method-level mappings are added to this base path.

For example:

```java
@GetMapping
```

means:

```text
GET /expenses
```

while:

```java
@GetMapping("/{id}")
```

means:

```text
GET /expenses/{id}
```

Similarly:

```java
@PostMapping
```

means:

```text
POST /expenses
```

```java
@PutMapping("/{id}")
```

means:

```text
PUT /expenses/{id}
```

```java
@DeleteMapping("/{id}")
```

means:

```text
DELETE /expenses/{id}
```

So think of it as:

```text
@RequestMapping("/expenses")
            +
       Method Mapping
            ↓
       Final Endpoint
```

Example:

```text
/expenses + /{id}
       ↓
GET /expenses/5
```

---

### 3. Why do we use Constructor Injection for `ExpenseService`, and what does Spring actually do here?

**Answer:**

The controller needs the Service layer to perform business operations:

```java
private final ExpenseService expenseService;
```

Instead of the controller creating the Service itself, Spring provides it through the constructor.

```java
public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
}
```

This is called **Constructor Dependency Injection**.

The idea is:

```text
ExpenseController
        │
        │ needs
        ▼
ExpenseService
```

Spring manages both objects.

During application startup, Spring sees that `ExpenseController` requires an `ExpenseService`. If `ExpenseService` is also registered as a Bean, Spring supplies it to the constructor.

Conceptually:

```text
Spring Container
      │
      ├── creates ExpenseService
      │
      └── creates ExpenseController
              │
              └── gives ExpenseService to constructor
```

This is part of **Dependency Injection** and **Inversion of Control (IoC)**.

The controller does **not** do:

```java
new ExpenseService();
```

because Spring should manage the dependency.

---

### 4. How does `@GetMapping` and `@PathVariable` work when we request an expense by ID?

**Answer:**

This method:

```java
@GetMapping("/{id}")
public ResponseEntity<Expense> getExpense(@PathVariable Long id) {
```

tells Spring:

> "When a GET request comes to `/expenses/{something}`, take that value and give it to the `id` parameter."

For example, the client sends:

```text
GET /expenses/25
```

Spring matches:

```text
/expenses/{id}
```

and extracts:

```text
id = 25
```

Then:

```java
@PathVariable Long id
```

receives:

```java
25L
```

So:

```text
GET /expenses/25
        ↓
{id} = 25
        ↓
@PathVariable
        ↓
Long id = 25
```

Then the controller passes that ID to the Service:

```java
expenseService.getExpenseById(id);
```

The controller therefore acts as the bridge:

```text
HTTP Request
     ↓
Controller
     ↓
Service
     ↓
Database
```

---

### 5. What do `@RequestBody`, `@Valid`, and `ExpenseDto` do when creating or updating an expense?

**Answer:**

For creating an expense:

```java
public ResponseEntity<Expense> addExpense(
        @Valid @RequestBody ExpenseDto expenseDto
)
```

there are three important parts.

#### `@RequestBody`

It tells Spring:

> "Take the JSON body sent by the client and convert it into a Java object."

For example, Postman might send:

```json
{
    "description": "Lunch",
    "amount": 250,
    "category": "Food"
}
```

Spring converts that JSON into:

```java
ExpenseDto expenseDto
```

#### `@Valid`

It tells Spring to run the validation rules defined on `ExpenseDto`.

For example, if the DTO contains:

```java
@NotBlank
private String description;
```

then Spring checks whether the incoming description is valid.

#### `ExpenseDto`

The DTO acts as the object used to receive input from the client.

The controller then manually transfers the DTO data into the Entity:

```java
Expense expense = new Expense();

expense.setDescription(expenseDto.getDescription());
expense.setAmount(expenseDto.getAmount());
expense.setCategory(expenseDto.getCategory());
```

The flow is:

```text
JSON
 ↓
@RequestBody
 ↓
ExpenseDto
 ↓
@Valid
 ↓
Validation
 ↓
Controller maps DTO → Entity
 ↓
Expense
 ↓
Service
```

This keeps the external request model separate from the database Entity.

---

### 6. How does pagination work in `getExpenses()` using `Pageable` and `PageRequest`?

**Answer:**

Instead of retrieving every expense at once, pagination allows us to retrieve a specific portion of the data.

The client can request:

```text
GET /expenses?page=0&size=10
```

These parameters are captured by:

```java
@RequestParam(defaultValue = "0") int page,
@RequestParam(defaultValue = "10") int size
```

So:

```text
page = 0
size = 10
```

Then:

```java
Pageable pageable = PageRequest.of(page, size);
```

creates a `Pageable` object describing:

> "I want page 0, containing 10 records."

Then it is passed to the Service:

```java
Page<Expense> allExpenses =
        expenseService.getAllExpenses(pageable);
```

The result is a `Page<Expense>`, which contains not only the expenses but also pagination information.

Conceptually:

```text
GET /expenses?page=2&size=10
              ↓
page = 2
size = 10
              ↓
PageRequest.of(2, 10)
              ↓
Pageable
              ↓
Service
              ↓
Database
              ↓
Page<Expense>
```

The `defaultValue` means:

```text
No page supplied → page 0
No size supplied → size 10
```

---

### 7. Why do we use `ResponseEntity`, and what are HTTP status codes such as `200`, `201`, `204`, and `404` doing here?

**Answer:**

`ResponseEntity` allows the controller to construct the complete HTTP response sent back to the client.

It can contain:

```text
Response Body
+
HTTP Status Code
+
Headers (if needed)
```

For example:

```java
return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
```

means:

```text
HTTP Status → 201 Created
Body        → savedExpense
```

The status codes in this controller communicate what happened:

| Status           | Meaning                                  | Used here                      |
| ---------------- | ---------------------------------------- | ------------------------------ |
| `200 OK`         | Request succeeded                        | GET / successful update        |
| `201 CREATED`    | New resource was created                 | POST                           |
| `204 NO_CONTENT` | Successful request with no response body | DELETE                         |
| `404 NOT_FOUND`  | Requested resource does not exist        | GET/UPDATE when ID isn't found |

For example:

```java
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
```

tells the client:

> "The requested expense could not be found."

So `ResponseEntity` gives the API control over the HTTP response rather than simply returning an object.

---

### 8. What is the complete flow of `POST /expenses` when creating a new expense?

**Answer:**

When the client sends:

```text
POST /expenses
```

with JSON:

```json
{
    "description": "Lunch",
    "amount": 250,
    "category": "Food"
}
```

the complete flow is:

```text
Client / Postman
        ↓
POST /expenses
        ↓
@RequestMapping("/expenses")
        ↓
@PostMapping
        ↓
addExpense()
        ↓
@RequestBody converts JSON → ExpenseDto
        ↓
@Valid validates ExpenseDto
        ↓
DTO data copied into Expense Entity
        ↓
expenseService.saveExpence(expense)
        ↓
Service handles persistence
        ↓
Saved Expense returned
        ↓
ResponseEntity
        ↓
HTTP 201 CREATED
        ↓
JSON response sent to client
```

The important architectural idea is that the **Controller does not directly handle the database**.

Instead:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

The Controller's primary responsibility is handling the HTTP side of the application.

---

### 9. How does the `PUT /expenses/{id}` update flow work, including what happens when the expense doesn't exist?

**Answer:**

For example:

```text
PUT /expenses/5
```

with:

```json
{
    "description": "Dinner",
    "amount": 500,
    "category": "Food"
}
```

Spring first extracts:

```text
id = 5
```

through:

```java
@PathVariable Long id
```

Then the Controller checks whether the expense exists:

```java
Optional<Expense> existingExpense =
        expenseService.getExpenseById(id);
```

If it exists:

```java
if (existingExpense.isPresent()) {
```

the existing Entity is retrieved:

```java
Expense expenseToUpdate = existingExpense.get();
```

Then its values are replaced:

```java
expenseToUpdate.setDescription(expenseDto.getDescription());
expenseToUpdate.setAmount(expenseDto.getAmount());
expenseToUpdate.setCategory(expenseDto.getCategory());
```

Then it is saved:

```java
Expense saved =
        expenseService.saveExpence(expenseToUpdate);
```

Finally:

```java
return new ResponseEntity<>(saved, HttpStatus.OK);
```

returns `200 OK`.

If it does **not** exist:

```java
return new ResponseEntity<>(HttpStatus.NOT_FOUND);
```

returns:

```text
404 NOT FOUND
```

So:

```text
PUT /expenses/5
       ↓
Find expense 5
       ↓
 ┌─────┴─────┐
 ↓           ↓
Exists?     Doesn't exist
 ↓           ↓
Update      404 NOT_FOUND
 ↓
Save
 ↓
200 OK
```

---

### 10. How does the DELETE operation work, and what is the overall responsibility of this Controller?

**Answer:**

For:

```text
DELETE /expenses/5
```

Spring matches:

```java
@DeleteMapping("/{id}")
```

and extracts:

```java
@PathVariable Long id
```

So:

```text
DELETE /expenses/5
        ↓
id = 5
        ↓
deleteExpense(5)
```

The Controller then delegates the deletion to the Service:

```java
expenseService.deleteExpense(id);
```

After successful deletion, it returns:

```java
return new ResponseEntity<>(HttpStatus.NO_CONTENT);
```

which means:

```text
HTTP 204 NO_CONTENT
```

with no response body.

---

## Overall Responsibility of `ExpenseController`

The Controller is mainly responsible for the **HTTP/API layer**.

It handles:

```text
HTTP Request
     ↓
Routing
     ↓
Reading request data
     ↓
Validation
     ↓
Calling Service
     ↓
Receiving result
     ↓
Choosing HTTP status
     ↓
HTTP Response
```

It does **not** primarily handle:

```text
Business Logic
Database Queries
Database Connection
```

Those responsibilities belong to other layers.

The architecture can therefore be remembered as:

```text
              CLIENT
                │
                │ HTTP Request
                ▼
       ┌─────────────────┐
       │   CONTROLLER    │
       │                 │
       │ Routing         │
       │ Request data    │
       │ Validation      │
       │ HTTP Response   │
       └────────┬────────┘
                │
                │ calls
                ▼
       ┌─────────────────┐
       │     SERVICE     │
       │                 │
       │ Business Logic  │
       └────────┬────────┘
                │
                │ calls
                ▼
       ┌─────────────────┐
       │   REPOSITORY    │
       │                 │
       │ Database access │
       └────────┬────────┘
                │
                ▼
            DATABASE
```

### One-Line Mental Model

> **The Controller is the HTTP entry and exit point of the application: it receives the request, extracts and validates the data, calls the Service, and converts the result into the appropriate HTTP response.**
