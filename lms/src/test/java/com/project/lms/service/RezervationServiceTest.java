package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.converter.RezervationConverter;
import com.project.lms.dto.RezervationCreateUpdateDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.exception.UserNotFoundException;
import com.project.lms.model.BookEntity;
import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.impl.RezervationServiceImpl;
import com.project.lms.utils.BookUtil;
import com.project.lms.utils.RezervationUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class RezervationServiceTest {

	@InjectMocks
	private RezervationServiceImpl rezervationService;

	@Mock
	private RezervationRepository rezervationRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BookRepository bookRepository;

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
	
	@Test
	void givenRezervations_whenDontExist_thenThrowsException() {
		long id = 1;

		Mockito.when(rezervationRepository.findById(id)).thenReturn(null);

		assertThrows(ObjectIdNotFound.class, () -> {
			rezervationService.showRezervationById(id);
			});
	}
	
	@Test
	void givenRezervationCreate_whenUsernameNotFound_thenThrowsException() {
		RezervationCreateUpdateDto rezervation = new RezervationCreateUpdateDto();
		
		Mockito.when(userRepository.getActivatedUserByUsername("test")).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> {
			rezervationService.createRezervation(rezervation);
			});
	}
	
	@Test
	void givenRezervationCreate_whenBookNotFound_thenThrowsException() {
		UserEntity user = UserUtil.userTest();
		RezervationCreateUpdateDto rezervation = new RezervationCreateUpdateDto();
		
		Mockito.when(userRepository.getActivatedUserByUsername(rezervation.getUsername())).thenReturn(user);
		Mockito.when(bookRepository.getBookByTitle("Hellbringer")).thenReturn(null);

		assertThrows(ObjectFilteredNotFound.class, () -> {
			rezervationService.createRezervation(rezervation);
			});
	}
	
	@Test
	void givenRezervationCreate_whenBookTaken_thenThrowsException() {
		UserEntity user = UserUtil.userTest();
		BookEntity book = BookUtil.bookFive();
		book.setTaken(true);
		RezervationCreateUpdateDto rezervation = new RezervationCreateUpdateDto();
		
		Mockito.when(userRepository.getActivatedUserByUsername(rezervation.getUsername())).thenReturn(user);
		Mockito.when(bookRepository.getBookByTitle(rezervation.getBookTitle())).thenReturn(book);
		Mockito.when(bookRepository.isTaken(Mockito.anyString())).thenReturn(false);

		assertThrows(CustomExceptionMessage.class, () -> {
			rezervationService.createRezervation(rezervation);
			});
		
		
		Mockito.when(bookRepository.isTaken(Mockito.anyString())).thenReturn(true);
		RezervationEntity rezervationEntity = RezervationConverter.toEntityCreate(book, user);
		Mockito.when(rezervationRepository.saveRezervation(rezervationEntity)).thenReturn(rezervationEntity);
		book.setTaken(true);
		Mockito.when(bookRepository.updateBook(book)).thenReturn(book);
		RezervationDto dtoToGet = rezervationService.createRezervation(rezervation);
		
		assertNotNull(dtoToGet);
		assertDoesNotThrow(()->rezervationService.createRezervation(rezervation));
		assertEquals(RezervationDto.class.getTypeName(), dtoToGet.getClass().getTypeName());
	}
	
	@Test
	void givenRezervation_whenDeleteNotFound_thenThrowsException() {
		long id = 1;
		
		Mockito.when(rezervationRepository.findById(id)).thenReturn(null);

		assertThrows(ObjectIdNotFound.class, () -> {
			rezervationService.deleteRezervation(id);
			});
	}
	
	@Test
	void givenRezervation_whenDelete_thenCheckIfReturns() {
		long id = 1;
		RezervationEntity rezervation = RezervationUtil.rezervationOne();
		rezervation.setReturnDate(null);
		
		Mockito.when(rezervationRepository.findById(id)).thenReturn(rezervation);
		

		assertThrows(CustomExceptionMessage.class, () -> {
			rezervationService.deleteRezervation(id);
			});
	}
	
	@Test
	void givenRezervation_whenDelete_thenValidate() {
		long id = 1;
		BookEntity book = BookUtil.bookFive();
		RezervationEntity rezervation = RezervationUtil.rezervationOne();
		rezervation.setReturnDate(LocalDateTime.now());
		
		Mockito.when(rezervationRepository.findById(id)).thenReturn(rezervation);
		Mockito.when(bookRepository.getBookByTitle(rezervation.getBook().getTitle())).thenReturn(book);
		
		BookEntity bookReturned = bookRepository.getBookByTitle(rezervation.getBook().getTitle());
		
		verify(bookRepository).getBookByTitle(rezervation.getBook().getTitle());
		assertNotNull(bookReturned);
		assertEquals(book.getTitle(), bookReturned.getTitle());
	}
	
	
	@Test
	void givenWrondId_whenClose_thenThrowException() {
		long id = 1;
		
		Mockito.when(rezervationRepository.findById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			rezervationService.closeRezervation(id);
		});
	}
	
	@Test
	void givenRezervation_whenUpdate_thenCheckValidations() {
		UserEntity user = UserUtil.userTest();
		BookEntity book = BookUtil.bookThree();
		long id = 1;
		RezervationCreateUpdateDto rezervationDto = new RezervationCreateUpdateDto();
		rezervationDto.setUsername(user.getUsername());
		rezervationDto.setBookTitle(book.getTitle());
		RezervationEntity rezervation = new RezervationEntity();
		rezervation.setBook(book);
		rezervation.setStudent(user);
		rezervation.setId(id);
		
		
		Mockito.when(rezervationRepository.findById(id)).thenReturn(rezervation);
		Mockito.when(userRepository.getActivatedUserByUsername(rezervationDto.getUsername())).thenReturn(null);
		
		assertThrows(UserNotFoundException.class, ()->{
			rezervationService.updateRezervation(id, rezervationDto);
		});
		
		Mockito.when(userRepository.getActivatedUserByUsername(rezervationDto.getUsername())).thenReturn(user);
		Mockito.when(bookRepository.getBookByTitle(rezervationDto.getBookTitle())).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->{
			rezervationService.updateRezervation(id, rezervationDto);
		});
		
		Mockito.when(bookRepository.getBookByTitle(rezervationDto.getBookTitle())).thenReturn(book);
		Mockito.when(bookRepository.isTaken(book.getTitle())).thenReturn(false);
		
		assertThrows(CustomExceptionMessage.class, ()->{
			rezervationService.updateRezervation(id, rezervationDto);
		});
	}
	
}
