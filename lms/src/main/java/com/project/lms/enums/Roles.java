package com.project.lms.enums;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
import static com.project.lms.enums.Permissions.*;

public enum Roles {
	ADMIN(Sets.newHashSet(USER_READ,USER_WRITE,USER_ROLE_READ,USER_ROLE_WRITE)),
	SECRETARY(Sets.newHashSet(BOOK_READ,BOOK_WRITE)),
	STUDENT(Sets.newHashSet(BOOK_READ));
	
	private final Set<Permissions> permissions;
	
	private Roles(Set<Permissions> permissions) {
		this.permissions = permissions;
	}

	public Set<Permissions> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissionss = getPermissions().stream()
						.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
						.collect(Collectors.toSet());
		permissionss.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return permissionss;
	}
	
}
