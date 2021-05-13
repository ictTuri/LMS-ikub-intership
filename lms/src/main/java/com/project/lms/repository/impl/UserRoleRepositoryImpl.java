package com.project.lms.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

	private EntityManager em;

	public UserRoleRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}
	
	private static final String GET_ALL_USER_ROLES = "FROM UserRoleEntity";

	@Override
	public List<UserRoleEntity> getAllUserRoles() {
		return em.createQuery(GET_ALL_USER_ROLES, UserRoleEntity.class).getResultList();
	}
	
	@Override
	public void saveUserRole(UserRoleEntity userRole) {
		em.persist(userRole);	
	}

	@Override
	public UserRoleEntity getUserRoleById(Long id) {
		try {
			return em.find(UserRoleEntity.class, id);
		} catch (IllegalArgumentException  e) {
			return null;
		}
	}

	
	@Override
	public void deleteUserRole(UserRoleEntity userRoleToDelete) {
		em.remove(userRoleToDelete);
	}
		
}
