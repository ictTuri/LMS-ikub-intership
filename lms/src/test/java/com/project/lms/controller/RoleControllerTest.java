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
import com.project.lms.dto.RoleCreateUpdateDto;
import com.project.lms.dto.RoleDto;
import com.project.lms.service.RoleService;
import com.project.lms.utils.RoleUtil;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

	@InjectMocks
	RoleController roleController;
	
	@Mock
	RoleService roleService;
	
	@BeforeEach
	void setup() {
		roleController = new RoleController(roleService);
	}
	
	@Test
	void givenRoleList_WhenGetList_checkValidateList() {
		List<RoleDto> roleList = new ArrayList<>();
		roleList.add(RoleConverter.toDto(RoleUtil.adminRole()));
		roleList.add(RoleConverter.toDto(RoleUtil.secretaryRole()));
		roleList.add(RoleConverter.toDto(RoleUtil.studentRole()));
		
		Mockito.when(roleService.showAllRoles()).thenReturn(roleList);

		ResponseEntity<List<RoleDto>> allRoles = roleController.showAllRoles();
		
		assertEquals(HttpStatus.OK, allRoles.getStatusCode());
		assertNotNull(allRoles);
		verify(roleService).showAllRoles();
	}

	@Test
	void givenNewRole_whenSave_thenVerifySave() {
		RoleCreateUpdateDto roleDto = new RoleCreateUpdateDto();
		roleDto.setName("ADMIN");
		RoleDto dto = RoleConverter.toDto(RoleUtil.adminRole());

		Mockito.when(roleService.createNewRole(roleDto)).thenReturn(dto);

		ResponseEntity<?> createdRole = roleController.createNewRole(roleDto);

		assertNotNull(createdRole);
		assertEquals(HttpStatus.CREATED, createdRole.getStatusCode());
	}
	
}
