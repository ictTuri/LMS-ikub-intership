package com.project.lms.repository.mongo;

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
class RezervationRepositoryMongoTest {

	@Autowired
	private BookRepositoryMongo bookRepository;

	@Autowired
	private RezervationRepositoryMongo rezervationRepository;
	
	@Autowired
	private UserRepositoryMongo userRepository;

	@Test
	void givenUser_whenRetrieved_thenGetRezervationData() {
		BookEntity bookOne = BookUtil.bookOne();
		BookEntity bookTwo = BookUtil.bookTwo();
		UserEntity student = UserUtil.userTest();
		userRepository.saveUser(student);
		bookRepository.saveBook(bookOne);
		bookRepository.saveBook(bookTwo);
		RezervationEntity rezervationOne = new RezervationEntity();
		rezervationOne.setBook(bookOne);
		rezervationOne.setStudent(student);
		rezervationRepository.saveRezervation(rezervationOne);		
		RezervationEntity rezervationTwo = new RezervationEntity();
		rezervationTwo.setBook(bookTwo);
		rezervationTwo.setStudent(student);
		rezervationRepository.saveRezervation(rezervationTwo);

		List<RezervationEntity> myRezervation = rezervationRepository.myRezervations(student);

		Assertions.assertEquals(2, myRezervation.size());;
	}
	
	@Test
	void givenRezervation_whenSave_thenGetCreatedRezervation() {
		Integer rezervationSize = rezervationRepository.findAll().size();
		BookEntity bookFour = BookUtil.bookFour();
		BookEntity bookFive = BookUtil.bookFive();
		UserEntity user = userRepository.getUserByUsername("test");
		bookRepository.saveBook(bookFour);
		bookRepository.saveBook(bookFive);
		RezervationEntity rezervationOne = new RezervationEntity();
		rezervationOne.setBook(bookFour);
		rezervationOne.setStudent(user);
		rezervationRepository.saveRezervation(rezervationOne);		
		RezervationEntity rezervationTwo = new RezervationEntity();
		rezervationTwo.setBook(bookFive);
		rezervationTwo.setStudent(user);
		rezervationRepository.saveRezervation(rezervationTwo);
		
		Assertions.assertEquals(rezervationSize+2, rezervationRepository.findAll().size());
		Assertions.assertNotNull(rezervationRepository.myRezervations(user));
	}

	@Test
	void givenWrongId_whenRetrieved_thenGetNoResult() {				
		RezervationEntity rezervation = rezervationRepository.findById(34);

		Assertions.assertNull(rezervation);
	}
	
	@Test
	void givenRezervation_whenUpdate_thenGetUpdatedRezervation() {
		BookEntity bookOne = BookUtil.bookOne();
		UserEntity user = UserUtil.userTest();
		userRepository.saveUser(user);
		bookRepository.saveBook(bookOne);
		RezervationEntity rezervationOne = new RezervationEntity();
		rezervationOne.setBook(bookOne);
		rezervationOne.setStudent(user);
		rezervationRepository.saveRezervation(rezervationOne);		
		
		BookEntity bookThree = BookUtil.bookThree();
		bookRepository.saveBook(bookThree);
		RezervationEntity rezervation = rezervationRepository.findById(1);
		rezervation.setBook(bookThree);
		
		rezervationRepository.updateRezervation(rezervation);
		
		Assertions.assertEquals("Night Call", rezervationRepository.findById(1).getBook().getTitle());
	}

	@Test
	void givenRezervation_whenDelete_thenGetNoResult() {
		rezervationRepository.deleteRezervation(rezervationRepository.findById(1));

		Assertions.assertNull(rezervationRepository.findById(1));
	}

}
