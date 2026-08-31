package com.snehadipangshu.expense_tracker_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.snehadipangshu.expense_tracker_api.entity.Expense;
import com.snehadipangshu.expense_tracker_api.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

// @Service tells Spring: "This is a business logic class. Manage it for us"
@Service
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
