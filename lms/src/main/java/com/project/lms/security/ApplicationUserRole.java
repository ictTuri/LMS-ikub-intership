package com.project.lms.security;

import com.google.common.collect.Sets;

import static com.project.lms.security.ApplicationUserPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum ApplicationUserRole {
		ADMIN(Sets.newHashSet(USERROLE_READ,USERROLE_WRITE,USER_READ,USER_WRITE)),
	    SECRETARY(Sets.newHashSet(BOOK_READ, BOOK_WRITE)),
	    STUDENT(Sets.newHashSet(BOOK_READ));

	    private final Set<ApplicationUserPermission> permissions;

	    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
	        this.permissions = permissions;
	    }

	    public Set<ApplicationUserPermission> getPermissions() {
	        return permissions;
	    }

	    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
	        Set<SimpleGrantedAuthority> userPermissions = getPermissions().stream()
	                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
	                .collect(Collectors.toSet());
	        userPermissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
	        return userPermissions;
	    }
}
