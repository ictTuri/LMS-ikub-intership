package com.project.lms.converter;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.dto.RoleCreateUpdateDto;
import com.project.lms.dto.RoleDto;
import com.project.lms.model.RoleEntity;

public class RoleConverter {

private RoleConverter() {}
	
	public static RoleDto toDto(RoleEntity role) {
		var roleToReturn = new RoleDto();
		roleToReturn.setId(role.getId());
		roleToReturn.setName(role.getName());
		return roleToReturn;
	}
	
	public static RoleEntity toEntity(RoleCreateUpdateDto role) {
		var roleToReturn = new RoleEntity();
		roleToReturn.setName(role.getName());
		return roleToReturn;
	}
	
	public static List<RoleDto> toListDto(List<RoleEntity> roles) {
		List<RoleDto> rolesToReturn = new ArrayList<>();
		for(RoleEntity re : roles) {
			rolesToReturn.add(toDto(re));
		}
		return rolesToReturn;
	}
	
}
