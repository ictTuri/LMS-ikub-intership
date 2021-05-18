package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.model.UserEntity;
import com.project.lms.mongoconfig.SequenceService;
import com.project.lms.repository.UserRepository;

@Repository
@Profile("mongo")
public class UserRepositoryMongo implements UserRepository{
	
	private MongoTemplate mt;
	private SequenceService ss;
	
	public UserRepositoryMongo(MongoTemplate mt, SequenceService ss) {
		super();
		this.mt = mt;
		this.ss = ss;
	}

	private static final String USERNAME = "username";
	private static final String USER_DOC = "users";
	
	@Override
	public void saveUser(UserEntity userToCreate) {
		// Insert a new user
		userToCreate.setId((Long)ss.generateSequence(UserEntity.SEQUENCE_NAME));
		mt.insert(userToCreate, USER_DOC);
	}

	@Override
	public UserEntity getUserById(Long id) {
		return mt.findById(id, UserEntity.class, USER_DOC);
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
		return mt.findOne(query, UserEntity.class, USER_DOC) != null;
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
		mt.save(userToDelete, USER_DOC);
	}

	@Override
	public UserEntity getActivatedUserByUsername(String username) {
		Query query = new Query()
				.addCriteria(Criteria.where(USERNAME).is(username))
				.addCriteria(Criteria.where("activated").is(true));
		return mt.findOne(query, UserEntity.class);
	}

	@Override
	public void deleteUser(UserEntity userToHardDelete) {
		// Hard delete user
		mt.remove(userToHardDelete, USER_DOC);
	}
	
}
