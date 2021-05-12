package com.project.lms.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.lms.converter.UserConverter;
import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.enums.Roles;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;
	
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public CustomResponseDto registerStudent(UserCreateUpdateDto user) {
		RoleEntity role = new RoleEntity();
		role.setName(Roles.STUDENT.getValue());
		UserEntity userToCreate = UserConverter.toEntity(user);
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setRole(role);
		userRole.setUser(userToCreate);
		roleRepository.save(role);
		userRepository.save(userToCreate);
		userRoleRepository.save(userRole);
		CustomResponseDto response = new CustomResponseDto("You have been register, please wait for the activation");
		return response;
	}

}
