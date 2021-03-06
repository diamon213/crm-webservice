package com.diamon.crm.controller;

import java.util.List;

import com.diamon.crm.model.AppUser;

public class UserDto {

	public Long id;
	public final String userName;
	public List<String> roles;

	public UserDto(AppUser user) {
		this.id = user.getId();
		this.userName = user.getUsername();
		this.roles = user.getRoles();
	}

}
