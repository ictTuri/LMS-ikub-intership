package com.project.lms.service;

import java.util.List;

import com.project.lms.dto.BookDto;
import com.project.lms.dto.BookCreateUpdateDto;

public interface BookService {
	// Get all books
	List<BookDto> getAllBooks();
	
	// Get book by id
	BookDto getBookById(Long id);
	
	// Create new book from passed object
	BookDto createBook(BookCreateUpdateDto book);
	
	// Update book by id
	BookDto updateBookById(Long id, BookCreateUpdateDto book);
	
	// Delete Book by id
	void deleteBookById(Long id);
}
