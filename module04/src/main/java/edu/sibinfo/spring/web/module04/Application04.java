package edu.sibinfo.spring.web.module04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application04 {

	public static void main(String[] args) {
		SpringApplication.run(Application04.class, args);
	}
}
