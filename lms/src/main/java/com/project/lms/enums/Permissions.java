package com.project.lms.enums;


public enum Permissions {
	BOOK_READ("book:read"),
	BOOK_WRITE("book:write"),
	USER_READ("user:read"),
	USER_WRITE("user:write"),
	USER_ROLE_READ("user_role:read"),
	USER_ROLE_WRITE("user_role:write");
	
	private String permission;

	private Permissions(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
}
