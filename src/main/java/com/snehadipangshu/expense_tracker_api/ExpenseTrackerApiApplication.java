// We are using Spring Boot 4.1.0 VERSION

package com.snehadipangshu.expense_tracker_api;

import org.springframework.boot.SpringApplication;
//	If we are building our web application, it automatically starts an embedded server, so that our app can listen for request immediately
//	It sets up default configuration of our app or web
//	It starts the Spring Application context(the container hold all our applications object/beans)


import org.springframework.boot.autoconfigure.SpringBootApplication;


// @SpringBootApplication is just like a teacher who approves a student assignment
// Student is Java Class {ExpenseTrackerApiApplication}
// Teacher is {@SpringBootApplication} which changes the meaning of the class

// @SpringBootApplication is annotation : combines three different annotations
// @Configuration + @EnableAutoConfiguration + "@ComponentScan"
// Responsibility : Start Application -> Configure Everything -> Search for Components

// Spring sees this class and says
//
//"Aha! This is the main Spring Boot application."


//		Architecture :-

//		@SpringBootApplication
//        ↓
//		@EnableAutoConfiguration
//        ↓
//		"Configure what we need"
//		↓
//		Web infrastructure can start
//        ↓
//		Tomcat
//        ↓
//		Controller

@SpringBootApplication

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

		//Boots Spring, starts the embedded Tomcat server, and keeps the application running
		SpringApplication.run(
				ExpenseTrackerApiApplication.class, args);
	}

}
