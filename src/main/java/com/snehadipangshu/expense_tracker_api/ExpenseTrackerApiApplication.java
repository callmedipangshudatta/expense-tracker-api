package com.snehadipangshu.expense_tracker_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication is just like a teacher who approves a student assignment
// Student is Java Class {ExpenseTrackerApiApplication}
// Teacher is {@SpringBootApplication} which changes the meaning of the class

// @SpringBootApplication is annotation : combines three different annotations
// @Configuration + @EnableAutoConfiguration + @ComponentScan
// Responsibility : Start Application -> Configure Everything -> Search for Components

// Spring sees this class and says
//
//"Aha! This is the main Spring Boot application."

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {

//				main()
//		↓
//				SpringApplication.run()
//		↓
//				Starts Server
//		↓
//				Loads Spring
//		↓
//				Reads Configuration
//		↓
//				Creates Objects
//		↓
//				Starts Tomcat
//		↓
//				Waits Forever

//		Boots Spring, starts the embedded Tomcat server, and keeps the application running
		SpringApplication.run(
				ExpenseTrackerApiApplication.class, args);
	}

}
