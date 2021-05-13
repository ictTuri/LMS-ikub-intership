package com.project.lms.converter;

import java.util.ArrayList;
import java.util.List;

import com.project.lms.dto.UserRegisterDto;
import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserDto;
import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;

public class UserConverter {
	
	private UserConverter() {}
	
	public static UserDto toDto(UserEntity user) {
		UserDto userToReturn = new UserDto();
		userToReturn.setId(user.getId());
		userToReturn.setFirstName(user.getFirstName());
		userToReturn.setLastName(user.getLastName());
		userToReturn.setEmail(user.getEmail());
		userToReturn.setUsername(user.getUsername());
		userToReturn.setActivated(user.isActivated());
		return userToReturn;
	}
	
	public static UserDto toDtoWithRoles(UserEntity user, List<RoleEntity> roles) {
		List<String> rolesName = new ArrayList<>();
		for(RoleEntity re: roles) {
			rolesName.add(re.getName());
		}
		
		UserDto userToReturn = new UserDto();
		userToReturn.setId(user.getId());
		userToReturn.setFirstName(user.getFirstName());
		userToReturn.setLastName(user.getLastName());
		userToReturn.setEmail(user.getEmail());
		userToReturn.setUsername(user.getUsername());
		userToReturn.setRoles(rolesName);
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
	
	public static UserEntity toEntity(UserRegisterDto user) {
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
