package edu.sibinfo.spring.web.module02.domain;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String familyName;
	private String firstName;
	private byte[] passwordEncoded;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="client_ID")
	private List<Phone> phones;

	public Client() {
		super();
	}

	public Client(String familyName, String firstName) {
		super();
		this.familyName = familyName;
		this.firstName = firstName;
		this.phones = new ArrayList<Phone>(1);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public List<Phone> getPhones() {
		return Collections.unmodifiableList(phones);
	}

	public void addPhone(Phone phone) {
		this.phones.add(phone);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [familyName=").append(familyName).append(", firstName=").append(firstName);
		if (passwordEncoded != null)
			builder.append(", password=[").append(new String(passwordEncoded, StandardCharsets.US_ASCII)).append(']');
		builder.append(", phones: ").append(phones);
		builder.append("]");
		return builder.toString();
	}

	public void setPassword(byte[] passwordEncoded) {
		this.passwordEncoded = passwordEncoded;
	}
}
