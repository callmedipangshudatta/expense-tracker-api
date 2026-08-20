package com.snehadipangshu.expense_tracker_api.controller;

//import means : primarily a Java compiler convenience
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//  Spring Boot starts
//       ↓
//  @SpringBootApplication
//       ↓
//  Spring scans application through @ComponentScan of @SpringBootApplication
//       ↓
//  Finds ExpenseController.java file
//       ↓
//  Sees @RestController //annotation for Controller
//       ↓
//  "Okay, this class handles HTTP both requests and responses."


//  @RestController
//     ↓
//  This class is a REST controller.
//     ↓
//  Spring registers it as a component that can handle web requests.
//     ↓
//  @GetMapping / @PostMapping / etc.
//     ↓
//  Define WHICH requests map to WHICH methods.




//  @RestController is an annotation

//  Annotation means ?
//  : extra information we attach to a class/method
//  so that a framework can understand how to treat it

//  basically tells Spring:
//  "Spring, treat this class as a REST controller.
//  It can receive HTTP requests and send HTTP responses."

//  Level 1. Identify the Controller
//  Mentioning @RestController tells to Spring this Java class
//  can handle HTTP Requests and send HTTP Response
//  but without any request, until we put something like
//  @GetMapping or @PostMapping annotations respectively

@RestController

//  Class - Level Routing
@RequestMapping("/expenses")
public class ExpenseController{

    //  tells Spring:
    //  "For a GET request to /expenses, use this method."
    //  Method : @GetMapping("/expenses")

    //  Level 2. Give specific requests
    //  When a GET request comes to /expenses,
    //  Spring execute this method

    @GetMapping
    public ResponseEntity<String> getExpenses(){
        //  HttpStatus.OK = 200
        return new ResponseEntity<>("Here are all the expenses",HttpStatus.OK);
    }

    //  2. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<String> getExpense(@PathVariable String id){
        //  In the future, if the ID isn't found, you would return HttpStatus.NOT_FOUND (404)
        return new ResponseEntity<>("Here is expense: " + id,HttpStatus.OK);
    }

    // 3. CREATE
    //  @PathVariable :- Take the value from {id} in the URL
    //  and give it to out Java Method as the id variable
    @PostMapping
    public ResponseEntity<String> addExpense(@RequestBody String expense){
        // When creating a resource, it is best practice to return 201 created
        return new ResponseEntity<>("Create expense: "+expense, HttpStatus.CREATED);
    }

    // 4. UPDATE
    // @PutMapping is used for updating an existing resource
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpense(@PathVariable String id,@RequestBody String updatedExpense){
        return new ResponseEntity<>("Update expense: " + id + " with data: "+updatedExpense,HttpStatus.OK);
    }

    // 5. DELETE
    // @DeleteMapping handles removal
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable String id){
        // HttpStatus.NO_CONTENT (204) is standard for successful deletion
        // when we have no body to return back to the client
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


