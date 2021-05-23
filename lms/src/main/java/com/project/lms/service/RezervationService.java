package com.project.lms.service;

import java.util.List;

import javax.validation.Valid;

import com.project.lms.dto.RezervationCreateUpdateDto;
import com.project.lms.dto.RezervationDto;

public interface RezervationService {

	List<RezervationDto> showAllRezervation();

	RezervationDto showRezervationById(long id);

	RezervationDto createRezervation(RezervationCreateUpdateDto rezervation);

	RezervationDto updateRezervation(long id, @Valid RezervationCreateUpdateDto rezervation);

	void deleteRezervation(long id);

}
