package com.project.lms.repository.mongo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.model.UserEntity;
import com.project.lms.repository.UserRepository;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class UserRepositoryMongoTest {
	@Autowired
	private UserRepository userRepository;

	
	@Test
	void givenUsername_whenRetrieved_thenGetUserData() {
		UserEntity user = UserUtil.userTest();
		userRepository.saveUser(user);
		String username = "test";
		
		UserEntity userRetrieved = userRepository.getUserByUsername(username);
		
		Assertions.assertEquals(username, userRetrieved.getUsername());
	}
	
	@Test
	void givenUser_whenUpdate_thenGetUpdatedUser() {
		UserEntity userToUpdate = userRepository.getUserByUsername("test");
		userToUpdate.setFirstName("testUpdate");
		
		userRepository.updateUser(userToUpdate);
		
		Assertions.assertEquals("testUpdate", userRepository.getUserByUsername("test").getFirstName());
	}
	
	@Test
	void givenUser_whenSave_thenGetCreatedUser() {
		Integer userSize = userRepository.getAllUsers().size();
		UserEntity user = UserUtil.userAdmin();

		userRepository.saveUser(user);
		
		Assertions.assertEquals(userSize+1, userRepository.getAllUsers().size());
		Assertions.assertNotNull(userRepository.getUserByUsername("admin"));
	}
	
	@Test
	void givenWrongUsername_whenRetrieved_thenGetNoResult() {
		String username = "usernameNonExistent";
		
		UserEntity user = userRepository.getUserByUsername(username);
		
		Assertions.assertNull(user);
	}
	
	
	@Test
	void givenUser_whenSoftDelete_thenGetNoResult() {
		UserEntity userToSoftDelete = userRepository.getUserByUsername("admin");

		userToSoftDelete.setActivated(Boolean.FALSE);
		userRepository.updateUser(userToSoftDelete);
		
		Assertions.assertNull(userRepository.getActivatedUserByUsername("admin"));
	}
}
