// We are using Spring Boot 4.1.0 VERSION

package com.snehadipangshu.expense_tracker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// additionally including imports for hibernating our JPA request error

import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;

// @SpringBootApplication is just like a teacher who approves a student assignment
// Student is Java Class {ExpenseTrackerApiApplication}
// Teacher is {@SpringBootApplication} which changes the meaning of the class

// @SpringBootApplication is annotation : combines three different annotations
// @Configuration + @EnableAutoConfiguration + "@ComponentScan"
// Responsibility : Start Application -> Configure Everything -> Search for Components

// Spring sees this class and says
//
//"Aha! This is the main Spring Boot application."

@SpringBootApplication(

		exclude = {
				//  DataSource :- object that manages connection between Application and Database
				DataSourceAutoConfiguration.class,

                //  Hibernate JPA :- Hibernate Java Persistence API is our Bilingual Translator
                //  JPA :- sets or defines the rules/interfaces for persisting Java Objects

                //  Hibernate :- an implementation of JPA
                //  Hibernate does the ORM work between Java objects and the database
				HibernateJpaAutoConfiguration.class

				// Why .class at end?

				// "Look at the blueprint for the Address Book (DataSource...class) and
				// the blueprint for the Translator (Hibernate...class).
				// where our exclude command tells
				// 'I forbid you from hiring for these roles or building these right now.
				// Just start the web server!' ".
		}

				//		Architecture :-

				//		@SpringBootApplication
				//        ↓
				//		@EnableAutoConfiguration
				//        ↓
				//		"Configure what you need,
				//		BUT exclude DataSource + Hibernate JPA."
				//		↓
				//		Web infrastructure can start
				//        ↓
				//		Tomcat
				//        ↓
				//		Controller


)
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {

	// main()
	//     ↓
	// SpringApplication.run()
	//     ↓
	// Spring Boot starts
	//     ↓
	// Creates Spring Application Context
	//     ↓
	// Reads application configuration
	//     ↓
	// Finds Spring components
	//     ↓
	// Finds ExpenseController
	//     ↓
	// Starts embedded Tomcat
	//     ↓
	// Port 8080
	//     ↓
	// Waits for HTTP requests

	// Temporary :-
	// Database/JPA autoconfiguration is currently excluded.
	// We will configure MySQL + JPA properly later.



//		Boots Spring, starts the embedded Tomcat server, and keeps the application running
		SpringApplication.run(
				ExpenseTrackerApiApplication.class, args);
	}

}
