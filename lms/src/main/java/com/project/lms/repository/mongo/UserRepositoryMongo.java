package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.repository.UserRepository;

@Repository
@Profile(value = "mongo")
public class UserRepositoryMongo  implements UserRepository{

	@Autowired
	private MongoTemplate mt;
	
	private static final String USERNAME = "username";
	
	@Override
	public void saveUser(UserEntity userToCreate) {
		// Insert a new user
		mt.insert(userToCreate, "users");
	}

	@Override
	public UserEntity getUserById(Long id) {
		return mt.findById(id, UserEntity.class, "users");
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return mt.findAll(UserEntity.class);
	}

	@Override
	public boolean checkUserByUsernameEmail(String username, String email) {
		Query query = new Query()
				.addCriteria(Criteria.where(USERNAME).is(username))
				.addCriteria(Criteria.where("email").is(email));
		return mt.findOne(query, UserEntity.class, "users") != null;
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		Query query = new Query()
				.addCriteria(Criteria.where(USERNAME).is(username));
		return mt.findOne(query, UserEntity.class);
	}

	@Override
	public boolean existUsername(String username) {
		Query query = new Query()
				.addCriteria(Criteria.where(USERNAME).is(username));
		return mt.findOne(query, UserEntity.class) != null;
	}

	@Override
	public boolean existEmail(String email) {
		Query query = new Query()
				.addCriteria(Criteria.where("email").is(email));
		return mt.findOne(query, UserEntity.class) != null;
	}

	@Override
	public UserEntity getActivatedUserById(long id) {
		Query query = new Query()
				.addCriteria(Criteria.where("id").is(id))
				.addCriteria(Criteria.where("activated").is("true"));
		return mt.findOne(query, UserEntity.class);
	}

	@Override
	public void updateUser(UserEntity userToDelete) {
		// update the user for soft delete
		mt.save(userToDelete, "users");
	}

	@Override
	public UserEntity getActivatedUserByUsername(String username) {
		Query query = new Query()
				.addCriteria(Criteria.where(USERNAME).is(username))
				.addCriteria(Criteria.where("activated").is("true"));
		return mt.findOne(query, UserEntity.class);
	}

	@Override
	public void deleteUser(UserEntity userToHardDelete) {
		// Hard delete user
		mt.remove(userToHardDelete, "users");
	}
	
}
