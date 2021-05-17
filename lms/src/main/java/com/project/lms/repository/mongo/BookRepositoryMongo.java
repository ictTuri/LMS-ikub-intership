package com.project.lms.repository.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;

@Repository
@Profile(value = "mongo")
public class BookRepositoryMongo implements BookRepository{

	@Autowired
	private MongoTemplate mt;
	
	@Override
	public List<BookEntity> getAll() {
		return mt.findAll(BookEntity.class);
	}

	@Override
	public BookEntity getById(Long id) {
		return mt.findById(id, BookEntity.class);
	}

	@Override
	public BookEntity saveBook(BookEntity bookToCreate) {
		return mt.insert(bookToCreate);
	}

	@Override
	public BookEntity updateBook(BookEntity bookToUpdate) {
		return mt.save(bookToUpdate, "books");
	}

	@Override
	public void deleteBook(BookEntity bookToDelete) {
		//Deletes book from DB
		mt.remove(bookToDelete, "books");
	}

	@Override
	public boolean checkBookByTitle(String title) {
		Query query = new Query()
				.addCriteria(Criteria.where("title").is(title));
			return mt.findOne(query, BookEntity.class) != null;
	}

	@Override
	public BookEntity getBookByTitle(String title) {
		Query query = new Query()
				.addCriteria(Criteria.where("title").is(title));
		return mt.findOne(query, BookEntity.class);
	}

}
