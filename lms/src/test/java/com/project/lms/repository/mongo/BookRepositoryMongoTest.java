package com.project.lms.repository.mongo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.lms.model.BookEntity;
import com.project.lms.utils.BookUtil;

@SpringBootTest
@ActiveProfiles("mongo")
class BookRepositoryMongoTest {

	@Autowired
	private BookRepositoryMongo bookRepository;
	
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
		
		BookEntity bookTitle = bookRepository.getBookByTitle(bookThree.getTitle());
		bookTitle.setTitle("Day Light");
		bookRepository.updateBook(bookTitle);
		
		Assertions.assertEquals("Day Light", bookRepository.getById(bookTitle.getId()).getTitle());
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
		BookEntity thisBook = new BookEntity();
		thisBook.setTaken(false);
		thisBook.setTitle("Unique Title");
		bookRepository.saveBook(thisBook);
		BookEntity bookToDelete = bookRepository.getBookByTitle("Unique Title");
		
		bookRepository.deleteBook(bookToDelete);
		
		Assertions.assertNull(bookRepository.getBookByTitle(thisBook.getTitle()));
	}
	
	@Test
	void givenTitle_whenCheckByTitle_thenGetBoolean() {
		String title = "Unique Title";
		
		Boolean doesNotExist = bookRepository.checkBookByTitle(title);
		assertFalse(doesNotExist);
		
		title = "Hellbringer";
		Boolean exist = bookRepository.checkBookByTitle(title);
		
		assertTrue(exist);
	}
	
	@Test
	void givenTitle_whenCheckIfTaken_thenGetBoolean() {
		String title = "Hellbringer";
		
		Boolean notTaken = bookRepository.isTaken(title);
		assertFalse(notTaken);
	}

}
