package com.snehadipangshu.expense_tracker_api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Entity tells Hibernate: "Make a database table out of this class"
@Entity

//@Table is optional, but it's good practice to explicitly name our table
@Table(name = "expenses")

//entity : it only cares about how data object looks like and how it maps to PostgreSQL table
public class Expense {

    //@Id marks this field as the Primary Key (the unique identifier)
    @Id

    //@GeneratedValue tells PostgreSQL to auto-increment this ID for us (1,2,3..)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column allows us to customize the database column
    //nullable = false means this field cannot be empty
    @Column(nullable = false)
    private String description;

    //We use BigDecimal for money to avoid rounding errors that happen with Double/Float
    @Column(nullable = false)
    private BigDecimal amount;

    private  String category;

    //Automatically records when the expense was created
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


}
