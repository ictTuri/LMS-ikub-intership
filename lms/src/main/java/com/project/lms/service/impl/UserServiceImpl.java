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
import com.project.lms.enums.Roles;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
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
	
	/*
	 * Return user by id
	 */
	public UserEntity userById(long id) {
		return userRepository.getUserById(id);
	}
	
	/*
	 * Return user by username
	 */
	public UserEntity userByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}

	/*
	 * Create a new user Student in database
	 * Validate unique email and username 
	 * After validation it returns a response and create a deactivated user
	 */
	@Override
	public CustomResponseDto registerStudent(UserRegisterDto user) {
		boolean existUsername = userRepository.existUsername(user.getUsername());
				if(!existUsername) {
					boolean existEmail = userRepository.existEmail(user.getEmail());
					if(!existEmail) {
						RoleEntity role = roleRepository.getRole("STUDENT");
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
					throw new CustomExceptionMessage("Email: "+user.getEmail()+" already exist");
				}
			throw new CustomExceptionMessage("Username: "+user.getUsername()+" already exist");
	}

	/*
	 * returns all users but with no roles
	 */
	@Override
	public List<UserDto> getAllUsers() {
		return UserConverter.toListDto(userRepository.getAllUsers());
	}

	/*
	 * Return user by id and corresponding roles
	 * If id invalid it throws exception
	 */
	@Override
	public UserDto getUserById(Long id) {
		UserEntity userToReturn = userById(id);
		if (userToReturn != null) {
			List<RoleEntity> roles = roleRepository.getUserRole(userToReturn);
			return UserConverter.toDtoWithRoles(userToReturn, roles);
		}
		throw new ObjectIdNotFound("User with id: " + id + " can not be found");
	}

	/*
	 * Create a new user from Admin
	 * Validates username and email uniques 
	 * Can be save activated or deactivated
	 */
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
				UserEntity userToSave = UserConverter.toEntity(user);
				// Save user on DB
				userRepository.saveUser(userToSave);
				UserRoleEntity userRole = new UserRoleEntity();
				userRole.setRole(roleToInsert);
				userRole.setUser(userToSave);
				// Save relation of user and role to UserRole table
				userRoleRepository.saveUserRole(userRole);
				UserEntity userToReturn = userByUsername(userToSave.getUsername());
				List<RoleEntity> rolesToReturn = roleRepository.getUserRole(userToReturn);
				return UserConverter.toDtoWithRoles(userToReturn, rolesToReturn);
			}
			throw new CustomExceptionMessage("Given Role is not valid,try Admin or Student or Secretary");
		}
		throw new CustomExceptionMessage("Username or email already exist!");
	}

	/*
	 * Update user 
	 * Validates Data and compare to Database
	 */
	@Override
	public UserDto updateUser(Long id, @Valid UserCreateUpdateDto user) {
		UserEntity userToUpdate = userById(id);
		if (userToUpdate != null) {
			if (existEmail(user, userToUpdate)) {
				throw new CustomExceptionMessage("Email: " + user.getEmail() + " is taken!");
			}
			if (existUsername(user, userToUpdate)) {
				throw new CustomExceptionMessage("Username: " + user.getUsername() + " is taken!");
			}
			String roleName = user.getRole().toUpperCase();
			return updateValidationsExtracted(user, userToUpdate, roleName);
		}
		throw new ObjectIdNotFound("Can not find user with given Id: " + id);
	}

	/*
	 * Check if username already exist
	 */
	public boolean existUsername(UserCreateUpdateDto user, UserEntity userToUpdate) {
		return !(userToUpdate.getUsername().equals(user.getUsername()))
				&& userRepository.existUsername(user.getUsername());
	}

	/*
	 * check if email already exist
	 */
	public boolean existEmail(UserCreateUpdateDto user, UserEntity userToUpdate) {
		return !(userToUpdate.getEmail().equalsIgnoreCase(user.getEmail()))
				&& userRepository.existEmail(user.getEmail());
	}

	/*
	 * Extra validation on update
	 * Check if a new role is inserted and validates 
	 * against the list of roles that user currently have
	 */
	public UserDto updateValidationsExtracted(UserCreateUpdateDto user, UserEntity userToUpdate, String roleName) {
		RoleEntity roleToInsert = roleRepository.getRole(roleName);
		if (roleToInsert != null) {
			List<RoleEntity> roles = roleRepository.getUserRole(userToUpdate);
			UserEntity userConverted = UserConverter.updateConvert(userToUpdate, user);
			userConverted.setPassword(passwordEncoder.encode(user.getPassword()));
			boolean foundRole = isUserRoleConnected(roles, user);
			if (foundRole) {
				userRepository.updateUser(userConverted);
				return UserConverter.toDtoWithRoles(userToUpdate, roles);
			} else {
				UserRoleEntity userRoleRelation = new UserRoleEntity();
				userRoleRelation.setRole(roleToInsert);
				userRoleRelation.setUser(userConverted);
				userRepository.updateUser(userConverted);
				userRoleRepository.saveUserRole(userRoleRelation);
				roles.add(roleToInsert);
				return UserConverter.toDtoWithRoles(userConverted, roles);
			}
		}
		throw new CustomExceptionMessage("Given Role is not valid,try Admin or Student or Secretary");
	}

	/*
	 * Check if there is a connection between roles of user and the new role passed
	 */
	public boolean isUserRoleConnected(List<RoleEntity> roles, UserCreateUpdateDto user) {
		for (RoleEntity re : roles) {
			if (re.getName().equalsIgnoreCase(user.getRole())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Soft delete a user from DB
	 */
	@Override
	public void softDeleteUser(long id) {
		UserEntity userToSoftDelete = userRepository.getActivatedUserById(id);
		if (userToSoftDelete != null) {
			userToSoftDelete.setActivated(Boolean.FALSE);
			userRepository.updateUser(userToSoftDelete);
		}else {
			throw new ObjectIdNotFound("Can not find user with given id: " + id);
		}
	}

	/*
	 * Hard Delee a user from DB 
	 * Also deletes the user roles
	 */
	@Override
	public void hardDeleteUser(long id) {
		UserEntity userToHardDelete = userById(id);
		if(userToHardDelete != null) {
			List<UserRoleEntity> listOfRelations = userRoleRepository.getThisUserRelations(userToHardDelete);
			for(UserRoleEntity ure : listOfRelations ) {
				userRoleRepository.deleteUserRole(ure);
			}
				userRepository.deleteUser(userToHardDelete);
		}else {
			throw new ObjectIdNotFound("Can not find user with id: "+id);
		}	
	}
	

}
