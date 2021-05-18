package com.project.lms.utils;

import com.project.lms.model.UserEntity;

public class UserUtil {
	public static UserEntity userTest() {
		UserEntity user = new UserEntity();
		user.setId(null);
		user.setFirstName("test");
		user.setLastName("test");
		user.setEmail("test@gmail.com");
		user.setUsername("test");
		user.setPassword("$2a$10$OpdoAtzBqkQnJgEStCvXkelzh9wzqvPeapEFhyL2dVvC0awCPYxoy");
		user.setActivated(true);
		user.setUserRoles(null);
		return user;
	}
	
	public static UserEntity userAdmin() {
		UserEntity user = new UserEntity();
		user.setId(null);
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setEmail("admin@gmail.com");
		user.setPassword("$2a$10$IZNCfsR8Mw14kdA1TFGlD.P./VoEPo7k7unJ0rePZPPE4PBVwfGHy");
		user.setUsername("admin");
		user.setActivated(true);
		user.setUserRoles(null);
		return user;
	}
}
