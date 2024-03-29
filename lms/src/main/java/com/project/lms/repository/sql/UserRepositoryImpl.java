package com.project.lms.repository.sql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.project.lms.model.UserEntity;
import com.project.lms.repository.UserRepository;

@Repository
@Profile("sql")
public class UserRepositoryImpl implements UserRepository {
	
	private EntityManager em;

	public UserRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}
	
	private static final String USERNAME = "username";
	private static final String EMAIL = "email";
	private static final String ID = "id";
	
	
	@Override
	public List<UserEntity> getAllUsers() {
		final var GET_ALL_USERS = "FROM UserEntity";
		return em.createQuery(GET_ALL_USERS, UserEntity.class).getResultList();
	}	
	
	@Override
	public void saveUser(UserEntity userToCreate) {
		em.persist(userToCreate);
	}

	@Override
	public void updateUser(UserEntity userToDelete) {
		em.merge(userToDelete);
	}
	
	@Override
	public UserEntity getUserById(Long id) {
		try {
			return em.find(UserEntity.class, id);
		} catch (IllegalArgumentException  e) {
			return null;
		}
	}

	@Override
	public boolean checkUserByUsernameEmail(String username, String email) {
		final var CHECK_USER_EXIST = "FROM UserEntity ue WHERE ue.username = :username OR ue.email = :email";
		TypedQuery<UserEntity> query = em.createQuery(CHECK_USER_EXIST, UserEntity.class)
				.setParameter(USERNAME, username)
				.setParameter(EMAIL, email);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		final var USER_BY_USERNAME = "FROM UserEntity ue WHERE ue.username = :username";
		TypedQuery<UserEntity> query = em.createQuery(USER_BY_USERNAME, UserEntity.class)
				.setParameter(USERNAME, username);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean existUsername(String username) {
		final var USER_USERNAME = "SELECT ue.username FROM UserEntity ue WHERE ue.username = :username";
		TypedQuery<String> query = em.createQuery(USER_USERNAME, String.class)
				.setParameter(USERNAME, username);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public boolean existEmail(String email) {
		final var USER_EMAIL = "SELECT ue.email FROM UserEntity ue WHERE ue.email = :email";
		TypedQuery<String> query = em.createQuery(USER_EMAIL, String.class)
				.setParameter(EMAIL, email);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}


	@Override
	public UserEntity getActivatedUserById(long id) {
		final var ACTIVE_USER_BY_ID = "FROM UserEntity ue WHERE ue.id = :id AND ue.activated = true";
		TypedQuery<UserEntity> query = em.createQuery(ACTIVE_USER_BY_ID, UserEntity.class)
				.setParameter(ID, id);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public UserEntity getActivatedUserByUsername(String username) {
		final var ACTIVE_USER_BY_USERNAME = "FROM UserEntity ue WHERE ue.username = :username AND ue.activated = true";
		TypedQuery<UserEntity> query = em.createQuery(ACTIVE_USER_BY_USERNAME, UserEntity.class)
				.setParameter(USERNAME, username);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public void deleteUser(UserEntity userToHardDelete) {
		em.remove(userToHardDelete);	
	}

}
