package com.project.lms.service;

import java.util.List;

import com.project.lms.dto.RoleCreateUpdateDto;
import com.project.lms.dto.RoleDto;

public interface RoleService {

	List<RoleDto> showAllRoles();

	RoleDto createNewRole(RoleCreateUpdateDto role);

}
