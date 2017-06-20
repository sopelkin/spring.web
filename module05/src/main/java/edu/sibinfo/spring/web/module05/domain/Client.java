package edu.sibinfo.spring.web.module05.domain;

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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
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
	@Setter(value=AccessLevel.NONE)
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

	public List<Phone> getPhones() {
		return Collections.unmodifiableList(phones);
	}

	public void addPhone(Phone phone) {
		this.phones.add(phone);
	}
}
