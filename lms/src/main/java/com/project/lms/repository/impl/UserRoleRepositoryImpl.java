package com.project.lms.repository.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.entity.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;

@Repository
@Transactional
public class UserRoleRepositoryImpl implements UserRoleRepository {

	private EntityManager em;

	public UserRoleRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private static final String GET_ALL_USER_ROLES = "FROM UserRoleEntity";
	private static final String GET_THIS_USER_RELATIONS = "FROM UserRoleEntity ure WHERE ure.user = :user";

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
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public void deleteUserRole(UserRoleEntity userRoleToDelete) {
		em.remove(userRoleToDelete);
	}

	@Override
	public UserRoleEntity updateUserRole(UserRoleEntity userRoleToUpdate) {
		return em.merge(userRoleToUpdate);

	}

	@Override
	public List<UserRoleEntity> getThisUserRelations(UserEntity userToHardDelete) {
		TypedQuery<UserRoleEntity> query = em.createQuery(GET_THIS_USER_RELATIONS, UserRoleEntity.class)
				.setParameter("user", userToHardDelete);
		try {
			return query.getResultList();
		} catch (IllegalStateException e) {
			return Collections.emptyList();
		}
	}

}
