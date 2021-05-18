package com.project.lms.repository.sql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.project.lms.model.RoleEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.RoleRepository;

@Repository
@Profile({"sql","test"})
public class RoleRepositoryImpl implements RoleRepository {

	private EntityManager em;

	public RoleRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public RoleEntity getRoleById(Long id) {
		try {
			return em.find(RoleEntity.class, id);
		} catch (IllegalArgumentException  e) {
			return null;
		}
	}

	@Override
	public RoleEntity getRole(String name) {
		final String ROLE_BY_NAME = "FROM RoleEntity re  WHERE re.name = :name";
		TypedQuery<RoleEntity> query = em.createQuery(ROLE_BY_NAME, RoleEntity.class)
				.setParameter("name", name);
		try {
			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public List<RoleEntity> getUserRole(UserEntity user) {
		final String GET_USER_ROLES = "SELECT re FROM RoleEntity re JOIN FETCH UserRoleEntity ure ON ure.role = re.id WHERE ure.user = :user";
		return em.createQuery(GET_USER_ROLES, RoleEntity.class).setParameter("user", user).getResultList();
	}

	@Override
	public void saveRole(RoleEntity role) {
		em.persist(role);
	}
	
}
