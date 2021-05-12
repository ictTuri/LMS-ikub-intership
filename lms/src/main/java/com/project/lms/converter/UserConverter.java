package com.project.lms.converter;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserDto;
import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;

public class UserConverter {

	private UserConverter() {}
	
	public static UserDto toDto(UserEntity user) {
		List<String> roles = new ArrayList<>();
		for(UserRoleEntity ure: user.getUsersRoles()) {
			roles.add(ure.getRole().toString());
		}
		
		UserDto userToReturn = new UserDto();
		userToReturn.setId(user.getId());
		userToReturn.setFirstName(user.getFirstName());
		userToReturn.setLastName(user.getLastName());
		userToReturn.setEmail(user.getEmail());
		userToReturn.setRoles(roles);
		userToReturn.setActivated(user.isActivated());
		return userToReturn;
	}
	
	public static UserEntity toEntity(UserCreateUpdateDto user) {
		UserEntity userToReturn = new UserEntity();
		userToReturn.setId(null);
		userToReturn.setFirstName(user.getFirstName());
		userToReturn.setLastName(user.getLastName());
		userToReturn.setEmail(user.getEmail());
		userToReturn.setUsername(user.getUsername());
		userToReturn.setPassword(user.getPassword());
		return userToReturn;
	}
	
	public static List<UserDto> toListDto(List<UserEntity> users) {
		List<UserDto> usersToReturn = new ArrayList<>();
		for(UserEntity be : users) {
			usersToReturn.add(toDto(be));
		}
		return usersToReturn;
	}
	
}
