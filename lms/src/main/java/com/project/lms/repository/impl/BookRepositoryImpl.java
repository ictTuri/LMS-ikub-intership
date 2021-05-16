package com.project.lms.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private EntityManager em;
	
	public BookRepositoryImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private static final String GET_ALL_BOOKS = "FROM BookEntity ";
	private static final String BOOK_BY_TITLE = "FROM BookEntity be WHERE be.title = :title";
	
	@Override
	public List<BookEntity> getAll() {
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
		TypedQuery<BookEntity> query = em.createQuery(BOOK_BY_TITLE, BookEntity.class)
				.setParameter("title", title);
		try {
			return query.getResultList().get(0) != null;
		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	@Override
	public BookEntity getBookByTitle(String title) {
		TypedQuery<BookEntity> query = em.createQuery(BOOK_BY_TITLE, BookEntity.class)
				.setParameter("title", title);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


}
