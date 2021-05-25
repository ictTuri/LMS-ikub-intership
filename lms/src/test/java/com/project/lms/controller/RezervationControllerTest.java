package com.project.lms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.lms.converter.RezervationConverter;
import com.project.lms.dto.RezervationCreateUpdateDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.model.RezervationEntity;
import com.project.lms.service.RezervationService;
import com.project.lms.utils.RezervationUtil;

@ExtendWith(MockitoExtension.class)
class RezervationControllerTest {
	@InjectMocks
	RezervationController rezervationController;
	
	@Mock
	RezervationService rezervationService;
	
	@BeforeEach
	void setup() {
		rezervationController = new RezervationController(rezervationService);
	}
	
	@Test
	void givenRezervationList_WhenGetList_checkValidateList() {
		List<RezervationDto> rezervations = new ArrayList<>();
		RezervationDto first = RezervationConverter.toDto(RezervationUtil.rezervationOne());
		RezervationDto second = RezervationConverter.toDto(RezervationUtil.rezervationTwo());
		rezervations.add(first);
		rezervations.add(second);
		
		
		Mockito.when(rezervationService.showAllRezervation()).thenReturn(rezervations);

		ResponseEntity<List<RezervationDto>> allRezervations = rezervationController.showAllRezervations();
		
		assertEquals(HttpStatus.OK, allRezervations.getStatusCode());
		assertNotNull(allRezervations);
		assertEquals(!rezervations.isEmpty(),allRezervations.hasBody());
		assertEquals(2, allRezervations.getBody().size());
		verify(rezervationService).showAllRezervation();
	}

	@Test
	void givenNewRezervation_whenSave_thenVerifySave() {
		RezervationCreateUpdateDto rezervation = new RezervationCreateUpdateDto();
		rezervation.setBookTitle("HellBringer");
		rezervation.setUsername("ictTuri");
		RezervationDto toReturn  = RezervationConverter.toDto(RezervationUtil.rezervationOne());
		

		Mockito.when(rezervationService.createRezervation(rezervation)).thenReturn(toReturn);

		ResponseEntity<?> createdRezervation = rezervationController.createRezervation(rezervation);

		assertNotNull(createdRezervation);
		assertEquals(HttpStatus.CREATED, createdRezervation.getStatusCode());
	}
	
	@Test
	void givenRezervationId_whenDelete_thenVerifyResponse() {
		ResponseEntity<?> deleteRezervationById = rezervationController.deleteRezervation(1);
		
		assertNotNull(deleteRezervationById);
		assertEquals(HttpStatus.NO_CONTENT,deleteRezervationById.getStatusCode());
	}
	
	@Test
	void givenRezervation_whenAskedById_thenReturnThatRezervation() {
		RezervationEntity rezervation = RezervationUtil.rezervationThree();
		rezervation.setId(Long.valueOf(1));
		
		Mockito.when(rezervationService.showRezervationById(Long.valueOf(1))).thenReturn(RezervationConverter.toDto(rezervation));
		
		ResponseEntity<?> rezervationById = rezervationController.showRezervationById(1);
		assertNotNull(rezervationById);
		assertEquals(HttpStatus.OK, rezervationById.getStatusCode());
	}
	
	@Test
	void givenRezervation_whenClose_thenReturnClosedRezervation() {
		long id = 1;
		RezervationDto rezervation = new RezervationDto();
		rezervation.setBookTitle("HellBringer");
		rezervation.setId(Long.valueOf(1));
		rezervation.setStudentUsername("TestStudent");
		rezervation.setReturnDate(LocalDateTime.now());
		
		
		Mockito.when(rezervationService.closeRezervation(id)).thenReturn(rezervation);
		
		ResponseEntity<?> rezervationClosed = rezervationController.closeRezervation(id);
		assertNotNull(rezervationClosed);
		assertEquals(HttpStatus.OK, rezervationClosed.getStatusCode());
	}
	
	@Test
	void givenRezervation_whenUpdate_thenReturnDto() {
		long id = 1;
		RezervationDto returnDto = new RezervationDto();
		returnDto.setBookTitle("HellBringer");
		returnDto.setStudentUsername("TestUsername");
		RezervationCreateUpdateDto rezervationUpdate = new RezervationCreateUpdateDto();
		
		Mockito.when(rezervationService.updateRezervation(id,rezervationUpdate)).thenReturn(returnDto);
		
		ResponseEntity<?> rezervationUpdated = rezervationController.updateRezervation(id, rezervationUpdate);
		assertNotNull(rezervationUpdated);
		assertEquals(HttpStatus.CREATED, rezervationUpdated.getStatusCode());
	}

}
