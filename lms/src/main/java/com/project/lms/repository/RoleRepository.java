package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
