package com.project.lms.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.dto.BookDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.BookEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.service.impl.BookServiceImpl;
import com.project.lms.utils.BookUtil;

@SpringBootTest
class BookServiceTest {
	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepository bookRepository;

	@Mock
	private RezervationRepository rezervationRepository;
	
	@Mock
	private SecurityContext securityContext;
	
	@Mock
	private Authentication authentication;

	private BookEntity bookOne, BookTwo;
	private BookDto bookDto;

	@BeforeEach
	public void setup() {
		bookOne = BookUtil.bookOne();
		BookTwo = BookUtil.bookTwo();
		bookDto = new BookDto();
	}

	@Test
	void givenBookList_whenGetThem_validateSize() {
		List<BookEntity> books = new ArrayList<>();
		books.add(bookOne);
		books.add(BookTwo);

		when(bookRepository.getAll()).thenReturn(books);
		int size = bookService.getAllBooks().size();
		assertEquals(2, size);
		verify(bookRepository).getAll();

	}

	@Test
	void givenBookOn_WhenAskedByAnyTitle_thenGetThisBook() {
		bookDto.setTitle("Programming in Java");
		bookOne.setTitle(bookDto.getTitle());

		Mockito.when(bookRepository.getBookByTitle(org.mockito.ArgumentMatchers.anyString())).thenReturn(bookOne);
		BookEntity findByTitle = bookService.getBookById("programming language");
		assertNotNull(findByTitle);
		assertEquals("Programming in Java", findByTitle.getTitle());

	}
	
	@Test
	void givenNewTitle_WhenUpdate_thenThrowException() {
		long id = 1;
		BookEntity bookEntity = BookUtil.bookOne();
		bookEntity.setId(id);
		BookCreateUpdateDto book = new BookCreateUpdateDto();
		book.setTitle("Hello World");

		
		Mockito.when(bookRepository.getById(id)).thenReturn(bookEntity);
		Mockito.when(bookRepository.checkBookByTitle(book.getTitle())).thenReturn(true);
		
		assertThrows(CustomExceptionMessage.class, ()->{bookService.updateBookById(id, book);});
	}
	
	@Test
	void givenNewBook_WhenUpdate_thenThrowException() {
		BookCreateUpdateDto book = new BookCreateUpdateDto();
		long id = 1;
		
		Mockito.when(bookRepository.getById(id)).thenReturn(null);
		assertThrows(ObjectIdNotFound.class, ()->{bookService.updateBookById(id, book);});
	}
	
	@Test
	void givenNewBook_WhenCreate_thenCheckExistBook() {
		BookCreateUpdateDto book = new BookCreateUpdateDto();
		book.setTitle("Guardian of Hell");
		Mockito.when(bookRepository.checkBookByTitle(book.getTitle())).thenReturn(true);
		
		assertThrows(ObjectFilteredNotFound.class, ()->{bookService.createBook(book);});
		verify(bookRepository).checkBookByTitle(book.getTitle());
	}
	
	@Test
	void givenBookId_WhenDelete_thenThrow() {
		when(bookRepository.getById(Mockito.anyLong())).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class,()->{bookService.deleteBookById(Long.valueOf(2));});
	}

}
