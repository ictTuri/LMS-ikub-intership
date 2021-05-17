package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.RoleEntity;
import com.project.lms.entity.UserEntity;
import com.project.lms.repository.RoleRepository;

@Repository
@Profile(value = "mongo")
public class RoleRepositoryMongo implements RoleRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public RoleEntity getRoleById(int id) {
		return null;
	}

	@Override
	public RoleEntity getRole(String name) {
		return null;
	}

	@Override
	public List<RoleEntity> getUserRole(UserEntity user) {
		return null;
	}

	@Override
	public void saveRole(RoleEntity role) {
		
	}

}
