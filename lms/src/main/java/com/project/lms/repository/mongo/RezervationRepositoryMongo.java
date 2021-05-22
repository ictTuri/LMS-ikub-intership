package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.model.RezervationEntity;
import com.project.lms.mongoconfig.SequenceService;
import com.project.lms.repository.RezervationRepository;

@Repository
@Profile("mongo")
public class RezervationRepositoryMongo implements RezervationRepository {

	private MongoTemplate mt;
	private SequenceService ss;

	public RezervationRepositoryMongo(MongoTemplate mt, SequenceService ss) {
		super();
		this.mt = mt;
		this.ss = ss;
	}

	@Override
	public List<RezervationEntity> findAll() {
		return mt.findAll(RezervationEntity.class, "rezervations");
	}

	@Override
	public RezervationEntity findById(long id) {
		return mt.findById(id, RezervationEntity.class);
	}

	@Override
	public RezervationEntity saveRezervation(RezervationEntity entityCreate) {
		entityCreate.setId(ss.generateSequence(RezervationEntity.SEQUENCE_NAME));
		return mt.insert(entityCreate);
	}

	@Override
	public RezervationEntity updateRezervation(RezervationEntity entityCreate) {
		return mt.save(entityCreate, "rezervations");
	}

}
