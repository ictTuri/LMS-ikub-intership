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
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserEntity> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkUserByUsernameEmail(String username, String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existEmail(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserEntity getActivatedUserById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(UserEntity userToDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity getActivatedUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(UserEntity userToHardDelete) {
		// TODO Auto-generated method stub
		
	}
	
}
