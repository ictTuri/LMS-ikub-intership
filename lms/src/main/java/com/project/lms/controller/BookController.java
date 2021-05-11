package com.project.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.dto.BookDto;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.service.BookService;

@RestController
@RequestMapping("api/v1/book")
public class BookController {
	
	private BookService bookService;
	
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@GetMapping()
	public ResponseEntity<List<BookDto>> getAllBooks(){
		return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBookById(@RequestParam("id") Long id){
		return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateUpdateDto book){
		return new ResponseEntity<>(bookService.createBook(book),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BookDto> updateBookById(@Valid @RequestParam("id") Long id,@RequestBody BookCreateUpdateDto book){
		return new ResponseEntity<>(bookService.updateBookById(id, book),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBookById(@RequestParam("id") Long id){
		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
