package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;

@Repository
@Profile(value = "mongo")
public class BookRepositoryMongo implements BookRepository{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<BookEntity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookEntity getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookEntity saveBook(BookEntity bookToCreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookEntity updateBook(BookEntity bookToUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(BookEntity bookToDelete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkBookByTitle(String title) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BookEntity getBookByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
