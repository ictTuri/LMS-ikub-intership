package com.project.lms.repository;

import java.util.List;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;

public interface RoleRepository{

	RoleEntity getRoleById(int id);

	RoleEntity getRole(String name);

	List<RoleEntity> getUserRole(UserEntity user);

	void saveRole(RoleEntity role);

}
