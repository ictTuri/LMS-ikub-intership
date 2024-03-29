package com.project.lms.repository.sql;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.project.lms.model.UserEntity;
import com.project.lms.model.UserRoleEntity;
import com.project.lms.repository.UserRoleRepository;

@Repository
@Profile("sql")
public class UserRoleRepositoryImpl implements UserRoleRepository {

	private EntityManager em;

	public UserRoleRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private static final String USER = "user";

	@Override
	public List<UserRoleEntity> getAllUserRoles() {
		final var GET_ALL_USER_ROLES = "FROM UserRoleEntity";
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
		final var GET_THIS_USER_RELATIONS = "FROM UserRoleEntity ure WHERE ure.user = :user";
		TypedQuery<UserRoleEntity> query = em.createQuery(GET_THIS_USER_RELATIONS, UserRoleEntity.class)
				.setParameter(USER, userToHardDelete);
		try {
			return query.getResultList();
		} catch (IllegalStateException e) {
			return Collections.emptyList();
		}
	}

}
