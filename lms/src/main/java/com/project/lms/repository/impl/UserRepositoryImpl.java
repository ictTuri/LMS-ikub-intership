package com.project.lms.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.project.lms.entity.UserEntity;
import com.project.lms.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
	
	private EntityManager em;

	public UserRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private static final String USER_USERNAME = "SELECT ue.username FROM UserEntity ue WHERE ue.username = :username";
	private static final String USER_EMAIL = "SELECT ue.email FROM UserEntity ue WHERE ue.email = :email";
	private static final String GET_ALL_USERS = "FROM UserEntity";
	private static final String CHECK_USER_EXIST = "FROM UserEntity ue WHERE ue.username = :username "
			+ "OR ue.email = :email";
	private static final String USER_BY_USERNAME = "FROM UserEntity ue WHERE ue.username = :username";
	private static final String ACTIVE_USER_BY_ID = "FROM UserEntity ue WHERE ue.id = :id AND ue.activated = true";
	private static final String ACTIVE_USER_BY_USERNAME = "FROM UserEntity ue WHERE ue.username = :username AND ue.activated = true";

	
	@Override
	public List<UserEntity> getAllUsers() {
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
		TypedQuery<UserEntity> query = em.createQuery(CHECK_USER_EXIST, UserEntity.class)
				.setParameter("username", username)
				.setParameter("email", email);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public UserEntity getUserByUsername(String username) {
		TypedQuery<UserEntity> query = em.createQuery(USER_BY_USERNAME, UserEntity.class)
				.setParameter("username", username);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean existUsername(String username) {
		TypedQuery<String> query = em.createQuery(USER_USERNAME, String.class)
				.setParameter("username", username);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public boolean existEmail(String email) {
		TypedQuery<String> query = em.createQuery(USER_EMAIL, String.class)
				.setParameter("email", email);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}


	@Override
	public UserEntity getActivatedUserById(long id) {
		TypedQuery<UserEntity> query = em.createQuery(ACTIVE_USER_BY_ID, UserEntity.class)
				.setParameter("id", id);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public UserEntity getActivatedUserByUsername(String username) {
		TypedQuery<UserEntity> query = em.createQuery(ACTIVE_USER_BY_USERNAME, UserEntity.class)
				.setParameter("username", username);
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
