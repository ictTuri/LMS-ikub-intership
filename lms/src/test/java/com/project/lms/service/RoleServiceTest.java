package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.model.RoleEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.service.impl.RoleServiceImpl;
import com.project.lms.utils.RoleUtil;

@SpringBootTest
class RoleServiceTest {

	@InjectMocks
	private RoleServiceImpl roleService;

	@Mock
	private RoleRepository roleRepository;
	
	@Test
	void givenRoleList_whenGetThem_validateSize() {
		List<RoleEntity> roles = new ArrayList<>();
		roles.add(RoleUtil.adminRole());
		roles.add(RoleUtil.secretaryRole());
		roles.add(RoleUtil.studentRole());

		when(roleRepository.getAllRoles()).thenReturn(roles);
		int size = roleService.showAllRoles().size();
		assertEquals(3, size);
		verify(roleRepository).getAllRoles();
	}
}
