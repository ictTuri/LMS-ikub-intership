package com.project.lms.repository.sql;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.repository.UserRepository;

@Repository
@Profile(value = "sql")
public interface UserRepositorySql extends UserRepository, JpaRepository<UserEntity, Long>{

}
