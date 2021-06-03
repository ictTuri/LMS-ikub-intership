package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.mongoconfig.SequenceService;
import com.project.lms.repository.UserRoleRepository;


@Repository
@Profile("mongo")
public class UserRoleRepositoryMongo implements UserRoleRepository{

	private MongoTemplate mt;
	private SequenceService ss;
	
	public UserRoleRepositoryMongo(MongoTemplate mt, SequenceService ss) {
		super();
		this.mt = mt;
		this.ss = ss;
	}
	
	private static final String USER_ROLE = "user_role";
	private static final String USER = "user";

	@Override
	public void saveUserRole(UserRoleEntity userRole) {
		// Save new User-Role relation
		userRole.setId(ss.generateSequence(UserRoleEntity.SEQUENCE_NAME));
		mt.insert(userRole, USER_ROLE);
	}

	@Override
	public List<UserRoleEntity> getAllUserRoles() {
		return mt.findAll(UserRoleEntity.class, USER_ROLE);
	}

	@Override
	public UserRoleEntity getUserRoleById(Long id) {
		return mt.findById(id, UserRoleEntity.class, USER_ROLE);
	}

	@Override
	public void deleteUserRole(UserRoleEntity userRoleToDelete) {
		// remove a user role relation
		mt.remove(userRoleToDelete, USER_ROLE);
	}

	@Override
	public UserRoleEntity updateUserRole(UserRoleEntity userRoleToUpdate) {
		return mt.save(userRoleToUpdate, USER_ROLE);
	}

	@Override
	public List<UserRoleEntity> getThisUserRelations(UserEntity userToHardDelete) {
		var query = new Query()
				.addCriteria(Criteria.where(USER).is(userToHardDelete));
		return mt.find(query, UserRoleEntity.class);
	}

}
