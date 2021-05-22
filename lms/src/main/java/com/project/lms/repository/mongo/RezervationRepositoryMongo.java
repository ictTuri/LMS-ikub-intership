package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.project.lms.model.RezervationEntity;
import com.project.lms.repository.RezervationRepository;

public class RezervationRepositoryMongo implements RezervationRepository {

	private MongoTemplate mt;
	
	public RezervationRepositoryMongo(MongoTemplate mt) {
		super();
		this.mt = mt;
	}

	@Override
	public List<RezervationEntity> findAll() {
		return mt.findAll(RezervationEntity.class, "rezervations");
	}

	@Override
	public RezervationEntity findById(long id) {
		return mt.findById(id, RezervationEntity.class);
	}

}
