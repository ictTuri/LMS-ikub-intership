package com.project.lms.controller;

import static org.junit.jupiter.api.Assertions.*;
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

import com.project.lms.converter.RoleConverter;
import com.project.lms.converter.UserConverter;
import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.service.UserRoleService;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;

@ExtendWith(MockitoExtension.class)
class UserRoleControllerTest {

	@InjectMocks
	UserRoleController userRoleController;
	
	@Mock
	UserRoleService userRoleService;
	
	@BeforeEach
	void setup() {
		userRoleController = new UserRoleController(userRoleService);
	}
	
	@Test
	void givenUserRoleList_WhenGetList_checkValidateList() {
		List<UserRoleDto> userRoles = new ArrayList<>();
		UserRoleDto userRoleOne = new UserRoleDto();
		UserRoleDto userRoleTwo = new UserRoleDto();
		userRoleOne.setRole(RoleConverter.toDto(RoleUtil.studentRole()));
		userRoleOne.setUser(UserConverter.toDto(UserUtil.userTest()));
		userRoleTwo.setRole(RoleConverter.toDto(RoleUtil.adminRole()));
		userRoleTwo.setUser(UserConverter.toDto(UserUtil.userAdmin()));
		userRoles.add(userRoleOne);
		userRoles.add(userRoleTwo);
		
		Mockito.when(userRoleService.getAllUserRole()).thenReturn(userRoles);

		ResponseEntity<List<UserRoleDto>> allUserRoles = userRoleController.showUserRolesData();
		
		assertEquals(HttpStatus.OK, allUserRoles.getStatusCode());
		assertNotNull(allUserRoles);
		assertEquals(!userRoles.isEmpty(),allUserRoles.hasBody());
		verify(userRoleService).getAllUserRole();
	}
	
	@Test
	void givenUserRole_WhenUpdate_throwException() {
		long id = 4;
		UserRoleCreateUpdateDto userRole = new UserRoleCreateUpdateDto();
		userRole.setRole("Admin");
		userRole.setUsername("admin");
		
		Mockito.when(userRoleService.updateUserRole(id, userRole)).thenThrow(new ObjectIdNotFound("Id not found"));
		
		assertThrows(ObjectIdNotFound.class, ()->{userRoleController.updateUserRole(id, userRole);});
		verify(userRoleService).updateUserRole(id, userRole);
	}

}
