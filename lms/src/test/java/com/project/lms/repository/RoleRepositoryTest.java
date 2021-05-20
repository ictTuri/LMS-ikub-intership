package com.project.lms.repository;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.model.RoleEntity;
import com.project.lms.utils.RoleUtil;

@SpringBootTest
@Transactional
class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	void givenRole_whenRetrieved_thenGetRoleData() {
		RoleEntity roleOne = RoleUtil.adminRole();
		// Role name from Util is "ADMIN"
		
		roleRepository.saveRole(roleOne);
		String role = "ADMIN";
		
		RoleEntity roleRetrieved = roleRepository.getRole(role);
		
		Assertions.assertEquals(role, roleRetrieved.getName());
	}
	
	@Test
	void givenRole_whenSave_thenGetCreatedRole() {
		Integer roleSize = roleRepository.getAllRoles().size();
		RoleEntity roleOne = RoleUtil.secretaryRole();
		RoleEntity roleTwo = RoleUtil.studentRole();

		roleRepository.saveRole(roleOne);
		roleRepository.saveRole(roleTwo);
		
		Assertions.assertEquals(roleSize+2, roleRepository.getAllRoles().size());
		Assertions.assertNotNull(roleRepository.getRole("SECRETARY"));
		Assertions.assertNotNull(roleRepository.getRole("STUDENT"));
		assertNull(roleRepository.getRole("NOTAROLE"));
	}
	
	@Test
	void givenWrongName_whenRetrieved_thenGetNoResult() {
		String role = "DIRECTOR";
		
		RoleEntity roleRetrieved = roleRepository.getRole(role);
		
		Assertions.assertNull(roleRetrieved);
	}


}
