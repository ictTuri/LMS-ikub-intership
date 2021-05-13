package com.project.lms.dto;

import lombok.Data;

@Data
public class UserRoleDto {

	private Long id;
	private UserDto user;
	private RoleDto role;
}
