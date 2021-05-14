package com.project.lms.enums;

public enum Roles {
	ADMIN("ADMIN"),
	SECRETARY("SECRETARY"),
	STUDENT("STUDENT");
	
	String value;

	private Roles(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
