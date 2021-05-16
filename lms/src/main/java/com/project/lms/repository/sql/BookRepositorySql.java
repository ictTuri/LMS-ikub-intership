package com.project.lms.repository.sql;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.lms.entity.BookEntity;
import com.project.lms.repository.BookRepository;

@Repository
@Profile(value = "sql")
public interface BookRepositorySql extends BookRepository, JpaRepository<BookEntity, Long>{
	
	public BookEntity findByTitle(String title);
	
	public Optional<BookEntity> findById(Long id);
	
	
	
}
