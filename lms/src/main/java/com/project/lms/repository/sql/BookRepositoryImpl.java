package com.project.lms.repository.sql;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.project.lms.model.BookEntity;
import com.project.lms.repository.BookRepository;


@Repository
@Profile("sql")
public class BookRepositoryImpl implements BookRepository {

	private EntityManager em;
	
	public BookRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private static final String TITLE = "title";
	
	@Override
	public List<BookEntity> getAll() {
		final var GET_ALL_BOOKS = "FROM BookEntity ";
		return em.createQuery(GET_ALL_BOOKS, BookEntity.class).getResultList();
	}

	@Override
	public BookEntity getById(Long id) {
		try {
			return em.find(BookEntity.class, id);
		} catch (IllegalArgumentException  e) {
			return null;
		}
	}

	@Override
	public BookEntity saveBook(BookEntity bookToCreate) {
		em.persist(bookToCreate);
		return bookToCreate;
	}

	@Override
	public BookEntity updateBook(BookEntity bookToUpdate) {
		return em.merge(bookToUpdate);
	}
	
	@Override
	public void deleteBook(BookEntity bookToDelete) {
		em.remove(bookToDelete);
	}

	@Override
	public boolean checkBookByTitle(String title) {
		final var BOOK_BY_TITLE = "FROM BookEntity be WHERE be.title = :title";
		TypedQuery<BookEntity> query = em.createQuery(BOOK_BY_TITLE, BookEntity.class)
				.setParameter(TITLE, title);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public BookEntity getBookByTitle(String title) {
		final var BOOK_BY_TITLE = "FROM BookEntity be WHERE be.title = :title";
		TypedQuery<BookEntity> query = em.createQuery(BOOK_BY_TITLE, BookEntity.class)
				.setParameter(TITLE, title);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean isTaken(String bookTitle) {
		final var BOOK_NOT_TAKEN = "FROM BookEntity be WHERE be.title = :title and taken = false";
		TypedQuery<BookEntity> query = em.createQuery(BOOK_NOT_TAKEN, BookEntity.class)
				.setParameter(TITLE, bookTitle);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

}
