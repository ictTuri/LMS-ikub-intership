package com.project.lms.repository.sql;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;

@Repository
@Profile(value = "sql")
public interface UserRoleRepositorySql extends UserRoleRepository, JpaRepository<UserRoleEntity, Long>{

}
