package com.project.lms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LmsApplicationTests {

	@Test
	void contextLoads() {
		//this is empty by request
		assertEquals(2, (1+1));
	}

}
