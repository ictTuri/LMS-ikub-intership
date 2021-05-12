package com.project.lms.enums;

public enum Roles {
	ADMIN("ROLE_ADMIN"),
	SECRETARY("ROLE_SECRETARY"),
	STUDENT("ROLE_STUDENT");
	
	String value;

	private Roles(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
