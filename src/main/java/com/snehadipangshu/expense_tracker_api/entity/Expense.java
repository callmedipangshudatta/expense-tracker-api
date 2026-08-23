package com.snehadipangshu.expense_tracker_api.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//@Entity tells Hibernate: "Make a database table out of this class"
//@Entity tells marks the class as a persistent Java object -> Creates a Table
@Entity

//@Table is optional, but it's good practice to explicitly name our table
@Table(name = "expenses")

//entity : it only cares about how data object looks like and how it maps to PostgreSQL table
public class Expense {

    //@Id marks this field as the Primary Key (the unique identifier)
    //Identifies the unique field for each object
    @Id

    //@GeneratedValue tells PostgreSQL to auto-increment this ID for us (1,2,3..)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column : maps a variable to a specific column or rule
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

    // JPA requires a default, no-argument constructor to work its magic
    public Expense(){
    }

    public Expense(String description, BigDecimal amount, String category){
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }


}
