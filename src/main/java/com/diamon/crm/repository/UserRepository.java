package com.diamon.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.diamon.crm.model.AppUser;
import com.diamon.crm.model.AppUserImpl;

public interface UserRepository
	extends CrudRepository<AppUserImpl, Long>{

	default AppUser insertUser(String userName, String password, List<String> roles) {
		return save(new AppUserImpl(userName, password, roles));
	}

	Optional<AppUser> findByUserName(String userName);

	default AppUser setRoles(AppUser user, List<String> roles) {
		return save(((AppUserImpl)user).setRoles(roles));
	}
}
