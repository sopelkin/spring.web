package edu.sibinfo.spring.web.module05.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@Configuration
@EntityScan(basePackageClasses = { 
		EntityConfiguration.class, // domain objects from this package
		Jsr310JpaConverters.class  // converters for Java 8 date&time classes
})
public class EntityConfiguration {

}
