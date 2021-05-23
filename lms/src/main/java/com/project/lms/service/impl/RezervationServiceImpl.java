package com.project.lms.service.impl;

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

	@Override
	public List<RezervationDto> showAllRezervation() {
		return RezervationConverter.toListDto(rezervationRepository.findAll());
	}

	@Override
	public RezervationDto showRezervationById(long id) {
		RezervationEntity rezervation = rezervationRepository.findById(id);
		if (rezervation != null) {
			return RezervationConverter.toDto(rezervation);
		}
		throw new ObjectIdNotFound("Rezervation with id: " + id + " can not be found");
	}

	@Override
	public RezervationDto createRezervation(RezervationCreateUpdateDto rezervation) {
		UserEntity student = userRepository.getActivatedUserByUsername(rezervation.getUsername());
		if (student != null) {
			BookEntity book = bookRepository.getBookByTitle(rezervation.getBookTitle());
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

	@Override
	public RezervationDto updateRezervation(long id, @Valid RezervationCreateUpdateDto rezervation) {
		RezervationEntity rezervationForUpdate = rezervationRepository.findById(id);
		if (rezervationForUpdate != null) {
			UserEntity student = userRepository.getActivatedUserByUsername(rezervation.getUsername());
			if (student != null) {
				BookEntity book = bookRepository.getBookByTitle(rezervation.getBookTitle());
				if (book != null) {
					boolean notTaken = bookRepository.isTaken(book.getTitle());
					if (notTaken) {
						if (book.getTitle().equalsIgnoreCase(rezervationForUpdate.getBook().getTitle())) {
							RezervationEntity entity = rezervationRepository
									.updateRezervation(RezervationConverter.toEntityCreate(book, student));
							book.setTaken(true);
							bookRepository.updateBook(book);
							return RezervationConverter.toDto(entity);
						}
						BookEntity bookNotTaken = bookRepository
								.getBookByTitle(rezervationForUpdate.getBook().getTitle());
						bookNotTaken.setTaken(false);
						bookRepository.updateBook(bookNotTaken);
						RezervationEntity entity = rezervationRepository
								.updateRezervation(RezervationConverter.toEntityCreate(book, student));
						book.setTaken(true);
						bookRepository.updateBook(book);
						return RezervationConverter.toDto(entity);
					}
					throw new CustomExceptionMessage("This book is already taken");
				}
				throw new ObjectIdNotFound("Can not find book with title: " + rezervation.getBookTitle());
			}
			throw new UserNotFoundException("Username: " + rezervation.getUsername() + " not found or deactivated !");
		}
		throw new ObjectIdNotFound("Can not find rezervation with id: " + id);
	}

	@Override
	public void deleteRezervation(long id) {
		RezervationEntity rezervationToDelete = rezervationRepository.findById(id);
		if(rezervationToDelete != null) {
			if(rezervationToDelete.getReturnDate()!=null) {
				BookEntity book = bookRepository.getBookByTitle(rezervationToDelete.getBook().getTitle());
				book.setTaken(false);
				bookRepository.updateBook(book);
				rezervationRepository.deleteRezervation(rezervationToDelete);
			}
			throw new CustomExceptionMessage("This Rezervation is not closed yet. Book is not returned!");
		}
		throw new ObjectIdNotFound("Can not find Rezervation with id: "+id);
	}

	
//	@Override
//	public UserEntity getUser() {
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		return  userRepository.getUserByUsername(username);	
//	}

}
