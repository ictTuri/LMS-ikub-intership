package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.dto.RezervationCreateUpdateDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.RezervationEntity;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.service.impl.RezervationServiceImpl;
import com.project.lms.utils.RezervationUtil;

@SpringBootTest
class RezervationServiceTest {

	@InjectMocks
	private RezervationServiceImpl rezervationService;

	@Mock
	private RezervationRepository rezervationRepository;

	@Test
	void givenRezervationsList_whenGetThem_validateSize() {
		List<RezervationEntity> rezervations = new ArrayList<>();
		RezervationEntity rezervationOne = RezervationUtil.rezervationOne();
		RezervationEntity rezervationTwo = RezervationUtil.rezervationTwo();
		rezervations.add(rezervationOne);
		rezervations.add(rezervationTwo);

		when(rezervationRepository.findAll()).thenReturn(rezervations);
		int size = rezervationService.showAllRezervation().size();

		assertEquals(2, size);
		verify(rezervationRepository).findAll();
	}

	@Test
	void givenRezervations_whenUpdate_thenThrowsException() {
		RezervationEntity rezervationOne = RezervationUtil.rezervationOne();
		RezervationCreateUpdateDto rezervation = new RezervationCreateUpdateDto();
		long id = 1;

		Mockito.when(rezervationRepository.updateRezervation(rezervationOne)).thenReturn(rezervationOne);

		assertThrows(ObjectIdNotFound.class, () -> {
			rezervationService.updateRezervation(id, rezervation);
		});
	}

	@Test
	void givenRezervation_whenGet_thenValidateBook() {
		RezervationEntity rezervation = RezervationUtil.rezervationTwo();
		long id = 1;

		Mockito.when(rezervationRepository.findById(id)).thenReturn(rezervation);
		RezervationDto rezervationToGet = rezervationService.showRezervationById(id);

		assertNotNull(rezervationToGet);
		assertEquals(rezervationToGet.getBookTitle(), rezervation.getBook().getTitle());
		assertEquals(rezervationToGet.getStudentUsername(), rezervation.getStudent().getUsername());
	}

}
