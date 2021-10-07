package com.exercisenice;

import com.exercisenice.loggers.DocumentLogger;
import com.exercisenice.models.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.Proxy;
import java.util.logging.*;

@SpringBootApplication
@EnableRetry
public class ExerciseNiceApplication {
//	public static Logger logger = Logger.getLogger("documentLogger");

	public static void main(String[] args) {
		SpringApplication.run(ExerciseNiceApplication.class, args);
	}
}
