package com.project.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.lms.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long>{

}
