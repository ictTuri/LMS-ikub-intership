package com.project.lms.enums;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RolesTest{

	@Test
	void givenString_whenPassedtoEnum_getCorrectValue() {
		String role = "stuDent";
		assertEquals(role.toUpperCase(), Roles.STUDENT.value);
	}
	
	@Test
	void askingRole_whenGetValue_validate() {
		String role = Roles.ADMIN.getValue();
		assertEquals("ADMIN", role);
	}
}
