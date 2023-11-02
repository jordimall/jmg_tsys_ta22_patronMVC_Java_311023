/**
 * 
 */
package TA22_patronMVC_ejercicio01.TA22_patronMVC_ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.User;

/**
 * 
 */
class UserTest {
	
	User user;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio test modelo User");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Fin test modelo User");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		user = null;
	}

	/**
	 * Test method for {@link models.User#User()}.
	 */
	@Test
	void testUser() {
		String perspect1 = "root";
		String perspect2 = "root";
		
		assertEquals(perspect1, user.getUserName());
		assertEquals(perspect2, user.getPassword());
	}

	/**
	 * Test method for {@link models.User#getUserName()}.
	 */
	@Test
	void testGetUserName() {
		String perspect = "root";
		
		assertEquals(perspect, user.getUserName());
	}

	/**
	 * Test method for {@link models.User#getPassword()}.
	 */
	@Test
	void testGetPassword() {
		String perspect = "root";
		
		assertEquals(perspect, user.getPassword());
	}

}
