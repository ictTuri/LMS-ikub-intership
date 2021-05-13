package com.project.lms.converter;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.dto.UserRoleDto;
import com.project.lms.entity.UserRoleEntity;

public class UserRoleConverter {

	private UserRoleConverter() {}
	
	public static UserRoleDto toDto(UserRoleEntity userRole) {
		UserRoleDto userRoleToReturn = new UserRoleDto();
		userRoleToReturn.setId(userRole.getId());
		userRoleToReturn.setUser(UserConverter.toDto(userRole.getUser()));
		userRoleToReturn.setRole(RoleConverter.toDto(userRole.getRole()));
		return userRoleToReturn;
	}
	

	public static List<UserRoleDto> toDtoList(List<UserRoleEntity> userRoles) {
		List<UserRoleDto> userRoleToReturn = new ArrayList<>();
		for(UserRoleEntity ure : userRoles) {
			userRoleToReturn.add(toDto(ure));
		}
		return userRoleToReturn;
	}
}
