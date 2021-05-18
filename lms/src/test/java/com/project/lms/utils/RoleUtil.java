package com.project.lms.utils;

import com.project.lms.model.RoleEntity;

public class RoleUtil {
	public static RoleEntity adminRole() {
		RoleEntity adminRole = new RoleEntity();
		adminRole.setName("ADMIN");
		adminRole.setUserRoles(null);
		return adminRole;
	}
	
	public static RoleEntity secretaryRole() {
		RoleEntity adminRole = new RoleEntity();
		adminRole.setName("SECRETARY");
		adminRole.setUserRoles(null);
		return adminRole;
	}
	
	public static RoleEntity studentRole() {
		RoleEntity adminRole = new RoleEntity();
		adminRole.setName("STUDENT");
		adminRole.setUserRoles(null);
		return adminRole;
	}
	
}
