//  com.snehadipangshu :- WHO/Personal Identifier
//  expense_tracker_api :- Root Folder of this Project
//  entity :- Role
package com.snehadipangshu.expense_tracker_api.entity;

//  This single line of code connects our Java application to a massive piece of machinery known as JPA(Jakarta Persistence API)
//  ,which handles Object-Relational Mapping (ORM)
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//  @Entity :- We put this above our class to tell Java,
//  that this class represents a database table OR
//  "Create a database table for this."
@Entity

//  Allows us to specify the exact table name
@Table(name = "expenses")

public class Expense {

    //  @Id marks this field as the Primary Key (the unique identifier)
    //  It designates this specific column as the Primary Key.

    //  example : "id" is our Primary Key in our Expense Tracker
    @Id

    //  @GeneratedValue : "Generate a Value" but automatically without user even typing it OR
    //  it tells PostgreSQL to auto-increment this ID for us (1,2,3..)

    //  strategy : is Java asking us "Which method do we want to use to create this number?"

    //  IDENTITY : tells the database to handle the counting automatically
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

    //  Automatically records when the expense was created
    //  Java Variable : createdAt uses 'camelCase'
    //  SQL Variable : created_at uses 'snake_case'
    //  This maps the Java Variable to the correct dB column name

    //  name = "created_at" :
    //  "we are explicitly mentioning the name for our dB column"

    //  updatable = false :
    //  "it means once the row is
    //  created, lock this column.Even if our own Java code
    //  accidentally tries to change the creation date later,
    //  block the update."
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
