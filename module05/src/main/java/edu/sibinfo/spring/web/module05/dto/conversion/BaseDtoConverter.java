package edu.sibinfo.spring.web.module05.dto.conversion;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;

public abstract class BaseDtoConverter<S, T> implements Converter<S, T> {

	@Autowired
	protected ConverterRegistry registry;
	@Autowired
	protected ConversionService service;

	@PostConstruct
	public void register() {
		registry.addConverter(this);
	}

}
