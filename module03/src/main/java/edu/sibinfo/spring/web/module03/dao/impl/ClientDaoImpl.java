package edu.sibinfo.spring.web.module03.dao.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import edu.sibinfo.spring.web.module03.dao.ClientDaoCustom;
import edu.sibinfo.spring.web.module03.domain.Client;
import edu.sibinfo.spring.web.module03.domain.QClient;

public class ClientDaoImpl implements ClientDaoCustom {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Iterable<Client> search(String charactersitics) {
		QClient client = QClient.client;
		String[] chs = charactersitics.split("\\s+");
		BooleanExpression predicate = null;
		String familyName = chs[0];
		if (Character.isUpperCase(familyName.charAt(0))) {
			predicate = client.familyName.startsWith(familyName);
		} else {
			predicate = client.familyName.contains(familyName);
		}
		if (chs.length > 1) {
			String firstName = chs[1];
			if (Character.isUpperCase(firstName.charAt(0))) {
				predicate = predicate.and(client.firstName.startsWith(firstName));
			} else {
				predicate = predicate.and(client.firstName.contains(firstName));
			}
		}
		return new JPAQuery<Client>(entityManager).from(client).where(predicate).fetch();
	}
}
