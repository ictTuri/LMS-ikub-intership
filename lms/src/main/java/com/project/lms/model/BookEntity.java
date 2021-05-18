package com.project.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Entity
@Table(name = "books")
@Document(collection = "books")
public class BookEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "books_sequence";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String title;
}
