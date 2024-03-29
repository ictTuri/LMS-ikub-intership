package com.project.lms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
@Document(collection = "users")
public class UserEntity {
	
	@Transient
	public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;

	@Column(name="activated")
	private boolean activated;
	
	@DBRef(lazy = true)
	@JsonBackReference
	@OneToMany(mappedBy = "user")
	private Set<UserRoleEntity> userRoles = new HashSet<>();
	
	@DBRef(lazy = true)
	@JsonBackReference
	@OneToMany(mappedBy = "student")
	private Set<RezervationEntity> rezervations = new HashSet<>();
	
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", username=" + username + ", password=" + password + ", activated=" + activated + "]";
	}

}
