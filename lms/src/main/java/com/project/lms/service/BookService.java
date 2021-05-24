package com.project.lms.service;

import java.util.List;

import javax.validation.Valid;

import com.project.lms.dto.BookDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.dto.BookCreateUpdateDto;

public interface BookService {
	// Get all books
	List<BookDto> getAllBooks();
	
	// Get book by id
	BookDto getBookById(Long id);
	
	// Create new book from passed object
	BookDto createBook(@Valid BookCreateUpdateDto book);
	
	// Update book by id
	BookDto updateBookById(Long id,@Valid  BookCreateUpdateDto book);
	
	// Delete Book by id
	void deleteBookById(Long id);

	// Put a new Rezervation from logged in Student 
	RezervationDto rezerveBookById(Long id);

	// Return list of Rezervations of the logged in Student
	List<RezervationDto> myRezervedBooks();

	// Return rezervation by id
	RezervationDto myRezervedBookById(long id);
}
