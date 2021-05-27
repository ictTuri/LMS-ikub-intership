package com.project.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.lms.converter.UserRoleConverter;
import com.project.lms.dto.UserRoleCreateUpdateDto;
import com.project.lms.dto.UserRoleDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.repository.RoleRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.repository.UserRoleRepository;
import com.project.lms.service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

	private UserRoleRepository userRoleRepository;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	public UserRoleServiceImpl(UserRoleRepository userRoleRepository, UserRepository userRepository,
			RoleRepository roleRepository) {
		super();
		this.userRoleRepository = userRoleRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	/*
	 * Return list of all relations of user-role
	 */
	@Override
	public List<UserRoleDto> getAllUserRole() {
		return UserRoleConverter.toDtoList(userRoleRepository.getAllUserRoles());
	}

	/*
	 * Return a user-role relation based on id
	 * If id not found throws exception
	 */
	@Override
	public UserRoleDto getUserRoleById(Long id) {
		UserRoleEntity userRoleToReturn = userRoleRepository.getUserRoleById(id);
		if (userRoleToReturn != null) {
			return UserRoleConverter.toDto(userRoleToReturn);
		}
		throw new ObjectIdNotFound("User Role Relation with id: " + id + " can not be found");
	}

	/*
	 * Create a new relation user-role
	 * If role or user does not exist throws exception
	 * If user-role relation already exist it throws exception
	 */
	@Override
	public UserRoleDto createUserRole(UserRoleCreateUpdateDto userRole) {
		UserEntity userToConnect = userRepository.getUserByUsername(userRole.getUsername());
		if (userToConnect != null) {
			String roleName = userRole.getRole().toUpperCase();
			RoleEntity roleToConnect = roleRepository.getRole(roleName);
			if (roleToConnect != null) {
				boolean foundRole = false;
				List<RoleEntity> roleList = roleRepository.getUserRole(userToConnect);
				for (RoleEntity re : roleList) {
					if (re.getName().equals(roleToConnect.getName())) {
						foundRole = true;
					}
				}
				if (!foundRole) {
					UserRoleEntity userRoleToAdd = new UserRoleEntity();
					userRoleToAdd.setRole(roleToConnect);
					userRoleToAdd.setUser(userToConnect);
					userRoleRepository.saveUserRole(userRoleToAdd);
					return UserRoleConverter.toDto(userRoleToAdd);
				}
				throw new CustomExceptionMessage("Relation between User and Role already exist!");
			}
			throw new CustomExceptionMessage("Given Role is not valid,try Admin or Student or Secretary");
		}
		throw new ObjectFilteredNotFound("There is no user with username: " + userRole.getUsername());
	}

	/*
	 * Update an relation user-role
	 * if invalid data passed it throws exception
	 */
	@Override
	public UserRoleDto updateUserRole(long id, UserRoleCreateUpdateDto userRole) {
		UserRoleEntity userRoleToUpdate = userRoleRepository.getUserRoleById(id);
		if (userRoleToUpdate != null) {
			UserEntity userToUpdate = userRepository.getUserByUsername(userRole.getUsername());
			if (userToUpdate != null) {
				String roleName = userRole.getRole().toUpperCase();
				RoleEntity roleToUpdate = roleRepository.getRole(roleName);
				if (roleToUpdate != null) {
					List<RoleEntity> roleList = roleRepository.getUserRole(userToUpdate);
					boolean foundRole = isUserRoleConnected(roleList, userRole);
					if (!foundRole) {
						userRoleToUpdate.setRole(roleToUpdate);
						userRoleToUpdate.setUser(userToUpdate);
						return UserRoleConverter.toDto(userRoleRepository.updateUserRole(userRoleToUpdate));
					}
					throw new CustomExceptionMessage("This Relation already exist!");
				}
				throw new ObjectIdNotFound("Role with name: " + id + " not found !");
			}
			throw new ObjectIdNotFound("User with given username: " + userRole.getUsername() + " not found!");
		}
		throw new ObjectIdNotFound("User Role Relation with id: " + id + " not found !");
	}

	/*
	 * Checks if user-role relation exists
	 */
	public boolean isUserRoleConnected(List<RoleEntity> roles, UserRoleCreateUpdateDto user) {
		for (RoleEntity re : roles) {
			if (re.getName().equalsIgnoreCase(user.getRole())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * delete a user-role relation 
	 * If it is only one user-role relation it throws exception
	 */
	@Override
	public void deleteUserRole(long id) {
		UserRoleEntity userRoleToDelete = userRoleRepository.getUserRoleById(id);
		if (userRoleToDelete != null) {
			int roleListSize = roleRepository.getUserRole(userRoleToDelete.getUser()).size();
			if (roleListSize > 1) {
				userRoleRepository.deleteUserRole(userRoleToDelete);
			}else {
				throw new CustomExceptionMessage("This user has only this role!");
			}
		}else {
			throw new ObjectIdNotFound("User Role Relation with id: " + id + " was not found!");
		}
	}

}
