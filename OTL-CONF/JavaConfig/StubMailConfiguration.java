package ru.otlnal.onlineloans.system.config;

import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring4.SpringTemplateEngine;
import ru.otlnal.onlineloans.system.mail.MailService;

@Configuration
public class StubMailConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    @Primary
    public MailService mailService(
        JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender, MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        return new StubMailService(jHipsterProperties, javaMailSender, messageSource, templateEngine);
    }

    public static class StubMailService extends MailService {

        private final Logger log = LoggerFactory.getLogger(MailService.class);

        public StubMailService(
            JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender, MessageSource messageSource,
            SpringTemplateEngine templateEngine
        ) {
            super(jHipsterProperties, javaMailSender, messageSource, templateEngine);
        }

        @Override
        public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
            log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);
        }
    }
}
