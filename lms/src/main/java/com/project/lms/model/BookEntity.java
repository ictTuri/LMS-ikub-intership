package com.project.lms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	@Column(name = "book_id")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@DBRef(lazy = true)
	@JsonBackReference
	@OneToMany(mappedBy = "book")
	private Set<RezervationEntity> rezervations = new HashSet<>();

	@Column(name = "taken")
	private Boolean taken;
	
}
