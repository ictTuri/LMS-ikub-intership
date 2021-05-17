package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.repository.UserRepository;

@Repository
@Profile(value = "mongo")
public class UserRepositoryMongo  implements UserRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void saveUser(UserEntity userToCreate) {
		
	}

	@Override
	public UserEntity getUserById(Long id) {
		return null;
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return null;
	}

	@Override
	public boolean checkUserByUsernameEmail(String username, String email) {
		return false;
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		return null;
	}

	@Override
	public boolean existUsername(String username) {
		return false;
	}

	@Override
	public boolean existEmail(String email) {
		return false;
	}

	@Override
	public UserEntity getActivatedUserById(long id) {
		return null;
	}

	@Override
	public void updateUser(UserEntity userToDelete) {
		
	}

	@Override
	public UserEntity getActivatedUserByUsername(String username) {
		return null;
	}

	@Override
	public void deleteUser(UserEntity userToHardDelete) {
		
	}
	
}
