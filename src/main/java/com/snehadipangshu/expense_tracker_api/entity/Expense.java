// <---IMPORTANT--->

package com.snehadipangshu.expense_tracker_api.entity;
//  com.snehadipangshu :- WHO/Personal Identifier
//  expense_tracker_api :- Root Folder of this Project
//  entity :- Role

import jakarta.persistence.*;
//  jakarta : root namespace
//  persistence : concept of data longevity, to take data from temporarily RAM to permanent storage device like Relational Database
//  * : known as 'wildcard', which means bring in every class, interface and annotation in the package



import java.math.BigDecimal;
import java.time.LocalDateTime;


//  Our Expense class is tied directly to our
//  "PostgreSQL database"
//          ↓
//  [ Its only job is to match our database columns
//  row-for-row. ]
//  We are talking about @Entity annotation, where it acts as a direct blueprint for our database table
//  Whatever fields exist in this class will directly map to columns in PostgreSQL.
//          ↓
//  [ It holds sensitive, permanent data
//  (like the auto-generated id and the createdAt
//  timestamp).]
//  These are backend-controlled variables that should not be tampered with by external users
//          ↓
//  [ We never want the outside
//  world to have direct access to the vault.]
//  Vault is our Entity class and this is why DTO comes up acting as "middlemen"
//  from saving entity to actually prevent it from :
//  -> Accidental Data Leakage (Sending too much)

@Entity
//  1.
//  @Entity :- We put this above our class to tell Java,
//  that this class represents a database table OR
//  "Create a database table for this."

//  2.
//  If a class does not have this annotation, Hibernate ignore this.
//  It remains a standard Java Object that lives in RAM and disappears when server stops.

//  If a class does have this annotation, Hibernate register this as a managed object.
//  We are giving ORM permission to read the variables insidex` the class and,
//  and automatically generate the SQL required to create, update, manage a corresponding table in the database.

@Table(name = "expenses")
//  Allows us to specify the exact table name
//  If we did not specify it earlier, it would have named as 'Expense'
//  But according to SQL Conventions : a database table must be Plural and Lowercase

public class Expense {

    @Id
    //  @Id marks this field as the Primary Key (the unique identifier)
    //  It designates this specific column as the Primary Key.
    //  example : "id" is our Primary Key in our Expense Tracker

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //  @GeneratedValue : "Generate a Value" but automatically without user even typing it OR
    //  it tells PostgreSQL to auto-increment this ID for us (1,2,3..)
    //          ↓
    //  strategy : is Java asking us "Which method do we want to use to create this number?"
    //          ↓
    //  IDENTITY : tells the database to handle the counting automatically, completely preventing
    //  duplicate IDs even if 100 users submit an expense at the exact same millisecond

    @Column(nullable = false)
    private String description;
    //  private : An object-oriented rule(encapsulation).
    //  It hides this variable from other Java classes.
    //  We cant modify it directly; they must use "getter" and "setter".
    //          ↓
    //  String  : The standard Java type for text
    //          ↓
    //  @Column(nullable = false) : We are commanding the database to enforce a strict rule.
    //  If any parts of our application tries to save an expense without description,
    //  the database, will reject it and instantly throw an error.

    @Column(nullable = false)
    private BigDecimal amount;
    //  private : An object-oriented rule(encapsulation).
    //  It hides this variable from other Java classes.
    //  We cant modify it directly; they must use "getter" and "setter".
    //          ↓
    //  BigDecimal : This is crucial Java class used specifically for financial math.
    //  It uses base-10 arithmetic, make it perfectly exact.
    //          ↓
    //  @Column(nullable = false) : We are commanding the database to enforce a strict rule.
    //  If any parts of our application tries to save an expense without amount,
    //  the database, will reject it and instantly throw an error.



    private  String category;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    //  LocalDataTime : A Java class that stores a date and time without worry about global time zones
    //  LocalDataTime.now() : The moment we create a new Expense object in our Java Code, this automatically stamps it
    //          ↓
    //  name = "created_at" : This tells ORM how to explicitly convert Java name to database name
    //          ↓
    //  updatable = false : It's like strict security lock. If we write code later that tries to change
    //  the expense description or category, Hibernate will exclude created_at from all future UPDATE statements for now


    public Expense(){
    }
    //This is primarly used to inject raw database data into them, through the help of Hibernate

    public Expense(String description, BigDecimal amount, String category){
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    //  This is our self-made constructor, our Java code uses this constructor
    //  to easily bundle the incoming description, amount, and category into a
    //  new Java object in one line of code.

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
