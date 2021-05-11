package com.project.lms.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class UserEntity {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private List<RoleEntity> roles;
	private boolean active;
	private LocalDateTime createdDate;
}
