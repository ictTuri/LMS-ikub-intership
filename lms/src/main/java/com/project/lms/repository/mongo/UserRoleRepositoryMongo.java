package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;


@Repository
@Profile(value = "mongo")
public class UserRoleRepositoryMongo implements UserRoleRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void saveUserRole(UserRoleEntity userRole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserRoleEntity> getAllUserRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRoleEntity getUserRoleById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserRole(UserRoleEntity userRoleToDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserRoleEntity updateUserRole(UserRoleEntity userRoleToUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleEntity> getThisUserRelations(UserEntity userToHardDelete) {
		// TODO Auto-generated method stub
		return null;
	}

}
