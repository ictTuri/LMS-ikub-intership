package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.repository.RoleRepository;

@Repository
@Profile(value = "mongo")
public class RoleRepositoryMongo implements RoleRepository{

	@Autowired
	private MongoTemplate mt;
	
	@Override
	public RoleEntity getRoleById(int id) {
		return mt.findById(id, RoleEntity.class);
	}

	@Override
	public RoleEntity getRole(String name) {
		Query query = new Query()
				.addCriteria(Criteria.where("name").is(name));
		return mt.findOne(query, RoleEntity.class);
	}

	@Override
	public List<RoleEntity> getUserRole(UserEntity user) {
		return null;
	}

	@Override
	public void saveRole(RoleEntity role) {
		// Save a new role
		mt.insert(role, "roles");
	}

}
