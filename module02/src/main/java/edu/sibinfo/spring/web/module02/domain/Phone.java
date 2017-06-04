package edu.sibinfo.spring.web.module02.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import edu.sibinfo.spring.web.module02.dao.PhoneType;

@Entity
public class Phone {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="phone_number")
	private String number;

	@Enumerated(EnumType.STRING)
	private PhoneType phoneType;
	
	// for Hibernate
	public Phone() {
	}

	public Phone(String number, PhoneType phoneType) {
		super();
		this.number = number;
		this.phoneType = phoneType;
	}

	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public PhoneType getPhoneType() {
		return phoneType;
	}

	@Override
	public String toString() {
		return number + "(" + phoneType.name().substring(0, 1) + ")";
	}
}
