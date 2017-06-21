package edu.sibinfo.spring.web.module05.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Data;

@XmlRootElement(name="client")
@Data
public class ClientDTO {
	private Long id;
	private String lastName;
	private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime registrationMoment;
	private List<PhoneDTO> phones;
}
