package com.project.lms.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.repository.sql.RoleRepositoryImpl;
import com.project.lms.repository.sql.UserRepositoryImpl;
import com.project.lms.repository.sql.UserRoleRepositoryImpl;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
@Transactional
@ActiveProfiles("sql")
class UserRoleRepositoryTest {
	
	@Autowired
	private UserRoleRepositoryImpl userRoleRepo;
	
	@Autowired
	private RoleRepositoryImpl roleRepo;
	
	@Autowired
	private UserRepositoryImpl userRepo;
	
	@Test
	void givenUserRole_whenRetrievedByUser_thenCheckUserRetrieved() {
		UserEntity user = UserUtil.userAdmin();
		user.setEmail("email@test.com");
		user.setUsername("userTestAdminOne");
		RoleEntity role = RoleUtil.adminRole();
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUser(user);
		userRole.setRole(role);
		userRepo.saveUser(user);
		roleRepo.saveRole(role);
		
		userRoleRepo.saveUserRole(userRole);
		
		List<UserRoleEntity> userRoleRetrieved = userRoleRepo.getThisUserRelations(user);
		
		Assertions.assertEquals(user.getUsername(), userRoleRetrieved.get(0).getUser().getUsername());
		Assertions.assertEquals(role.getName(), userRoleRetrieved.get(0).getRole().getName());
	}

	@Test
	void givenUserRole_whenUpdate_thenGetUpdatedUserRole() {
		UserEntity user = UserUtil.userAdmin();
		user.setUsername("admintTestthree");
		user.setEmail("testEmail@gmail.com");
		RoleEntity roleStudent = RoleUtil.studentRole();
		RoleEntity roleAdmin = RoleUtil.adminRole();
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUser(user);
		userRole.setRole(roleAdmin);
		userRepo.saveUser(user);
		roleRepo.saveRole(roleAdmin);
		
		userRoleRepo.saveUserRole(userRole);
		roleRepo.saveRole(roleStudent);
		userRole.setRole(roleStudent);
		userRoleRepo.updateUserRole(userRole);
		
		List<UserRoleEntity> userRoleRetrieved = userRoleRepo.getThisUserRelations(user);
		
		Assertions.assertEquals(user.getUsername(), userRoleRetrieved.get(0).getUser().getUsername());
		Assertions.assertEquals(roleStudent.getName(), userRoleRetrieved.get(0).getRole().getName());
		
	}
}
