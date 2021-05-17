package com.project.lms.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.utils.RoleUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
@Transactional
class UserRoleRepositoryTest {
	
	@Autowired
	private UserRoleRepository userRoleRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	void givenUserRole_whenRetrievedByUser_thenCheckUserRetrieved() {
		UserEntity user = UserUtil.userAdmin();
		RoleEntity role = RoleUtil.adminRole();
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setUser(user);
		userRole.setRole(role);
		userRepo.saveUser(user);
		roleRepo.saveRole(role);
		
		userRoleRepo.saveUserRole(userRole);
		
		List<UserRoleEntity> userRoleRetrieved = userRoleRepo.getThisUserRelations(user);
		
		Assertions.assertEquals(user, userRoleRetrieved.get(0).getUser());
		Assertions.assertEquals(role, userRoleRetrieved.get(0).getRole());
	}

	@Test
	void givenUserRole_whenUpdate_thenGetUpdatedUserRole() {
		UserEntity user = UserUtil.userAdmin();
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
		
		Assertions.assertEquals(user, userRoleRetrieved.get(0).getUser());
		Assertions.assertEquals(roleStudent, userRoleRetrieved.get(0).getRole());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
