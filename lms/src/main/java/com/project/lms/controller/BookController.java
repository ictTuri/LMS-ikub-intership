package com.project.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.project.lms.dto.RezervationDto;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.service.BookService;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
	
	private static final Logger logger = LogManager.getLogger(BookController.class);
	
	private BookService bookService;
	
	public BookController(BookService bookService) {
		super();
		this.bookService = bookService;
	}

	@GetMapping()
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_STUDENT')")
	public ResponseEntity<List<BookDto>> showAllBooks(){
		logger.info("Viewing all book!");
		return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_STUDENT')")
	public ResponseEntity<BookDto> showBookById(@PathVariable("id") Long id){
		logger.info("Viewing book with id: {}",id);
		return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK);
	}
	
	@PostMapping()
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateUpdateDto book){
		logger.info("Creating new book with title: {}",book.getTitle());
		return new ResponseEntity<>(bookService.createBook(book),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY')")
	public ResponseEntity<BookDto> updateBookById(@Valid @PathVariable("id") long id,@RequestBody BookCreateUpdateDto book){
		logger.info("Updating book with id: {}, setting title: {}",id,book.getTitle());
		return new ResponseEntity<>(bookService.updateBookById(id, book),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_SECRETARY','ROLE_ADMIN')")
	public ResponseEntity<Void> deleteBookById(@PathVariable("id") long id){
		logger.info("Deleting book with id: {}",id);
		bookService.deleteBookById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/{id}/rezerve")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<RezervationDto> rezerveBookById(@PathVariable("id") Long id){
		logger.info("Renting book with id: {}",id);
		return new ResponseEntity<>(bookService.rezerveBookById(id),HttpStatus.CREATED);
	}
	
	@GetMapping("/mybooks")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<List<RezervationDto>> myRezervedBooks(){
		logger.info("Getting Student rezervations");
		return new ResponseEntity<>(bookService.myRezervedBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/mybooks/{id}")
	@PreAuthorize("hasRole('ROLE_STUDENT')")
	public ResponseEntity<RezervationDto> myRezervedBookById(@PathVariable("id") long id){
		logger.info("Getting Student rezervation by id: {}",id);
		return new ResponseEntity<>(bookService.myRezervedBookById(id),HttpStatus.OK);
	}
}
