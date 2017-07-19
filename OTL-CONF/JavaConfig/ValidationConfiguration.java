package ru.otlnal.onlineloans.system.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.otlnal.onlineloans.web.rest.reg.ValidationConstants;
import ru.otlnal.onlineloans.web.rest.reg.reg1.RegistrationStep1DTO;

@Configuration
public class ValidationConfiguration {

    @Bean(name = "middleNameValidator")
    Validator middleNameValidator() {

        return new Validator() {

            @Override
            public boolean supports(Class<?> clazz) {
                return RegistrationStep1DTO.class.isAssignableFrom(clazz);
            }

            @Override
            public void validate(Object target, Errors errors) {

                RegistrationStep1DTO dto = (RegistrationStep1DTO) target;

                String middleName = dto.getMiddleName();

                if (StringUtils.isNotBlank(middleName) && !middleName.matches(ValidationConstants.NAME_PATTERN)) {
                    errors.rejectValue("middleName", "Pattern", "Не подходит по шаблону");
                }
            }
        };
    }
}
