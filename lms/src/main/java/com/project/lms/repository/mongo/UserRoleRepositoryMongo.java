package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;


@Repository
@Profile(value = "mongo")
public class UserRoleRepositoryMongo implements UserRoleRepository{

	@Autowired
	private MongoTemplate mt;
	
	@Override
	public void saveUserRole(UserRoleEntity userRole) {
		// Save new User-Role relation
		mt.insert(userRole, "user_role");
	}

	@Override
	public List<UserRoleEntity> getAllUserRoles() {
		return mt.findAll(UserRoleEntity.class, "user_role");
	}

	@Override
	public UserRoleEntity getUserRoleById(Long id) {
		return mt.findById(id, UserRoleEntity.class, "user_role");
	}

	@Override
	public void deleteUserRole(UserRoleEntity userRoleToDelete) {
		// remove a user role relation
		mt.remove(userRoleToDelete, "user_role");
	}

	@Override
	public UserRoleEntity updateUserRole(UserRoleEntity userRoleToUpdate) {
		return mt.save(userRoleToUpdate, "user_role");
	}

	@Override
	public List<UserRoleEntity> getThisUserRelations(UserEntity userToHardDelete) {
		Query query = new Query()
				.addCriteria(Criteria.where("user").is(userToHardDelete));
		return mt.find(query, UserRoleEntity.class);
	}

}
