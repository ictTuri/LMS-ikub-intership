package com.project.lms.repository.sql;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.RoleEntity;
import com.project.lms.repository.RoleRepository;

@Repository
@Profile(value = "sql")
public interface RoleRepositorySql extends RoleRepository, JpaRepository<RoleEntity, Integer>{

}
