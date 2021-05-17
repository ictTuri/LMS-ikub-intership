package com.project.lms.repository;

import java.util.List;

import com.project.lms.entity.BookEntity;

public interface BookRepository{

	List<BookEntity> getAll();

	BookEntity getById(Long id);

	BookEntity saveBook(BookEntity bookToCreate);
	
	BookEntity updateBook(BookEntity bookToUpdate);

	void deleteBook(BookEntity bookToDelete);

	boolean checkBookByTitle(String title);

	BookEntity getBookByTitle(String title);

}
