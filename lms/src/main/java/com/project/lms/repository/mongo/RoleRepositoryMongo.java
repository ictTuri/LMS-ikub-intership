package com.project.lms.repository.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.mongoconfig.SequenceService;
import com.project.lms.repository.RoleRepository;

@Repository
@Profile("mongo")
public class RoleRepositoryMongo implements RoleRepository {

	private MongoTemplate mt;
	private SequenceService ss;

	public RoleRepositoryMongo(MongoTemplate mt, SequenceService ss) {
		super();
		this.mt = mt;
		this.ss = ss;
	}

	@Override
	public RoleEntity getRoleById(Long id) {
		return mt.findById(id, RoleEntity.class);
	}

	@Override
	public RoleEntity getRole(String name) {
		Query query = new Query().addCriteria(Criteria.where("name").is(name));
		return mt.findOne(query, RoleEntity.class);
	}

	@Override
	public List<RoleEntity> getUserRole(UserEntity user){
		Query query = new Query().addCriteria(Criteria.where("user").is(user));
		List<UserRoleEntity> list  = mt.find(query, UserRoleEntity.class);
		List<RoleEntity> listRoles = new ArrayList<>();
		for(UserRoleEntity ure : list) {
			listRoles.add(ure.getRole());
		}
		return listRoles;
	}

	@Override
	public void saveRole(RoleEntity role) {
		// Save a new role
		role.setId(ss.generateSequence(RoleEntity.SEQUENCE_NAME));
		mt.insert(role, "roles");
	}

	@Override
	public List<RoleEntity> getAllRoles() {
		return mt.findAll(RoleEntity.class,	 "roles");
	}

}
