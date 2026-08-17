# Doubt Questions

Questions that came up while building this project, along with my understanding of what we did.

## Q1. What is REST?

### Representational State Transfer. In simple words, it means "data transfer".

### If an API is the "waiter" that takes our request to the "kitchen", then REST is a strict "set of rules and etiquette" that the waiter and kitchen must follow.

### It is a form of architectural style. It is a standard way of designing web APIs so that they are fast, predictable, and easy to use.

### When an API follows these principles, it is called a RESTful API.

## Q2. What is Tomcat really? Why is it needed?

### Tomcat, also known as Apache Tomcat, is a piece of software designed specifically to run Java web applications and connect them to the internet.

### It's needed because:

### 1. Without it, Java would remain just the application logic and would not be able to directly serve HTTP requests over the web.

### 2. It listens for incoming HTTP traffic on a port, such as port 8080 on your computer.

### 3. It receives HTTP requests and passes them to the Java application, and then sends the application's response back as an HTTP response.
