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
	
	/*
	 * Returns the UserEntity of the logged in user
	 */
	public UserEntity thisUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return  userRepository.getUserByUsername(username);	
	}
	
	/*
	 * Returns the BookEntity by id
	 */
	public BookEntity bookById(long id) {
		return bookRepository.getById(id);
	}
	
	/*
	 * Returns the BookEntity by title
	 */
	public BookEntity bookByTitle(String title) {
		return bookRepository.getBookByTitle(title);
	}
	
	/*
	 * Returns all books from database
	 */
	@Override
	public List<BookDto> getAllBooks() {
			List<BookEntity> booksToReturn = bookRepository.getAll();
			return BookConverter.toListDto(booksToReturn);
	}

	/*
	 * Returns one book by id 
	 * If not find any throws exception
	 */
	@Override
	public BookDto getBookById(Long id) {
		BookEntity book = bookById(id);
		if(book != null) {
			return BookConverter.toDto(book);
		}
		throw new ObjectIdNotFound("Book with given Id does not exist!");
	}

	/*
	 * Persist a new book into database
	 * If same title exist it throws exception
	 */
	@Override
	public BookDto createBook(BookCreateUpdateDto book) {
		boolean existBookTitle = bookRepository.checkBookByTitle(book.getTitle());
		if(!existBookTitle) {
			BookEntity bookToCreate = BookConverter.toEntity(book);
			BookEntity bookToReturn = bookRepository.saveBook(bookToCreate);
			return BookConverter.toDto(bookToReturn);
		}
		throw new ObjectFilteredNotFound("Book with title : "+book.getTitle()+" - already exist");
	}

	/*
	 * Update book by id
	 * If new title already exist it throws exception
	 */
	@Override
	public BookDto updateBookById(Long id, BookCreateUpdateDto book) {
		BookEntity bookToUpdate = bookById(id);
		if(bookToUpdate != null) {
			if(!(book.getTitle().equals(bookToUpdate.getTitle())) && bookRepository.checkBookByTitle(book.getTitle())) {
				throw new CustomExceptionMessage("Book with title: "+book.getTitle()+" -already exist");
			}
			bookToUpdate.setTitle(book.getTitle());
			return BookConverter.toDto(bookRepository.updateBook(bookToUpdate));
		}
		throw new ObjectIdNotFound("Book with given Id does not exist!");
	}

	/*
	 * Deletes a book by id 
	 * If book is rented it throws exception
	 */
	@Override
	public void deleteBookById(Long id) {
		BookEntity bookToDelete = bookById(id);
		if(bookToDelete != null) {
			if(bookToDelete.getTaken().equals(false)) {
				bookRepository.deleteBook(bookToDelete);
			}
			throw new CustomExceptionMessage("Ca not delete . Is Rented by a Student!");
		}
		throw new ObjectIdNotFound("Can not find the book with given Id!");
	}

	/*
	 * Returns book by title
	 * If not found one throws exception
	 */
	public BookEntity getBookById(String title) {
		BookEntity book = bookByTitle(title);
		if(book != null) {
			return book;
		}
		throw new CustomExceptionMessage("Can not find book with title: "+title);
	}

	/*
	 * Create a rezervation for the logged in User
	 * If book by id is already taken it throws exception
	 * if not taken it set taken value to true and create Rezervation
	 */
	@Override
	public RezervationDto rezerveBookById(Long id) {
		BookEntity bookToRezerve = bookById(id);
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

	/*
	 * Returns a list of rezervations for the user
	 */
	@Override
	public List<RezervationDto> myRezervedBooks() {
		List<RezervationEntity> myRezervations = rezervationRepository.myRezervations(thisUser());
		return RezervationConverter.toListDto(myRezervations);
	}

	/*
	 * Returns a rezervation by id for the Logged in user
	 */
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
