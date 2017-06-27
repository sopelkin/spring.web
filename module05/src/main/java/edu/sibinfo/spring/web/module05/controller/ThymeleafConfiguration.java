package edu.sibinfo.spring.web.module05.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
public class ThymeleafConfiguration {

  // adds 'temporals' to the thymeleaf page context
  @Bean
  public Java8TimeDialect java8TimeDialect() {
    return new Java8TimeDialect();
  }

  // add sec namespace to thymeleaf
  @Bean
  public SpringSecurityDialect springSecurityDialect() {
    return new SpringSecurityDialect();
  }
}
