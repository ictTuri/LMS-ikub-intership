package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.dto.UserDto;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.impl.UserServiceImpl;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Test
	void givenUserList_whenGetThem_validateSize() {
		List<UserEntity> users = new ArrayList<>();
		users.add(UserUtil.userTest());
		users.add(UserUtil.userAdmin());

		when(userRepository.getAllUsers()).thenReturn(users);
		int size = userService.getAllUsers().size();
		
		assertEquals(2, size);
		verify(userRepository).getAllUsers();
	}
	
	@Test
	void givenId_whenGet_thenValidateUser() {
		UserEntity user = UserUtil.userAdmin();
		
		Mockito.when(userRepository.getUserById(Long.valueOf(1))).thenReturn(user);
		UserDto returned = userService.getUserById(Long.valueOf(1));
		
		assertNotNull(returned);
		assertEquals(returned.getUsername(), user.getUsername());
	}
	
	
}
