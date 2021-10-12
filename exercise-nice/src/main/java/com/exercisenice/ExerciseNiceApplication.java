package com.exercisenice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;


@SpringBootApplication
@EnableRetry
public class ExerciseNiceApplication {
//	public static Logger logger = Logger.getLogger("documentLogger");

	public static void main(String[] args) {
		SpringApplication.run(ExerciseNiceApplication.class, args);
	}
}
