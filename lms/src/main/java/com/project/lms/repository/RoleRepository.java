package com.project.lms.repository;

import java.util.List;

import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;

public interface RoleRepository{

	RoleEntity getRoleById(Long id);

	RoleEntity getRole(String name);

	List<RoleEntity> getUserRole(UserEntity user);

	void saveRole(RoleEntity role);

}
