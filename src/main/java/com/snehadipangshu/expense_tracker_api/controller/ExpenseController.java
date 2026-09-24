package com.snehadipangshu.expense_tracker_api.controller;

//import means : primarily a Java compiler convenience

//1. Project Specific Models and Logic
import com.snehadipangshu.expense_tracker_api.entity.Expense;
//What it does: Represents our actual database table.
//Real World: The physical receipt we keep after buying coffee.

import com.snehadipangshu.expense_tracker_api.service.ExpenseService;
import com.snehadipangshu.expense_tracker_api.service.UpiParserService;
//What it does: Contains the heavy business logic (calculations, parsing, saving).
//Real World: The accountant in the back office might crunch numbers and organize the related files.

import com.snehadipangshu.expense_tracker_api.dto.ExpenseDto;
import com.snehadipangshu.expense_tracker_api.dto.SmsRequestDto;
//What it does: Data Transfer Objects (DTOs) carry specific data between processes without exposing the entire database model.
//Real World: An executive summary handed to a client instead of your messy 100-page raw notebook.

//2. Spring Web and API Routing
import org.springframework.web.bind.annotation.*;
//What it does: Maps web URLs (like /api/expenses) to our Java code (contains tools like @GetMapping or @PostMapping).
//Real World: A receptionist who listens to a customer's request and points them to the correct department.

//3. API Response and Validation
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//What it does: Formats the final answer sent back to the user, including the status code (200 OK, 404 Not Found) and the data.
//Real World: A postal tracking update saying "Delivered Successfully" vs "Address Not Found."


import jakarta.validation.Valid;
//What it does: Checks if incoming data follows the rules before letting into our system.
//Real World: The bouncer at club checking IDs at the door.

//4. Data Management and Pagination
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//What it does: Chunks massive amounts of data into smaller, loadable sections.
//Real World: Reading a 500-page book one page at a time instead of reading it on one giant, infinite scroll

//5. Core Java Utilities
import java.util.List;
//What it does: a dynamic collection of items
//Real World: Our grocery shopping list

import java.util.Optional;
//What it does: A container that may or may not contain data, preventing the dreaded "Null Pointer Exception"
//Real World: A Schrödinger's delivery box—you have a safe way to handle it whether it's empty or full.

import java.util.Map;

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
//       ↓
//  @RestController
//       ↓
//  This class is a REST controller.
//       ↓
//  Spring registers it as a component that can handle web requests.
//       ↓
//  @GetMapping / @PostMapping / etc.
//       ↓
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
//What it does: Tells Spring that every method in this class will return pure data (like JSON), not HTML web-pages
//Real World: A dedicated drive-thru window that hands you exactly the food you ordered in a neat bag, rather than inviting you inside the restaurant to sit down.

//  Class - Level Routing
@RequestMapping("/expenses")
//What it does: Sets the base URL path for this entire class. Any specific route we build inside will automatically start with /expenses.
//Real World: The directory sign in an office lobby saying, "All Expense matters are handled in Room 300."

public class ExpenseController{

    //final is written: so that it can't be accidentally overwritten while the app is running
    private final ExpenseService expenseService;
    private final UpiParserService upiParserService;

    //Constructor Injection
    public ExpenseController(ExpenseService expenseService, UpiParserService upiParserService) {
        this.expenseService = expenseService;
        this.upiParserService = upiParserService;
    }
    //What it does: Spring automatically passes the required services (ExpenseService, UpiParserService) into the controller when the app starts. The final keyword ensures these tools can't be accidentally overwritten while the app is running.
    //Real World: A surgeon stepping into the operating room where the team has already perfectly laid out the exact sterile tools needed for the surgery.


    //  tells Spring:
    //  "For a GET request to /expenses, use this method."
    //  Method : @GetMapping("/expenses")

    //  Level 2. Give specific requests
    //  When a GET request comes to /expenses,
    //  Spring execute this method

    //NEW : pagination
    @GetMapping
    //What it does: It acts as a trigger! Because our class has @RequestMapping("/expenses") at the top,
    //this annotation tells Spring: "If an HTTP GET request comes in for /expenses, wake up this specific method"

    public ResponseEntity<Page<Expense>> getExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        //This converts the URL number into Pageable object
        Pageable pageable = PageRequest.of(page,size);

        //Fetch the specific page of data
        Page<Expense> allExpenses = expenseService.getAllExpenses(pageable);

        return new ResponseEntity<>(allExpenses, HttpStatus.OK);
        //  ResponseEntity packages our response back into an official HTTP format
        //  with status codes like 200 OK or 201 Created
    }
    //Explanation : This specific code says that ResponseEntity<Page<Expense>> is class return type, that describes
    //we will store expense in Page Format which shows only chunk of Expense Records rather than Raw, infinite scroll page
    //

    //  2. GET BY ID
    @GetMapping("/{id}")
    //What it does: Because the class is already mapped to /expenses,
    //this tells Spring to listen for requests that look like /expenses/5 or /expenses/102

    //to find the single-specific resource, we use PathVariable
    public ResponseEntity<Expense> getExpense(@PathVariable Long id){

        Optional<Expense> expense = expenseService.getExpenseById(id);
        //What it does: The Controller hands the ID to the Service and says,
        // "Go find this in the database."

        //Why Optional -> becuase we cant return direct null, so instead we send in sealed Box, that would not crash


        if(expense.isPresent()){
            //What it does: You are checking the box before trying to pull data out of it.
            //It asks, "Did the database actually find something?"
            return new ResponseEntity<>(expense.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. CREATE
    //  @PathVariable :- Take the value from {id} in the URL
    //  and give it to out Java Method as the id variable
    @PostMapping
    // NEW: We use @Valid and ExpenseDto here!
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody ExpenseDto expenseDto){
        //  @RequestBody tells Spring to take incoming JSON text from Postman
        //  and map it into our Java Expense object

        // Manual Mapping: Transfer data from the DTO (Teller) to the Entity (Vault)
        Expense expense = new Expense();
        expense.setDescription(expenseDto.getDescription());
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(expenseDto.getCategory());
        //  When creating a resource, it is best practice to return 201 created

        Expense savedExpense = expenseService.saveExpence(expense);
        return new ResponseEntity<>(savedExpense,HttpStatus.CREATED);
    }

    // 4. UPDATE
    // @PutMapping is used for updating an existing resource
    @PutMapping("/{id}")
    // NEW: We use @Valid and ExpenseDto here too!
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id,@Valid @RequestBody ExpenseDto expenseDto){
        Optional<Expense> existingExpense = expenseService.getExpenseById(id);

        if (existingExpense.isPresent()) {
            Expense expenseToUpdate = existingExpense.get();

            expenseToUpdate.setDescription(expenseDto.getDescription());
            expenseToUpdate.setAmount(expenseDto.getAmount());
            expenseToUpdate.setCategory(expenseDto.getCategory());

            Expense saved = expenseService.saveExpence(expenseToUpdate);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 5. DELETE
    // @DeleteMapping handles removal
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        // HttpStatus.NO_CONTENT (204) is standard for successful deletion
        // when we have no body to return back to the client
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 6. PARSE RAW UPI SMS (PHASE 10)
    // Accepts raw SMS text, extracts merchant + amount, assigns category
    @PostMapping("/parse")
    public ResponseEntity<ExpenseDto> parseUpiMessage(@Valid @RequestBody SmsRequestDto request){
        ExpenseDto parsedExpense = upiParserService.parseSms(request.getSmsText());
        return new ResponseEntity<>(parsedExpense, HttpStatus.OK);
    }

    // 7. ANALYTICS (PHASE 11)
    // Returns total spending grouped by category for frontend charts
    @GetMapping("/analytics")
    public ResponseEntity<List<Map<String, Object>>> getAnalytics() {
        List<Map<String, Object>> summary = expenseService.getCategorySummary();
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

}