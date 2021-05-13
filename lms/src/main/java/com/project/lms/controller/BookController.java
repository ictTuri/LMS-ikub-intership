package com.project.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.dto.BookDto;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.service.BookService;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
	
	private BookService bookService;
	
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@GetMapping()
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_STUDENT')")
	public ResponseEntity<List<BookDto>> showAllBooks(){
		return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_STUDENT')")
	public ResponseEntity<BookDto> showBookById(@PathVariable("id") Long id){
		return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasAuthority('book:write')")
	public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateUpdateDto book){
		return new ResponseEntity<>(bookService.createBook(book),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('book:write')")
	public ResponseEntity<BookDto> updateBookById(@Valid @PathVariable("id") long id,@RequestBody BookCreateUpdateDto book){
		return new ResponseEntity<>(bookService.updateBookById(id, book),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('book:write')")
	public ResponseEntity<Void> deleteBookById(@PathVariable("id") long id){
		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
