package edu.sibinfo.spring.web.module05;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import edu.sibinfo.spring.web.module05.domain.Client;
import edu.sibinfo.spring.web.module05.dto.ClientDTO;

public class MyPermEval implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		if(authentication.getName().equals("user") && targetType.contains("Client")) {
			if(targetId instanceof Long && ((Long)targetId) % 2 == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if(authentication.getName().equals("user") && targetDomainObject instanceof ClientDTO) {
			if(((ClientDTO)targetDomainObject).getId() % 2 == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
}
