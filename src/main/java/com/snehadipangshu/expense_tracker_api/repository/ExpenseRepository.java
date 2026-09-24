package com.snehadipangshu.expense_tracker_api.repository;
//  This tells Java where the file lives in our folder structure.

import com.snehadipangshu.expense_tracker_api.entity.Expense;
//  This brings our Expense class we just created.
//  So ExpenseRepository.java file knows exactly what kind of data is going to be saving and retrieving.

import org.springframework.data.jpa.repository.JpaRepository;
//  JpaRepository already has pre-written SQL Commands inside it

import org.springframework.stereotype.Repository;
//  This is a label or a badge that you stick onto our file.
//       ↓
//  When it sees the @Repository badge on class
//  , it immediately knows "This specific file is in charge of talking to database.
//  I will treat it with special database-handling rules and connect it to PostgreSQL."

import java.util.List;
//  A standard java import.
//       ↓
//  When we ask the database to "find all the expense", it won't just hand you one Expense
//  object, it will hand us collection of them.
//       ↓
//  List is the Java container used to hold multiple items in a specific order.

import org.springframework.data.jpa.repository.Query;
import java.util.Map;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    //  public : it here means our file is accessible to rest of our application.
    //  Our ExpenseService.java or ExpenseController.java etc, might be able to see and use it.
    //       ↓
    //  interface : In java, an interface is like a job description or a contract.
    //  Notice, we are not saying class, that means we are not building actual machine
    //  here; we are just writing down a list of commands.
    //  Here SpringBoot acts as the manufacturer - it reads the interface and secretly
    //  builds a hidden "class" in the background that actually executes the SQL


    //Spring Data JPA derives SQL queries automatically:

    List<Expense> findByCategory(String category);
    //  SQL Denote :
    //  SELECT * FROM expenses where category = ?
    //       ↓
    //  We are telling Java to expect a collection of Expense objects not just one.
    //       ↓
    //  find : SpringBoot translates this to SELECT * FROM expenses;
    //       ↓
    //  By : This acts as the SQL "WHERE" clause. It tells SpringBoot that a condition is coming next.
    //       ↓
    //  Category : SpringBoot looks at our Expense entity, sees the "private String category;" variable, and knows
    //  to target the specific database column



    //SELECT * FROM expenses WHERE LOWER(description) LIKE LOWER(%keyword%)
    List<Expense> findByDescriptionContainingIgnoreCase(String keyword);
    //  findByDescription: Tells the database to execute SELECT * FROM expenses WHERE description.
    //       ↓
    //  Containing: This is a massive time-saver. It translates to the SQL wildcard LIKE '%keyword%'.
    //  Instead of looking for an exact, identical match, it searches for our keyword hidden anywhere inside the description text.
    //       ↓
    //  IgnoreCase: This tells PostgreSQL to ignore capital letters. Under the hood,
    //  it applies the LOWER() SQL function to both the database column and
    //  our keyword, ensuring they are compared fairly.

    // NEW: Phase 11 Analytics Query
    // We use @Query to write custom JPQL. Instead of pulling all records into Java,
    // we force PostgreSQL to group the categories and calculate the sum directly.
    @Query("SELECT e.category as category, SUM(e.amount) as total FROM Expense e GROUP BY e.category")
    List<Map<String, Object>> getExpenseSummaryByCategory();
}
