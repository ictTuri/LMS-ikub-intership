package com.project.lms.repository.sql;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.RezervationRepository;

@Repository
@Profile("sql")
public class RezervationRepositoryImpl implements RezervationRepository {

	private EntityManager em;
	
	public RezervationRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}
	
	private static final String USER = "user";

	@Override
	public List<RezervationEntity> findAll() {
		final var GET_ALL_REZERVATIONS = "FROM RezervationEntity ";
		return em.createQuery(GET_ALL_REZERVATIONS, RezervationEntity.class).getResultList();
	}

	@Override
	public RezervationEntity findById(long id) {
		try {
			return em.find(RezervationEntity.class, id);
		} catch (IllegalArgumentException  e) {
			return null;
		}
	}

	@Override
	public RezervationEntity saveRezervation(RezervationEntity entityCreate) {
		em.persist(entityCreate);
		return entityCreate;
	}

	@Override
	public RezervationEntity updateRezervation(RezervationEntity entityCreate) {
		return em.merge(entityCreate);
	}

	@Override
	public void deleteRezervation(RezervationEntity rezervationToDelete) {
		em.remove(rezervationToDelete);
	}

	@Override
	public List<RezervationEntity> myRezervations(UserEntity thisUser) {
		final var GET_THIS_USER_REZERVATIONS = "SELECT re FROM RezervationEntity re WHERE re.student = :user";
		try{
			return em.createQuery(GET_THIS_USER_REZERVATIONS, RezervationEntity.class)
					.setParameter(USER, thisUser).getResultList();
		}catch(IllegalStateException e) {
			return Collections.emptyList();
		}
	}
	
	

}
