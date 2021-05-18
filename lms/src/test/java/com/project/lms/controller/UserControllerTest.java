package com.project.lms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.project.lms.dto.UserDto;
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
	
	

}
