package com.project.lms.repository.sql;

import java.util.List;

import javax.persistence.EntityManager;

import com.project.lms.model.RezervationEntity;
import com.project.lms.repository.RezervationRepository;

public class RezervationRepositoryImpl implements RezervationRepository {

	private EntityManager em;
	
	public RezervationRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public List<RezervationEntity> findAll() {
		final String GET_ALL_REZERVATIONS = "FROM RezervationEntity ";
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
	
	

}
