package com.project.lms.security;

public enum ApplicationUserPermission {
		BOOK_READ("book:read"),
		BOOK_WRITE("book:write"),
	    USER_READ("user:read"),
	    USER_WRITE("user:write"),
		USERROLE_READ("userrole:read"),
		USERROLE_WRITE("userrole:write");
		
	    private final String permission;

	    ApplicationUserPermission(String permission) {
	        this.permission = permission;
	    }

	    public String getPermission() {
	        return permission;
	    }
}
