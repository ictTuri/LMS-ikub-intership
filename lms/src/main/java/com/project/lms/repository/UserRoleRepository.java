package com.project.lms.repository;

import java.util.List;

import com.project.lms.entity.UserRoleEntity;

public interface UserRoleRepository{

	void saveUserRole(UserRoleEntity userRole);

	List<UserRoleEntity> getAllUserRoles();

	UserRoleEntity getUserRoleById(Long id);

	void deleteUserRole(UserRoleEntity userRoleToDelete);

	UserRoleEntity updateUserRole(UserRoleEntity userRoleToUpdate);

}
