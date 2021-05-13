package com.project.lms.service;

import java.util.List;

import javax.validation.Valid;

import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;

public interface UserRoleService {

	List<UserRoleDto> getAllUserRole();

	UserRoleDto getUserRoleById(Long id);

	UserRoleDto createUserRole(UserRoleCreateUpdateDto userRole);

	UserRoleDto updateUserRole(long id,@Valid UserRoleCreateUpdateDto userRole);

	void deleteUserRole(long id);

}
