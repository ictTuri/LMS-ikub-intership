package com.project.lms.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.entity.UserEntity;
import com.project.lms.utils.UserUtil;

@SpringBootTest
@Transactional
class UserRepositoryTest {

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
		UserEntity user = UserUtil.userTest();
		userRepository.saveUser(user);
		user.setFirstName("testUpdate");
		
		userRepository.updateUser(user);
		
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
		String username = "test";
		
		UserEntity user = userRepository.getUserByUsername(username);
		
		Assertions.assertNull(user);
	}
	
	
	@Test
	void givenUser_whenSoftDelete_thenGetNoResult() {
		UserEntity user = UserUtil.userAdmin();
		userRepository.saveUser(user);

		user.setActivated(Boolean.FALSE);
		userRepository.updateUser(user);
		
		Assertions.assertNull(userRepository.getActivatedUserById(2));
	}

}
