package com.project.lms.repository.mongo;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;

@Repository
@Profile(value = "mongo")
public interface BookRepositoryMongo extends BookRepository, MongoRepository<BookEntity, Long>{

}
