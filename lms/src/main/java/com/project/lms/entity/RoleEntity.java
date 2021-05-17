package com.project.lms.entity;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@Document(collection = "roles")
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer id;
	
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

}
	
	
