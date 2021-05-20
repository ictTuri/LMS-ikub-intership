package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.impl.UserRoleServiceImpl;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class UserRoleServiceTest {


	@InjectMocks
	private UserRoleServiceImpl userRoleService;
	
	@Mock
	private UserRoleRepository userRoleRepository;
	
	
	@Test
	void givenUserRoleList_whenGetThem_validateSize() {
		List<UserRoleEntity> userRoles = new ArrayList<>();
		UserRoleEntity userRoleOne = new UserRoleEntity();
		userRoleOne.setRole(RoleUtil.adminRole());
		userRoleOne.setUser(UserUtil.userAdmin());
		userRoles.add(userRoleOne);

		when(userRoleRepository.getAllUserRoles()).thenReturn(userRoles);
		int size = userRoleService.getAllUserRole().size();
		
		assertEquals(1, size);
		verify(userRoleRepository).getAllUserRoles();
	}
	
	@Test
	void givenUserRole_whenUpdate_thenThrowsException() {
		UserRoleCreateUpdateDto userRoleToUpdate = new UserRoleCreateUpdateDto();
		userRoleToUpdate.setRole("ADMIN");
		userRoleToUpdate.setUsername("admin");
		
		UserRoleEntity userRoleOne = new UserRoleEntity();
		userRoleOne.setRole(RoleUtil.adminRole());
		userRoleOne.setUser(UserUtil.userAdmin());
		long id = 1;
		
		Mockito.when(userRoleRepository.updateUserRole(userRoleOne)).thenReturn(userRoleOne);
		
		assertThrows(ObjectIdNotFound.class, ()->{userRoleService.updateUserRole(id, userRoleToUpdate);});
	}
	
	@Test
	void givenUserRole_whenGet_thenValidateUserAndRole() {
		UserRoleEntity userRoleOne = new UserRoleEntity();
		userRoleOne.setRole(RoleUtil.adminRole());
		userRoleOne.setUser(UserUtil.userAdmin());
		long id = 1;
		
		Mockito.when(userRoleRepository.getUserRoleById(id)).thenReturn(userRoleOne);
		UserRoleDto userRoleToGet = userRoleService.getUserRoleById(id);
		
		assertNotNull(userRoleToGet);
		assertEquals(userRoleToGet.getRole().getName(), userRoleOne.getRole().getName());
		assertEquals(userRoleToGet.getUser().getUsername(), userRoleOne.getUser().getUsername());
	}
	
}
