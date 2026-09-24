//  <--- Business Logic Layer --->

package com.snehadipangshu.expense_tracker_api.service;
//  importing and using the @Service annotation tells Spring Boot,
//  "This is the control center where all our business math, validations, and core logic will happen."

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//  These allow us to fetch-data in bite-sized chunks

import com.snehadipangshu.expense_tracker_api.entity.Expense;
import com.snehadipangshu.expense_tracker_api.repository.ExpenseRepository;
//  The Service layer needs to manipulate data, so it brings our Expense vault and
//  the ExpenseRepository(our robotic librarian) to issue commands like "repository.save()"

import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
//  Optional is Java's way of forcing us to be safe.
//       ↓
//  Example : When you call repository.findById(99),
//  Spring Boot no longer hands you the Expense directly.
//  It hands you an Optional<Expense> (the sealed box).

@Service
//  @Service tells SpringBoot : "This is more like the brain of the operation.
//  It handles the core business rules"

public class ExpenseService {

    //We bring in the Repository so the Service can talk to the database
    private final ExpenseRepository expenseRepository;

    //Constructor Injection (This fixes the 'expenseRepository')
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    //GET ALL
    public Page<Expense> getAllExpenses(Pageable pageable) {
        // findAll(pageable) is a built-in Spring Data JPA magic method!
        return expenseRepository.findAll(pageable);
    }

    //GET BY ID
    public Optional<Expense> getExpenseById(Long id){
        return expenseRepository.findById(id);
    }

    //CREATE/UPDATE
    public Expense saveExpence(Expense expense){
        return expenseRepository.save(expense);
    }

    //DELETE
    public void deleteExpense(Long id){
        expenseRepository.deleteById(id);
    }
}
