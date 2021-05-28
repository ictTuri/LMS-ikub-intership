package com.project.lms.repository.mongo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.utils.RoleUtil;

@SpringBootTest
@ActiveProfiles("mongo")
class RoleRepositoryMongoTest {

	@Autowired
	private RoleRepositoryMongo roleRepository;
	
	@Autowired
	private UserRepositoryMongo userRepository;
	
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
		Assertions.assertNotNull(roleRepository.getRole("ADMIN"));
		Assertions.assertNotNull(roleRepository.getRole("SECRETARY"));
		Assertions.assertNotNull(roleRepository.getRole("STUDENT"));
		assertNull(roleRepository.getRole("NOTAROLE"));
	}
	
	@Test
	void givenWrongName_whenRetrieved_thenGetNoResult() {
		String role = "DIRECTOR";
		
		RoleEntity roleRetrieved = roleRepository.getRole(role);
		RoleEntity roleRetrievedOne = roleRepository.getRole("ADMIN");
		
		assertNotNull(roleRetrievedOne);
		Assertions.assertNull(roleRetrieved);
	}
	
	@Test
	void givenId_whenRetrieved_thenGetRoleEntity() {
		long id = 1;
		
		RoleEntity role = roleRepository.getRoleById(id);
		
		assertNotNull(role);
		assertEquals(role.getClass().getTypeName(), RoleEntity.class.getTypeName());
	}
	
	@Test
	void givenUser_whenRetrieved_thenGetAllRoles() {
		long id = 1;
		UserEntity user = userRepository.getUserById(id);
		
		List<RoleEntity> myRoles = roleRepository.getUserRole(user);
		
		assertNotNull(myRoles);
	}

}
