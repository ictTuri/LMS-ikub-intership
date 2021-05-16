package com.project.lms.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.RoleEntity;
import com.project.lms.repository.RoleRepository;

@Repository
@Profile(value = "mongo")
public interface RoleRepositoryMongo extends RoleRepository, MongoRepository<RoleEntity, Integer>{

}
