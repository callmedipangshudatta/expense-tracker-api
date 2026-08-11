package com.snehadipangshu.expense_tracker_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//  Spring Boot starts
//       ↓
//  @SpringBootApplication
//       ↓
//  Spring scans application
//       ↓
//  Finds ExpenseController
//       ↓
//  Sees @RestController
//       ↓
//  "Okay, this class handles HTTP requests."

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
public class ExpenseController{

    //  tells Spring:
    //  "For a GET request to /expenses, use this method."
    //  Method : @GetMapping("/expenses")

    //  Level 2. Give specific requests
    //  When a GET request comes to /expenses,
    //  Spring execute this method

    @GetMapping("/expenses") // GET /expenses
    public String getExpenses(){
        return "Here are the expenses";
    }
}


