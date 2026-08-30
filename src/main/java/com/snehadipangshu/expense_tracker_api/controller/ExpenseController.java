package com.snehadipangshu.expense_tracker_api.controller;

//import means : primarily a Java compiler convenience
import com.snehadipangshu.expense_tracker_api.entity.Expense;
import com.snehadipangshu.expense_tracker_api.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    // --- NEW: Bring in the Service layer to talk to the database ---
    private final ExpenseService expenseService;

    //Constructor Injection
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    //  tells Spring:
    //  "For a GET request to /expenses, use this method."
    //  Method : @GetMapping("/expenses")

    //  Level 2. Give specific requests
    //  When a GET request comes to /expenses,
    //  Spring execute this method

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(){
        //  HttpStatus.OK = 200
        List<Expense> allExpenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(allExpenses,HttpStatus.OK);
    }

    //  2. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id){

        Optional<Expense> expense = expenseService.getExpenseById(id);

        if(expense.isPresent()){
            return new ResponseEntity<>(expense.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. CREATE
    //  @PathVariable :- Take the value from {id} in the URL
    //  and give it to out Java Method as the id variable
    @PostMapping
    // Notice we are now returning an 'Expense' object, not a 'String'!
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense){
        // When creating a resource, it is best practice to return 201 created

        Expense savedExpense = expenseService.saveExpence(expense);
        return new ResponseEntity<>(savedExpense,HttpStatus.CREATED);
    }

    // 4. UPDATE
    // @PutMapping is used for updating an existing resource
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense){
        Optional<Expense> existingExpense = expenseService.getExpenseById(id);

        if (existingExpense.isPresent()) {
            Expense expenseToUpdate = existingExpense.get();
            expenseToUpdate.setDescription(updatedExpense.getDescription());
            expenseToUpdate.setAmount(updatedExpense.getAmount());
            expenseToUpdate.setCategory(updatedExpense.getCategory());

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

}


