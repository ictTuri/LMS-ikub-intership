package com.project.lms.repository;

import java.util.List;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.lms.model.BookEntity;
import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.utils.BookUtil;
import com.project.lms.utils.UserUtil;

@SpringBootTest
class RezervationRepositoryTest {
	
	@Autowired
	private RezervationRepository rezervationsRepository;
	
	@Autowired
	private	BookRepository bookRepository;
	
	@Autowired
	private	UserRepository userRepository;
	
	@Test
	void givenUser_whenRetrieved_thenGetRezervationData() {
		BookEntity bookOne = BookUtil.bookOne();
		BookEntity bookTwo = BookUtil.bookTwo();
		UserEntity student = UserUtil.userTest();
		bookRepository.saveBook(bookOne);
		bookRepository.saveBook(bookTwo);
		userRepository.saveUser(student);
		
		RezervationEntity rezervationOne = new RezervationEntity();
		rezervationOne.setBook(bookOne);
		rezervationOne.setStudent(student);
		rezervationsRepository.saveRezervation(rezervationOne);
		
		RezervationEntity rezervationTwo = new RezervationEntity();
		rezervationTwo.setBook(bookTwo);
		rezervationTwo.setStudent(student);
		rezervationsRepository.saveRezervation(rezervationTwo);
		
		List<RezervationEntity> myRezervation = rezervationsRepository.myRezervations(student);
		
		Assertions.assertEquals(2, myRezervation.size());
	}
	
	@Test
	void givenRezervation_whenSave_thenGetCreatedRezervation() {
		Integer rezervationSize = rezervationsRepository.findAll().size();
		BookEntity bookOne = BookUtil.bookOne();
		BookEntity bookTwo = BookUtil.bookTwo();
		UserEntity student = UserUtil.userTest();
		bookRepository.saveBook(bookOne);
		bookRepository.saveBook(bookTwo);
		userRepository.saveUser(student);
		
		RezervationEntity rezervationOne = new RezervationEntity();
		rezervationOne.setBook(bookOne);
		rezervationOne.setStudent(student);
		rezervationsRepository.saveRezervation(rezervationOne);
		
		RezervationEntity rezervationTwo = new RezervationEntity();
		rezervationTwo.setBook(bookTwo);
		rezervationTwo.setStudent(student);
		rezervationsRepository.saveRezervation(rezervationTwo);
		
		Assertions.assertEquals(rezervationSize+2, rezervationsRepository.findAll().size());
		Assertions.assertNotNull(rezervationsRepository.myRezervations(student));
	}
	
	@Test
	void givenWrongUser_whenRetrieved_thenGetNoResult() {
		UserEntity user = UserUtil.userAdmin();
		userRepository.saveUser(user);
		
		List<RezervationEntity> rezervations = rezervationsRepository.myRezervations(user);
		
		Assertions.assertEquals(0, rezervations.size());
	}
	
	
	@Test
	void givenRezervation_whenDelete_thenGetNoResult() {
		BookEntity bookOne = BookUtil.bookOne();
		UserEntity student = UserUtil.userTest();
		bookRepository.saveBook(bookOne);
		userRepository.saveUser(student);
		
		RezervationEntity rezervationOne = new RezervationEntity();
		rezervationOne.setBook(bookOne);
		rezervationOne.setStudent(student);
		rezervationsRepository.saveRezervation(rezervationOne);

		rezervationsRepository.deleteRezervation(rezervationOne);
		
		Assertions.assertEquals(0, rezervationsRepository.myRezervations(student).size());
	}
}
