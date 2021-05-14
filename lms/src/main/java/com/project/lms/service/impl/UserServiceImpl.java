package com.project.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.lms.converter.UserConverter;
import com.project.lms.dto.CustomResponseDto;
import com.project.lms.dto.UserCreateUpdateDto;
import com.project.lms.dto.UserRegisterDto;
import com.project.lms.dto.UserDto;
import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.enums.Roles;
import com.project.lms.exception.MyExcMessages;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private UserRoleRepository userRoleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userRoleRepository = userRoleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// STUDENT REGISTRATION METHOD
	@Override
	public CustomResponseDto registerStudent(UserRegisterDto user) {
		// Get Student role object from DB
		RoleEntity role = roleRepository.getRole("ROLE_STUDENT");
		UserEntity userToCreate = UserConverter.toEntity(user);
		userToCreate.setPassword(passwordEncoder.encode(userToCreate.getPassword()));
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setRole(role);
		userRole.setUser(userToCreate);
		// Save User on DB
		userRepository.saveUser(userToCreate);
		// Save user role Relation on join table
		userRoleRepository.saveUserRole(userRole);
		return new CustomResponseDto("You have been register, please wait for the activation");
	}

	// GET ALL USERS BUT NOT THE ROLES
	@Override
	public List<UserDto> getAllUsers() {
		return UserConverter.toListDto(userRepository.getAllUsers());
	}

	// GET ONE USER WITH ASSIGNED ROLES TO IT
	@Override
	public UserDto getUserById(Long id) {
		UserEntity userToReturn = userRepository.getUserById(id);
		if (userToReturn != null) {
			List<RoleEntity> roles = roleRepository.getUserRole(userToReturn);
			return UserConverter.toDtoWithRoles(userToReturn, roles);
		}
		throw new MyExcMessages("User with id: " + id + " can not be found");
	}
 
	// CREATE NEW USER WITH ANY ROLE METHOD
	@Override
	public UserDto createUser(UserCreateUpdateDto user) {
		boolean existUserWithUsernameEmail = userRepository.checkUserByUsernameEmail(user.getUsername(),
				user.getEmail().toLowerCase());
		String roleName = user.getRole().toUpperCase();
		if (!existUserWithUsernameEmail) {
			RoleEntity roleToInsert = roleRepository.getRole(Roles.valueOf(roleName).name());
			if (roleToInsert != null) {
				// Encoded password by BCrypt on 10 power
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setEmail(user.getEmail().toLowerCase());
				UserEntity userToSave = UserConverter.toEntity(user);
				// Save user on DB
				userRepository.saveUser(userToSave);
				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setRole(roleToInsert);
				userRole.setUser(userToSave);
				// Save relation of user and role to UserRole table
				userRoleRepository.saveUserRole(userRole);
				UserEntity userToReturn = userRepository.getUserByUsername(userToSave.getUsername());
				List<RoleEntity> rolesToReturn = roleRepository.getUserRole(userToReturn);
				return UserConverter.toDtoWithRoles(userToReturn, rolesToReturn);
			}
			throw new MyExcMessages("Given Role is not valid,try Admin or Student or Secretary");
		}
		throw new MyExcMessages("Username or email already exist!");
	}

	// UPDATE USER DATA

	@Override
	public UserDto updateUser(Long id, @Valid UserCreateUpdateDto user) {
		UserEntity userToUpdate = userRepository.getUserById(id);
		if (userToUpdate != null) {
			if (!(userToUpdate.getEmail().equalsIgnoreCase(user.getEmail()))
					&& userRepository.existEmail(user.getEmail())) {
				throw new MyExcMessages("Email: " + user.getEmail() + " is taken!");
			}
			if (!(userToUpdate.getUsername().equals(user.getUsername()))
					&& userRepository.existUsername(user.getUsername())) {
				throw new MyExcMessages("Username: " + user.getUsername() + " is taken!");
			}
			String roleName = user.getRole().toString().toUpperCase();
			return updateValidationsExtracted(user, userToUpdate, roleName);
		}
		throw new MyExcMessages("Can not find user with given Id: " + id);
	}

	// VALIDATION EXTRACTED FOR USER UPDATE
	private UserDto updateValidationsExtracted(UserCreateUpdateDto user, UserEntity userToUpdate, String roleName) {
		RoleEntity roleToInsert = roleRepository.getRole(roleName);

		if (roleToInsert != null) {
			List<RoleEntity> roles = roleRepository.getUserRole(userToUpdate);
			boolean foundRole = isUserRoleConnected(roles,user);
			if (foundRole) {
				userToUpdate.setFirstName(user.getFirstName());
				userToUpdate.setLastName(user.getLastName());
				userToUpdate.setEmail(user.getEmail());
				userToUpdate.setUsername(user.getUsername());
				userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
				userToUpdate.setActivated(user.isActivated());
				userRepository.updateUser(userToUpdate);
				return UserConverter.toDtoWithRoles(userToUpdate, roles);
			} else {
				userToUpdate.setFirstName(user.getFirstName());
				userToUpdate.setLastName(user.getLastName());
				userToUpdate.setEmail(user.getEmail());
				userToUpdate.setUsername(user.getUsername());
				userToUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
				userToUpdate.setActivated(user.isActivated());
				UserRoleEntity userRoleRelation = new UserRoleEntity();
				userRoleRelation.setRole(roleToInsert);
				userRoleRelation.setUser(userToUpdate);
				userRepository.updateUser(userToUpdate);
				userRoleRepository.saveUserRole(userRoleRelation);
				userRepository.saveUser(userToUpdate);
				roles.add(roleToInsert);
				return UserConverter.toDtoWithRoles(userToUpdate, roles);
			}
		}
		throw new MyExcMessages("Given Role is not valid,try Admin or Student or Secretary");
	}

	// Helping method to go through roles 
	public boolean isUserRoleConnected(List<RoleEntity> roles, UserCreateUpdateDto user) {
		for (RoleEntity re : roles) {
			if (re.getName().equalsIgnoreCase(user.getRole().toString())) {
				return  true;
			}
		}
		return false;
	}

	// SOFT DELETE USER BY ID
	@Override
	public void softDeleteUser(long id) {
		UserEntity userToDelete = userRepository.getActivatedUserById(id);
		if (userToDelete != null) {
			userToDelete.setActivated(false);
			userRepository.updateUser(userToDelete);
		}
		throw new MyExcMessages("Can not find user with given id: " + id);
	}

}
