package com.project.lms.repository;


import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.lms.model.BookEntity;
import com.project.lms.repository.sql.BookRepositoryImpl;
import com.project.lms.utils.BookUtil;

@SpringBootTest
@Transactional
@ActiveProfiles("sql")
class BookRepositoryTest {

	@Autowired
	private BookRepositoryImpl bookRepository;
	
	@Test
	void givenTitle_whenRetrieved_thenGetBookData() {
		BookEntity bookOne = BookUtil.bookOne();
		BookEntity bookTwo = BookUtil.bookTwo();
		
		bookRepository.saveBook(bookOne);
		bookRepository.saveBook(bookTwo);
		
		
		String title = "Death Star";
		
		BookEntity bookRetrieved = bookRepository.getBookByTitle(title);
		
		Assertions.assertEquals(title, bookRetrieved.getTitle());
	}
	
	@Test
	void givenBook_whenUpdate_thenGetUpdatedBook() {
		BookEntity bookThree = BookUtil.bookThree();
		bookRepository.saveBook(bookThree);
		
		
		BookEntity bookId = bookRepository.getBookByTitle(bookThree.getTitle());
		bookThree.setTitle("Day Light");
		bookRepository.updateBook(bookThree);
		
		Assertions.assertEquals("Day Light", bookRepository.getById(bookId.getId()).getTitle());
	}
	
	@Test
	void givenBook_whenSave_thenGetCreatedBook() {
		Integer bookSize = bookRepository.getAll().size();
		BookEntity bookFour = BookUtil.bookFour();
		BookEntity bookFive = BookUtil.bookFive();

		bookRepository.saveBook(bookFour);
		bookRepository.saveBook(bookFive);
		
		Assertions.assertEquals(bookSize+2, bookRepository.getAll().size());
		Assertions.assertNotNull(bookRepository.getBookByTitle("Red Vision"));
	}
	
	@Test
	void givenWrongTitle_whenRetrieved_thenGetNoResult() {
		String title = "3 Year Vacation";
		
		BookEntity bookRetrieved = bookRepository.getBookByTitle(title);
		
		Assertions.assertNull(bookRetrieved);
	}
	
	
	@Test
	void givenBook_whenDelete_thenGetNoResult() {
		BookEntity bookOne = BookUtil.bookOne();
		bookRepository.saveBook(bookOne);

		bookRepository.deleteBook(bookOne);
		
		Assertions.assertNull(bookRepository.getBookByTitle(bookOne.getTitle()));
	}

}
