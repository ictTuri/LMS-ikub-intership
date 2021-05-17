package com.project.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.lms.converter.BookConverter;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.dto.BookDto;
import com.project.lms.entity.BookEntity;
import com.project.lms.exception.MyExcMessages;
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

	// RETURNS ALL BOOKS
	@Override
	public List<BookDto> getAllBooks() {
			List<BookEntity> booksToReturn = bookRepository.getAll();
			return BookConverter.toListDto(booksToReturn);
	}

	// GET BOOK BY ID
	@Override
	public BookDto getBookById(Long id) {
		BookEntity book = bookRepository.getById(id);
		if(book != null) {
			return BookConverter.toDto(book);
		}
		throw new MyExcMessages("Book with given Id does not exist!");
	}

	// CREATE NEW BOOK ON DB
	@Override
	public BookDto createBook(BookCreateUpdateDto book) {
		boolean existBookTitle = bookRepository.checkBookByTitle(book.getTitle());
		if(!existBookTitle) {
			BookEntity bookToCreate = BookConverter.toEntity(book);
			return BookConverter.toDto(bookRepository.saveBook(bookToCreate));
		}
		throw new MyExcMessages("Book with title : "+book.getTitle()+" - already exist");
	}

	// VALIDATED DATA AND UPDATES BOOK TITLE
	@Override
	public BookDto updateBookById(Long id, BookCreateUpdateDto book) {
		BookEntity bookToUpdate = bookRepository.getById(id);
		if(bookToUpdate != null) {
			if(!(book.getTitle().equals(bookToUpdate.getTitle())) && bookRepository.checkBookByTitle(book.getTitle())) {
				throw new MyExcMessages("Book with title: "+book.getTitle()+" -already exist");
			}
			bookToUpdate.setTitle(book.getTitle());
			return BookConverter.toDto(bookRepository.updateBook(bookToUpdate));
		}
		throw new MyExcMessages("Book with given Id does not exist!");
	}

	// DELETE BOOK IF FOUND BY ID
	@Override
	public void deleteBookById(Long id) {
		BookEntity bookToDelete = bookRepository.getById(id);
		if(bookToDelete != null) {
			bookRepository.deleteBook(bookToDelete);
		}
		throw new MyExcMessages("Can not find the book with given Id!");
	}

	public BookEntity getBookById(String title) {
		return bookRepository.getBookByTitle(title);
	}

}
