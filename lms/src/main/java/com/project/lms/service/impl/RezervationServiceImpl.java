package com.project.lms.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.project.lms.converter.RezervationConverter;
import com.project.lms.dto.RezervationCreateUpdateDto;
import com.project.lms.dto.RezervationDto;
import com.project.lms.exception.CustomExceptionMessage;
import com.project.lms.exception.ObjectFilteredNotFound;
import com.project.lms.exception.ObjectIdNotFound;
import com.project.lms.exception.UserNotFoundException;
import com.project.lms.model.BookEntity;
import com.project.lms.model.RezervationEntity;
import com.project.lms.model.UserEntity;
import com.project.lms.repository.BookRepository;
import com.project.lms.repository.RezervationRepository;
import com.project.lms.repository.UserRepository;
import com.project.lms.service.RezervationService;

@Service
@Transactional
public class RezervationServiceImpl implements RezervationService {

	private RezervationRepository rezervationRepository;
	private UserRepository userRepository;
	private BookRepository bookRepository;

	public RezervationServiceImpl(RezervationRepository rezervationRepository, UserRepository userRepository,
			BookRepository bookRepository) {
		super();
		this.rezervationRepository = rezervationRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}
	
	/*
	 * Returns the BookEntity by title
	 */
	public BookEntity bookByTitle(String title) {
		return bookRepository.getBookByTitle(title);
	}
	
	/*
	 * Return Rezervation by id 
	 */
	public RezervationEntity rezervationById(long id) {
		return rezervationRepository.findById(id);
	}
	
	/*
	 * returns all rezervations
	 * If non it returns an empty list
	 */
	@Override
	public List<RezervationDto> showAllRezervation() {
		return RezervationConverter.toListDto(rezervationRepository.findAll());
	}

	/*
	 * Returns a rezervation by id
	 * If id invalid it throws exception
	 */
	@Override
	public RezervationDto showRezervationById(long id) {
		RezervationEntity rezervation = rezervationById(id);
		if (rezervation != null) {
			return RezervationConverter.toDto(rezervation);
		}
		throw new ObjectIdNotFound("Rezervation with id: " + id + " can not be found");
	}

	/*
	 * Create new rezervation
	 * If book taken, username or book not found
	 * It throws Exception
	 */
	@Override
	public RezervationDto createRezervation(RezervationCreateUpdateDto rezervation) {
		UserEntity student = userRepository.getActivatedUserByUsername(rezervation.getUsername());
		if (student != null) {
			BookEntity book = bookByTitle(rezervation.getBookTitle());
			if (book != null) {
				boolean notTaken = bookRepository.isTaken(book.getTitle());
				if (notTaken) {
					RezervationEntity entity = RezervationConverter.toEntityCreate(book, student);
					rezervationRepository.saveRezervation(entity);
					book.setTaken(true);
					bookRepository.updateBook(book);
					return RezervationConverter.toDto(entity);
				}
				throw new CustomExceptionMessage("This book is already taken");
			}
			throw new ObjectFilteredNotFound("Can not find book with title: " + rezervation.getBookTitle());
		}
		throw new UserNotFoundException("Username: " + rezervation.getUsername() + " not found or deactivated !");
	}

	/*
	 * Update rezervation
	 * It takes rzervation by id if exist 
	 * And update user and book
	 */
	@Override
	public RezervationDto updateRezervation(long id, @Valid RezervationCreateUpdateDto rezervation) {
		RezervationEntity rezervationForUpdate = rezervationById(id);
		if (rezervationForUpdate != null) {
			UserEntity student = userRepository.getActivatedUserByUsername(rezervation.getUsername());
			if (student != null) {
				BookEntity book = bookByTitle(rezervation.getBookTitle());
				if (book != null) {
					boolean notTaken = bookRepository.isTaken(book.getTitle());
					return checkIfBookTaken(rezervationForUpdate, student, book, notTaken);
				}
				throw new ObjectIdNotFound("Can not find book with title: " + rezervation.getBookTitle());
			}
			throw new UserNotFoundException("Username: " + rezervation.getUsername() + " not found or deactivated !");
		}
		throw new ObjectIdNotFound("Can not find rezervation with id: " + id);
	}

	/*
	 * Performs the update based on validations required
	 */
	public RezervationDto checkIfBookTaken(RezervationEntity rezervationForUpdate, UserEntity student, BookEntity book,
			boolean notTaken) {
		if (notTaken) {
			// If new book is not taken than change to taken on new updated rezervation
			if (book.getTitle().equalsIgnoreCase(rezervationForUpdate.getBook().getTitle())) {
				// Checks if new book is the same as the previous one
				RezervationEntity entity = rezervationRepository.updateRezervation(RezervationConverter.toEntityCreate(book, student));
				book.setTaken(true);
				bookRepository.updateBook(book);
				return RezervationConverter.toDto(entity);
			}
			// If new book is different then update new one and the previous one as returned
			BookEntity bookNotTaken = bookByTitle(rezervationForUpdate.getBook().getTitle());
			bookNotTaken.setTaken(false);
			bookRepository.updateBook(bookNotTaken);
			RezervationEntity entity = rezervationRepository.updateRezervation(RezervationConverter.toEntityCreate(book, student));
			book.setTaken(true);
			bookRepository.updateBook(book);
			return RezervationConverter.toDto(entity);
		}
		throw new CustomExceptionMessage("This book is already taken");
	}

	/*
	 * Deletes a Rezervation
	 * If Rezervation does not have a return date yet
	 * It Throws exception
	 */
	@Override
	public void deleteRezervation(long id) {
		RezervationEntity rezervationToDelete = rezervationById(id);
		if (rezervationToDelete != null) {
			if (rezervationToDelete.getReturnDate() != null) {
				BookEntity book = bookByTitle(rezervationToDelete.getBook().getTitle());
				book.setTaken(false);
				bookRepository.updateBook(book);
				rezervationRepository.deleteRezervation(rezervationToDelete);
			}
			throw new CustomExceptionMessage("This Rezervation is not closed yet. Book is not returned!");
		}
		throw new ObjectIdNotFound("Can not find Rezervation with id: " + id);
	}

	/*
	 * Closes a Rezervation
	 * It sets a Return date and mark book as not taken
	 * If rezervation is not found by id it throws exception
	 */
	@Override
	public RezervationDto closeRezervation(long id) {
		RezervationEntity rezervationToClose = rezervationById(id);
		if(rezervationToClose != null) {
			BookEntity book = bookByTitle(rezervationToClose.getBook().getTitle());
			book.setTaken(false);
			rezervationToClose.setReturnDate(LocalDateTime.now());
			rezervationRepository.updateRezervation(rezervationToClose);
			bookRepository.updateBook(book);
			return RezervationConverter.toDto(rezervationToClose);
		}
		throw new ObjectIdNotFound("Can not find rezervation with given id");
	}
}
