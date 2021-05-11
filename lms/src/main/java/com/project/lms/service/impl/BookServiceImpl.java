package com.project.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.lms.converter.BookConverter;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.dto.BookDto;
import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.service.BookService;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookDto> getAllBooks() {
		List<BookEntity> booksToReturn = bookRepository.findAll();
		return BookConverter.toListDto(booksToReturn);
	}

	@Override
	public BookDto getBookById(Long id) {
		return BookConverter.toDto(bookRepository.getOne(id));
	}

	@Override
	public BookDto createBook(BookCreateUpdateDto book) {
		BookEntity bookToCreate = BookConverter.toEntity(book);
		return BookConverter.toDto(bookRepository.save(bookToCreate));
	}

	@Override
	public BookDto updateBookById(Long id, BookCreateUpdateDto book) {
		BookEntity bookToUpdate = bookRepository.getOne(id);
		return BookConverter.toDto(bookRepository.save(bookToUpdate));
	}

	@Override
	public void deleteBookById(Long id) {
		BookEntity bookToDelete = bookRepository.getOne(id);
		bookRepository.delete(bookToDelete);
	}


}
