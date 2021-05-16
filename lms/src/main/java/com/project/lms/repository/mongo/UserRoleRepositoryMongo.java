package com.project.lms.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;

@Repository
@Profile(value = "mongo")
public interface UserRoleRepositoryMongo extends UserRoleRepository, MongoRepository<UserRoleEntity, Long>{

}
