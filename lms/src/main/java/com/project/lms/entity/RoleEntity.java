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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@Table(name = "roles")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Integer id;
	
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
	
	
