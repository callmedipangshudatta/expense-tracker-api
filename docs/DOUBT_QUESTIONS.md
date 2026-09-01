# Doubt Questions

Questions that came up while building this project, along with my understanding of what we did.

## Topic: `ExpenseTrackerApiApplication.java` — Spring Boot Bootstrap & Core Annotations

---

### 1. What happens under the hood when `SpringApplication.run()` is executed?

**Answer:**

It triggers the entire lifecycle of the Spring Boot application:

1. Boots Spring Boot.
2. Creates the **Spring Application Context** (IoC container).
3. Reads the application's configuration.
4. Performs component scanning to find classes such as `ExpenseController`.
5. Creates and manages the required Spring Beans.
6. Starts the embedded **Tomcat server**.
7. Tomcat starts listening for HTTP requests, usually on **port `8080`**.
8. The application keeps running and waits for incoming requests.

**Code Reference:**

```java
// Creates Application Context
// -> Reads configuration
// -> Finds components
// -> Creates Beans
// -> Starts Tomcat
// -> Listens on port 8080

SpringApplication.run(ExpenseTrackerApiApplication.class, args);
```

---

### 2. Which three core annotations are combined to form `@SpringBootApplication`?

**Answer:**

`@SpringBootApplication` is a convenience annotation that combines three important annotations:

* `@Configuration`
* `@EnableAutoConfiguration`
* `@ComponentScan`

So, conceptually:

```text
@SpringBootApplication
        |
        +-- @Configuration
        +-- @EnableAutoConfiguration
        +-- @ComponentScan
```

**Code Reference:**

```java
@SpringBootApplication
```

---

### 3. What is the specific responsibility of `@EnableAutoConfiguration` under the hood?

**Answer:**

`@EnableAutoConfiguration` tells Spring Boot:

> "Look at the dependencies available in the application and automatically configure the application appropriately."

For example, if Spring Boot detects dependencies related to:

* Spring MVC
* Embedded Tomcat
* Jackson
* JPA
* Hibernate

then Spring Boot can automatically configure the infrastructure required for those technologies.

For a web application, this contributes to setting up things such as the embedded web server and Spring MVC infrastructure.

**Code Reference:**

```java
@EnableAutoConfiguration
```

**Important distinction:**

```text
@ComponentScan
    ↓
Finds your application's components

@EnableAutoConfiguration
    ↓
Configures the application's infrastructure
    based on the dependencies present
```

---

### 4. How does `@ComponentScan` locate components like `ExpenseController`?

**Answer:**

By default, component scanning starts from the package containing the main application class and scans that package **and all of its sub-packages**.

For example:

```text
com.snehadipangshu.expense_tracker_api
│
├── ExpenseTrackerApiApplication.java
│
├── controller
│   └── ExpenseController.java
│
├── service
│   └── ExpenseService.java
│
└── repository
    └── ExpenseRepository.java
```

If the main class is inside:

```text
com.snehadipangshu.expense_tracker_api
```

then Spring automatically scans:

```text
com.snehadipangshu.expense_tracker_api
com.snehadipangshu.expense_tracker_api.controller
com.snehadipangshu.expense_tracker_api.service
com.snehadipangshu.expense_tracker_api.repository
```

When Spring finds classes annotated with things such as:

```java
@Controller
@RestController
@Service
@Repository
@Component
```

it registers them as **Beans** in the Application Context.

**Code Reference:**

```java
@SpringBootApplication
```

because `@SpringBootApplication` includes `@ComponentScan`.

---

### 5. How does Spring Boot start an embedded server like Tomcat without requiring an external server setup?

**Answer:**

Spring Boot applications can include an **embedded servlet container**, such as Tomcat, as part of their dependencies.

When Spring Boot detects that the application is a servlet-based web application, its auto-configuration sets up the web environment and embedded server.

The important idea is:

```text
Traditional Java Web Application:

Your Application
      ↓
External Tomcat
      ↓
Deploy WAR
      ↓
Run Application
```

Whereas with Spring Boot:

```text
Spring Boot Application
      ↓
Embedded Tomcat
      ↓
Application runs inside it
```

So you can simply run:

```java
SpringApplication.run(ExpenseTrackerApiApplication.class, args);
```

and Spring Boot starts the web server as part of the application startup process.

**Code Reference:**

```java
SpringApplication.run(ExpenseTrackerApiApplication.class, args);
```

---

### 6. What is the Spring Application Context, and why is it initialized at startup?

**Answer:**

The **Spring Application Context** is the central **IoC (Inversion of Control) container** of the Spring application.

It is responsible for:

* Creating Beans
* Storing Beans
* Configuring Beans
* Managing Bean lifecycles
* Injecting dependencies
* Connecting different parts of the application

For example:

```text
Application Context
│
├── ExpenseController
├── ExpenseService
├── ExpenseRepository
└── Other Beans
```

Suppose:

```java
@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
}
```

Spring needs to know:

> "Where should I get `ExpenseService` from?"

The Application Context manages that relationship.

So during startup:

```text
SpringApplication.run()
        ↓
Application Context created
        ↓
Components discovered
        ↓
Beans created
        ↓
Dependencies connected
        ↓
Application becomes ready
```

**Code Reference:**

```java
SpringApplication.run(ExpenseTrackerApiApplication.class, args);
```

---

### 7. Why do we pass `ExpenseTrackerApiApplication.class` as the first argument to `SpringApplication.run()`?

**Answer:**

Because Spring Boot needs to know the **primary configuration/source class** from which it should bootstrap the application.

```java
SpringApplication.run(
    ExpenseTrackerApiApplication.class,
    args
);
```

Here:

```java
ExpenseTrackerApiApplication.class
```

tells Spring:

> "Use this class as one of the primary sources for configuring and starting the application."

Because this class is normally located at the root package, it also establishes the natural starting point for component scanning.

For example:

```text
com.snehadipangshu.expense_tracker_api
│
├── ExpenseTrackerApiApplication
│
├── controller
├── service
└── repository
```

So this class effectively becomes the **starting point of the Spring Boot application**.

---

### 8. Where does the default port `8080` come from, and can it be changed?

**Answer:**

For a typical Spring Boot servlet web application, the embedded server uses **port `8080` by default**.

The port can be changed using:

```properties
server.port=9090
```

inside:

```text
src/main/resources/application.properties
```

Then the application will listen on:

```text
http://localhost:9090
```

instead of:

```text
http://localhost:8080
```

**Code Reference:**

```properties
server.port=9090
```

**Conceptually:**

```text
Default:
Application → Embedded Tomcat → 8080

Changed:
Application → Embedded Tomcat → 9090
```

---

### 9. What happens if a component like a Controller or Service is placed outside the root package?

**Answer:**

By default, Spring's component scanning only searches the package containing the main application class and its sub-packages.

For example, if the main class is:

```text
com.snehadipangshu.expense_tracker_api
```

Spring automatically scans:

```text
com.snehadipangshu.expense_tracker_api.*
```

But suppose we create:

```text
com.otherpackage.ExpenseController
```

This is outside the default scanning hierarchy.

Therefore, Spring will generally **not discover it automatically**.

The result can be that the class is not registered as a Bean, meaning Spring cannot automatically manage or inject it.

You can explicitly configure scanning if necessary:

```java
@ComponentScan(basePackages = "com.otherpackage")
```

**Important idea:**

```text
Root Package
    ↓
Scanned
    ↓
Sub-packages
    ↓
Scanned
```

But:

```text
Completely Different Package
    ↓
Not automatically scanned
```

---

### 10. Why must the `main` method containing `SpringApplication.run()` be declared as `static`?

**Answer:**

The Java Virtual Machine (JVM) needs a way to start the application **without first creating an object of the class**.

The JVM looks for the standard entry point:

```java
public static void main(String[] args)
```

The important part here is:

```java
static
```

Because a static method belongs to the **class itself**, the JVM can invoke it directly.

For example:

```java
public static void main(String[] args) {
    SpringApplication.run(ExpenseTrackerApiApplication.class, args);
}
```

The startup flow is therefore:

```text
JVM
 ↓
Finds main()
 ↓
Calls static main()
 ↓
SpringApplication.run()
 ↓
Spring Boot starts
 ↓
Application Context created
 ↓
Beans discovered and created
 ↓
Embedded Tomcat starts
 ↓
Application waits for HTTP requests
```

---

# Quick Mental Model

When the application starts, think of it like this:

```text
JVM
 │
 │ calls
 ▼
main()
 │
 │ calls
 ▼
SpringApplication.run()
 │
 ├── Creates Spring Application Context
 │
 ├── Reads configuration
 │
 ├── @ComponentScan
 │      └── Finds your Beans
 │
 ├── @EnableAutoConfiguration
 │      └── Configures infrastructure
 │
 ├── Creates and wires Beans
 │
 ├── Starts Embedded Tomcat
 │
 └── Listens for HTTP requests
          │
          ▼
       Port 8080
```

## The Three Most Important Annotations

```text
@SpringBootApplication
        │
        ├── @Configuration
        │       └── This class can provide configuration
        │
        ├── @EnableAutoConfiguration
        │       └── Automatically configures infrastructure
        │
        └── @ComponentScan
                └── Finds application components
```

## One-Line Understanding

> **`SpringApplication.run()` starts the Spring Boot application, creates the Application Context, discovers and configures Beans, starts the embedded web server, and keeps the application alive to handle HTTP requests.**
