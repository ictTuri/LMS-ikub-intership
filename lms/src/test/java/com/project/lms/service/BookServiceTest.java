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

import com.project.lms.converter.BookConverter;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.dto.BookDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.BookEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.impl.BookServiceImpl;
import com.project.lms.utils.BookUtil;


@SpringBootTest
class BookServiceTest {
	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private UserRepository userRepository;

	@Mock
	private RezervationRepository rezervationRepository;

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
		long id = 1;
		
		when(bookRepository.getById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class,()->{bookService.deleteBookById(id);});
	}
	
	@Test
	void givenBook_WhenRezerve_thenThrow() {
		long id = 1;
		BookEntity book = BookUtil.bookFive();
		book.setTaken(true);
		when(bookRepository.getById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class,()->{bookService.rezerveBookById(id);});
		
		when(bookRepository.getById(id)).thenReturn(book);
		assertThrows(CustomExceptionMessage.class,()->{bookService.rezerveBookById(id);});
	}
	
	@Test
	void givenBook_WhenUpdate_thenGoThrough() {
		long id = 1;
		BookEntity book = BookUtil.bookFive();
		BookCreateUpdateDto bookDto = new BookCreateUpdateDto();
		bookDto.setTitle("Three Stones");
		
		Mockito.when(bookRepository.getById(id)).thenReturn(book);
		Mockito.when(bookRepository.checkBookByTitle(bookDto.getTitle())).thenReturn(false);
		Mockito.when(bookRepository.updateBook(book)).thenReturn(book);
		
		BookDto dtoToGet = bookService.updateBookById(id, bookDto);
		assertNotNull(dtoToGet);
		assertEquals(book.getTitle(), dtoToGet.getTitle());
	}
	
	@Test
	void givenId_WhenGet_thenGoThrough() {
		long id = 1;
		BookEntity book = BookUtil.bookFour();
		
		Mockito.when(bookRepository.getById(id)).thenReturn(null);
		
		assertThrows(ObjectIdNotFound.class, ()->bookService.getBookById(id));
		
		Mockito.when(bookRepository.getById(id)).thenReturn(book);
		
		BookDto dtoBook = bookService.getBookById(id);
		
		assertNotNull(dtoBook);
		assertEquals(book.getTitle(), dtoBook.getTitle());
		assertDoesNotThrow(()->bookService.getBookById(id));
	}
	
	@Test
	void givenDto_WhenCreate_thenGoThrough() {
		BookEntity book = BookUtil.bookFour();
		BookCreateUpdateDto dto = new BookCreateUpdateDto();
		dto.setTitle(book.getTitle());
		
		Mockito.when(bookRepository.checkBookByTitle(dto.getTitle())).thenReturn(false);
		BookEntity bookToCreate = BookConverter.toEntity(dto);
		Mockito.when(bookRepository.saveBook(bookToCreate)).thenReturn(book);
	
		BookDto dtoToGet = bookService.createBook(dto);
		assertNotNull(dtoToGet);
		assertEquals(book.getTitle(), dtoToGet.getTitle());
	}
	
}
