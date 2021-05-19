package com.project.lms.model;

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
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_role")
@NoArgsConstructor
@Document(collection = "user_role")
public class UserRoleEntity {

	@Transient
	public static final String SEQUENCE_NAME = "user_role_sequence";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_role_id")
	private Long id;

	@DBRef(lazy = true)
	@JsonManagedReference
	@ManyToOne()
	@JoinColumn(name = "user_id")  
	private UserEntity user;

	@DBRef(lazy = true)
	@JsonManagedReference
	@ManyToOne()
	@JoinColumn(name = "role_id")  
	private RoleEntity role;

	@Override
	public String toString() {
		return "UserRoleEntity [id=" + id + "]";
	}

}
