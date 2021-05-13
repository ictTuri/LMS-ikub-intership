package com.project.lms.repository.impl;

import com.project.lms.entity.UserEntity;

public class UserUtil {
	public static UserEntity createUser() {
		UserEntity user = new UserEntity();
		user.setFirstName("test");
		user.setLastName("test");
		user.setEmail("test@gmail.com");
		user.setPassword("test");
		user.setUsername("test");
		user.setActivated(true);
		return user;
	}
	
	public static UserEntity createUserAdmin() {
		UserEntity user = new UserEntity();
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setEmail("admin@gmail.com");
		user.setPassword("admin");
		user.setUsername("admin");
		user.setActivated(true);
		return user;
	}
}
