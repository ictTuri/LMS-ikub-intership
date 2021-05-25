package com.project.lms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.lms.converter.UserConverter;
import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserDto;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.UserEntity;
import com.project.lms.service.UserService;
import com.project.lms.utils.UserUtil;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;
	
	@BeforeEach
	void setup() {
		userController = new UserController(userService);
	}
	
	@Test
	void givenUserList_WhenRetrieved_verifyListSize() {
		List<UserDto> users = new ArrayList<>();
		UserEntity userOne = UserUtil.userAdmin();
		UserEntity userTwo = UserUtil.userTest();
		users.add(UserConverter.toDto(userOne));
		users.add(UserConverter.toDto(userTwo));
		
		Mockito.when(userService.getAllUsers()).thenReturn(users);
		ResponseEntity<List<UserDto>> allUsers = userController.showAllUsers();
		
		assertEquals(HttpStatus.OK, allUsers.getStatusCode());
		assertEquals(users.size(),allUsers.getBody().size());
		assertNotNull(allUsers);
		assertEquals(!users.isEmpty(),allUsers.hasBody());
		verify(userService).getAllUsers();
	}
	
	@Test
	void givenUser_WhenSave_getUserDto() {
		UserEntity userOne = UserUtil.userAdmin();
		UserCreateUpdateDto userToSave = new UserCreateUpdateDto();
		userToSave.setFirstName(userOne.getFirstName());
		userToSave.setLastName(userOne.getLastName());
		userToSave.setEmail(userOne.getEmail());
		userToSave.setUsername(userOne.getUsername());
		userToSave.setActivated(userOne.isActivated());
		userToSave.setRole("Admin");
		userToSave.setPassword(userOne.getPassword());
		UserDto userToReturn= UserConverter.toDto(userOne); 
		
		Mockito.when(userService.createUser(userToSave)).thenReturn(userToReturn);
		ResponseEntity<UserDto> newUser = userController.createUser(userToSave);
		
		assertEquals(HttpStatus.CREATED, newUser.getStatusCode());
		assertEquals(newUser.getBody().getUsername(),userToSave.getUsername());
		assertNotNull(newUser);
		verify(userService).createUser(userToSave);
	}
	
	@Test
	void givenUser_WhenUpdate_throwsException() {
		long id = 4;
		UserEntity userOne = UserUtil.userAdmin();
		UserCreateUpdateDto userToSave = new UserCreateUpdateDto();
		userToSave.setFirstName(userOne.getFirstName());
		userToSave.setLastName(userOne.getLastName());
		userToSave.setEmail(userOne.getEmail());
		userToSave.setUsername(userOne.getUsername());
		userToSave.setActivated(userOne.isActivated());
		userToSave.setRole("Admin");
		userToSave.setPassword(userOne.getPassword());
		
		Mockito.when(userService.updateUser(id, userToSave)).thenThrow(new ObjectIdNotFound("Can not find user with given Id: " + id));

		assertThrows(ObjectIdNotFound.class, ()->{userController.updateUser(id, userToSave);});
		verify(userService).updateUser(id, userToSave);
	}
	
	@Test
	void givenId_WhenRequestUser_GetOK() {
		long id = 1;
		UserDto user = UserConverter.toDto(UserUtil.userAdmin());
		
		Mockito.when(userService.getUserById(id)).thenReturn(user);
		
		ResponseEntity<UserDto> userById = userController.showUserById(id);

		assertNotNull(userById);
		assertEquals(HttpStatus.OK, userById.getStatusCode());
		verify(userService).getUserById(id);
	}
	
	@Test
	void givenUser_WhenUpdate_GetOK() {
		long id = 1;
		UserCreateUpdateDto userToUpdate = new UserCreateUpdateDto();
		userToUpdate.setUsername("test");
		UserDto user = UserConverter.toDto(UserUtil.userAdmin());
		
		Mockito.when(userService.updateUser(id,userToUpdate)).thenReturn(user);
		
		ResponseEntity<UserDto> userUpdated = userController.updateUser(id, userToUpdate);

		assertNotNull(userUpdated);
		assertEquals(HttpStatus.OK, userUpdated.getStatusCode());
		verify(userService).updateUser(id,userToUpdate);
	}
	
	@Test
	void givenUser_WhenSoftDelete_GetNoContent() {
		long id = 1;
		
		Mockito.doNothing().when(userService).softDeleteUser(id);
		
		ResponseEntity<Void> userSoftDeleted = userController.softDeleteUser(id);

		assertNotNull(userSoftDeleted);
		assertEquals(HttpStatus.NO_CONTENT, userSoftDeleted.getStatusCode());
		verify(userService).softDeleteUser(id);
	}
	
	@Test
	void givenUser_WhenHardDelete_GetNoContent() {
		long id = 1;
		
		Mockito.doNothing().when(userService).hardDeleteUser(id);
		
		ResponseEntity<Void> userHardDeleted = userController.hardDeleteUser(id);

		assertNotNull(userHardDeleted);
		assertEquals(HttpStatus.NO_CONTENT, userHardDeleted.getStatusCode());
		verify(userService).hardDeleteUser(id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
