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
import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.service.impl.BookServiceImpl;
import com.project.lms.utils.BookUtil;

@SpringBootTest
class BookServiceTest {
	@InjectMocks
	private BookServiceImpl bookService;

	@Mock
	private BookRepository bookRepository;

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
	void givenBookCreateDto_WhenSave_thenGetSaved() {
		BookCreateUpdateDto bookCreateDto = new BookCreateUpdateDto();
		bookCreateDto.setTitle(bookOne.getTitle());

		Mockito.when(bookRepository.saveBook(bookOne)).thenReturn(bookOne);
		bookDto = BookConverter.toDto(bookOne);
		BookDto createBook = bookService.createBook(bookCreateDto);
		assertEquals(createBook, bookDto);

		verify(bookRepository).saveBook(bookOne);
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


}
