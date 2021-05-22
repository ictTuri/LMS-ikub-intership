package com.project.lms.service;

import java.util.List;

import com.project.lms.dto.RezervationDto;

public interface RezervationService {

	List<RezervationDto> showAllRezervation();

	RezervationDto showRezervationById(long id);

}
