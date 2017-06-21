package edu.sibinfo.spring.web.module05.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.sibinfo.spring.web.module05.dao.PhoneType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Phone {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="phone_number")
	private String number;

	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;

	@Setter(value=AccessLevel.PACKAGE)
	@Column(columnDefinition="DATE") // force DATE type in DB
	private LocalDateTime added;
	
	public Phone(String number, PhoneType phoneType) {
		super();
		this.number = number;
		this.phoneType = phoneType;
		this.added = LocalDateTime.now();
	}
}
