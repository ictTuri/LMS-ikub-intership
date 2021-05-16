package com.project.lms.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.repository.UserRepository;

@Repository
@Profile(value = "mongo")
public interface UserRepositoryMongo extends UserRepository, MongoRepository<UserEntity, Long>{

}
