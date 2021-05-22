package com.project.lms.service.impl;

import java.util.List;

import com.project.lms.converter.RezervationConverter;
import com.project.lms.dto.RezervationDto;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.service.RezervationService;

public class RezervationServiceImpl implements RezervationService {

	private RezervationRepository rezervationRepository;
	
	public RezervationServiceImpl(RezervationRepository rezervationRepository) {
		super();
		this.rezervationRepository = rezervationRepository;
	}

	@Override
	public List<RezervationDto> showAllRezervation() {
		return RezervationConverter.toListDto(rezervationRepository.findAll());
	}

	@Override
	public RezervationDto showRezervationById(long id) {
		RezervationDto rezervationToReturn =  RezervationConverter.toDto(rezervationRepository.findById(id));
		if(rezervationToReturn != null) {
			return rezervationToReturn;
		}
		throw new ObjectIdNotFound("Rezervation with id: "+id+" can not be found");
	}

}
