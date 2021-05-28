package com.project.lms.repository.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.lms.model.UserEntity;
import com.project.lms.utils.UserUtil;

@SpringBootTest
@Transactional
@ActiveProfiles("sql")
class UserRepositoryTest {

	@Autowired
	private UserRepositoryImpl userRepository;
	
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
		
		UserEntity userToUpdate = userRepository.getUserByUsername(user.getUsername());
		
		userToUpdate.setFirstName("testUpdate");
		
		userRepository.updateUser(userToUpdate);
		
		Assertions.assertEquals("testUpdate", userRepository.getUserByUsername("test").getFirstName());
	}
	
	@Test
	void givenUser_whenSave_thenGetCreatedUser() {
		Integer userSize = userRepository.getAllUsers().size();
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest@gmail.com");
		user.setUsername("adminTest");
		userRepository.saveUser(user);
		
		Assertions.assertEquals(userSize+1, userRepository.getAllUsers().size());
		Assertions.assertNotNull(userRepository.getUserByUsername("adminTest"));
	}
	
	@Test
	void givenWrongUsername_whenRetrieved_thenGetNoResult() {
		String username = "notAUser";
		
		UserEntity user = userRepository.getUserByUsername(username);
		
		Assertions.assertNull(user);
	}
	
	
	@Test
	void givenUser_whenSoftDelete_thenGetNoResult() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest2@gmail.com");
		user.setUsername("adminTestUser");
		userRepository.saveUser(user);

		user.setActivated(Boolean.FALSE);
		userRepository.updateUser(user);
		
		Assertions.assertNull(userRepository.getActivatedUserByUsername("adminTestUser"));
	}
	
	@Test
	void givenId_whenGetUser_thenReturnUser() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest3@gmail.com");
		user.setUsername("adminTestUserOne");
		user.setActivated(true);
		userRepository.saveUser(user);
		UserEntity userByUsername = userRepository.getUserByUsername("adminTestUserOne");
		UserEntity userById = userRepository.getUserById(userByUsername.getId());
		
		assertEquals(userByUsername, userById);
		Assertions.assertNotNull(userRepository);
		
		UserEntity userByIdInvalid = userRepository.getUserById(Long.valueOf(5784));
		assertNull(userByIdInvalid);
		
	}
	
	@Test
	void givenUsernameEmail_whenGetUser_thenCheck() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest4@gmail.com");
		user.setUsername("adminTestUserTwo");
		user.setActivated(true);
		userRepository.saveUser(user);
		boolean existUsernameEmail = userRepository.checkUserByUsernameEmail("adminTestUserTwo", "userTest4@gmail.com");
		
		assertTrue(existUsernameEmail);
		assertEquals(Boolean.TRUE, existUsernameEmail);
		
		boolean existNotUsernameEmail = userRepository.checkUserByUsernameEmail("adminTestTwothree", "userTest344@gmail.com");
		
		assertFalse(existNotUsernameEmail);
		assertEquals(Boolean.FALSE, existNotUsernameEmail);
	}
	
	@Test
	void givenUsername_whenCheck_thenVerify() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest5@gmail.com");
		user.setUsername("adminTestUserThree");
		user.setActivated(true);
		userRepository.saveUser(user);
		
		boolean existUsername = userRepository.existUsername(user.getUsername());
		assertTrue(existUsername);
	}
	
	@Test
	void givenEmail_whenCheck_thenVerify() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest6@gmail.com");
		user.setUsername("adminTestUserFour");
		user.setActivated(true);
		userRepository.saveUser(user);
		
		boolean existUsername = userRepository.existEmail(user.getEmail());
		assertTrue(existUsername);
	}
	
	@Test
	void givenId_whenGetActivatedUser_thenVerify() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest7@gmail.com");
		user.setUsername("adminTestUserFive");
		user.setActivated(true);
		userRepository.saveUser(user);
		UserEntity userRetrieved = userRepository.getUserByUsername(user.getUsername());
		
		UserEntity userActivatedById = userRepository.getActivatedUserById(userRetrieved.getId());
		
		assertNotNull(userActivatedById);
		assertEquals("adminTestUserFive", userActivatedById.getUsername());
		
		UserEntity wrongActivatedById = userRepository.getActivatedUserById(Long.valueOf(3455));
		assertNull(wrongActivatedById);
	}
	
	@Test
	void givenUser_whenDelete_thenCheck() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("userTest6@gmail.com");
		user.setUsername("adminTestUsersix");
		user.setActivated(true);
		
		userRepository.saveUser(user);
		UserEntity userToDelete = userRepository.getUserByUsername("adminTestUsersix");
		userRepository.deleteUser(userToDelete);
		UserEntity userAfterDelete = userRepository.getUserByUsername("adminTestUsersix");
		
		assertNull(userAfterDelete);
	}

}
