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
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "roles")
@Document(collection = "roles")
public class RoleEntity {

	@Transient
	public static final String SEQUENCE_NAME = "roles_sequence";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;
	
	@DBRef(lazy = true)
	@JsonBackReference
	@OneToMany(mappedBy = "role")
	private Set<UserRoleEntity> userRoles = new HashSet<>();
	
	@Column(name = "name")
	private String name;
	
	@Override
	public String toString() {
		return "RoleEntity [id=" + id + ", name=" + name + "]";
	}

	public RoleEntity(String name) {
		super();
		this.name = name;
	}

}
	
	
