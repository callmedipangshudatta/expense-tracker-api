package com.snehadipangshu.expense_tracker_api.repository;

import com.snehadipangshu.expense_tracker_api.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    //Spring Data JPA derives SQL queries automatically:

    //SELECT * FROM expenses where category = ?
    List<Expense> findByCategory(String category);

    //SELECT * FROM expenses WHERE LOWER(description) LIKE LOWER(%keyword%)
    List<Expense> findByDescriptionContainingIgnoreCase(String keyword);
}
