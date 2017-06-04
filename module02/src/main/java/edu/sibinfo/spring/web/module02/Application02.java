package edu.sibinfo.spring.web.module02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application02 {

	public static void main(String[] args) {
		SpringApplication.run(Application02.class, args);
	}
}
