package com.project.lms.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "rezervations")
public class RezervationEntity {

	@Transient
	public static final String SEQUENCE_NAME = "rezervation_sequence";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rezervation_id")
	private Long id;
	
	@DBRef(lazy = true)
	@JsonManagedReference
	@ManyToOne()
	@JoinColumn(name = "student_id")
	private UserEntity student;
	
	@DBRef(lazy = true)
	@JsonManagedReference
	@ManyToOne()
	@JoinColumn(name = "book_id") 
	private BookEntity book;
	
	@Column(name = "rezervation_date")
	private LocalDateTime rezervationDate;
	
	@Column(name = "return_date")
	private LocalDateTime returnDate;
}
