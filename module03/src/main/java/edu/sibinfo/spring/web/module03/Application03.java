package edu.sibinfo.spring.web.module03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application03 {

	public static void main(String[] args) {
		SpringApplication.run(Application03.class, args);
	}
}
