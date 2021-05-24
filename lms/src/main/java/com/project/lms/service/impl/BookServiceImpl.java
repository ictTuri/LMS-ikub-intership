package com.project.lms.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.lms.converter.BookConverter;
import com.project.lms.converter.RezervationConverter;
import com.project.lms.dto.BookCreateUpdateDto;
import com.project.lms.dto.BookDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.model.BookEntity;
import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.BookService;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	private RezervationRepository rezervationRepository;
	private UserRepository userRepository;

	public BookServiceImpl(BookRepository bookRepository, RezervationRepository rezervationRepository,
			UserRepository userRepository) {
		super();
		this.bookRepository = bookRepository;
		this.rezervationRepository = rezervationRepository;
		this.userRepository = userRepository;
	}

	@Override
	public List<BookDto> getAllBooks() {
			List<BookEntity> booksToReturn = bookRepository.getAll();
			return BookConverter.toListDto(booksToReturn);
	}

	@Override
	public BookDto getBookById(Long id) {
		BookEntity book = bookRepository.getById(id);
		if(book != null) {
			return BookConverter.toDto(book);
		}
		throw new ObjectIdNotFound("Book with given Id does not exist!");
	}

	@Override
	public BookDto createBook(BookCreateUpdateDto book) {
		boolean existBookTitle = bookRepository.checkBookByTitle(book.getTitle());
		if(!existBookTitle) {
			BookEntity bookToCreate = BookConverter.toEntity(book);
			return BookConverter.toDto(bookRepository.saveBook(bookToCreate));
		}
		throw new ObjectFilteredNotFound("Book with title : "+book.getTitle()+" - already exist");
	}

	@Override
	public BookDto updateBookById(Long id, BookCreateUpdateDto book) {
		BookEntity bookToUpdate = bookRepository.getById(id);
		if(bookToUpdate != null) {
			if(!(book.getTitle().equals(bookToUpdate.getTitle())) && bookRepository.checkBookByTitle(book.getTitle())) {
				throw new ObjectFilteredNotFound("Book with title: "+book.getTitle()+" -already exist");
			}
			bookToUpdate.setTitle(book.getTitle());
			return BookConverter.toDto(bookRepository.updateBook(bookToUpdate));
		}
		throw new ObjectIdNotFound("Book with given Id does not exist!");
	}

	@Override
	public void deleteBookById(Long id) {
		BookEntity bookToDelete = bookRepository.getById(id);
		if(bookToDelete != null) {
			bookRepository.deleteBook(bookToDelete);
		}
		throw new ObjectIdNotFound("Can not find the book with given Id!");
	}

	public BookEntity getBookById(String title) {
		return bookRepository.getBookByTitle(title);
	}

	@Override
	public RezervationDto rezerveBookById(Long id) {
		BookEntity bookToRezerve = bookRepository.getById(id);
		if(bookToRezerve != null) {
			if(bookToRezerve.getTaken().equals(false)) {
				RezervationEntity entity = RezervationConverter.toEntityCreate(bookToRezerve, thisUser());
				rezervationRepository.saveRezervation(entity);
				bookToRezerve.setTaken(true);
				bookRepository.updateBook(bookToRezerve);
				return RezervationConverter.toDto(entity);
			}
			throw new CustomExceptionMessage("This Book is already taken!");
		}
		throw new ObjectIdNotFound("Can not find book with id: "+id);
	}

	public UserEntity thisUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return  userRepository.getUserByUsername(username);	
	}

	
	@Override
	public List<RezervationDto> myRezervedBooks() {
		List<RezervationEntity> myRezervations = rezervationRepository.myRezervations(thisUser());
		return RezervationConverter.toListDto(myRezervations);
	}

	@Override
	public RezervationDto myRezervedBookById(long id) {
		List<RezervationEntity> myRezervations = rezervationRepository.myRezervations(thisUser());
		for(RezervationEntity re : myRezervations) {
			if(re.getId().equals(id)) {
				return RezervationConverter.toDto(re);
			}
		}
		throw new ObjectFilteredNotFound("Rezervation with Id: "+id+" Can not be found!");
	}
}
