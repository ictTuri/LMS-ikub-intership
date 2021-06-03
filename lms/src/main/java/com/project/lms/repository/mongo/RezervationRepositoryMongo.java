package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.mongoconfig.SequenceService;
import com.project.lms.repository.RezervationRepository;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
	
	private static final String REZERVATIONS = "rezervations";
	private static final String STUDENT = "student";
	
	@Override
	public List<RezervationEntity> findAll() {
		return mt.findAll(RezervationEntity.class, REZERVATIONS);
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
		return mt.save(entityCreate, REZERVATIONS);
	}

	@Override
	public void deleteRezervation(RezervationEntity rezervationToDelete) {
		//Deletes rezervations from DB
		mt.remove(rezervationToDelete, REZERVATIONS);
	}

	@Override
	public List<RezervationEntity> myRezervations(UserEntity thisUser){
		var query = new Query()
				.addCriteria(Criteria.where(STUDENT).is(thisUser));
		return mt.find(query, RezervationEntity.class);
	}

}
