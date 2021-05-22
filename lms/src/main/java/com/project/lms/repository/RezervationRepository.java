package com.project.lms.repository;

import java.util.List;

import com.project.lms.model.RezervationEntity;

public interface RezervationRepository {

	List<RezervationEntity> findAll();

	RezervationEntity findById(long id);

}
