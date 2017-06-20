package edu.sibinfo.spring.web.module05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application05 {

	public static void main(String[] args) {
		SpringApplication.run(Application05.class, args);
	}
}
