package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
