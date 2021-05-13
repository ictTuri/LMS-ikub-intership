package com.project.lms.repository;

import java.util.List;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;

public interface RoleRepository{

	// Return role by id if found, else returns null
	RoleEntity getRoleById(int id);

	// Return role by name if found, else returns null
	RoleEntity getRole(String name);

	// Returns list of roles that belond to passed user variable
	List<RoleEntity> getUserRole(UserEntity user);

}
