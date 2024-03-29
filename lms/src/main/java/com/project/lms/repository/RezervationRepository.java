package com.project.lms.repository;

import java.util.List;

import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;

public interface RezervationRepository {

	List<RezervationEntity> findAll();

	RezervationEntity findById(long id);

	RezervationEntity saveRezervation(RezervationEntity entityCreate);

	RezervationEntity updateRezervation(RezervationEntity entityCreate);

	void deleteRezervation(RezervationEntity rezervationToDelete);

	List<RezervationEntity> myRezervations(UserEntity thisUser);

}
