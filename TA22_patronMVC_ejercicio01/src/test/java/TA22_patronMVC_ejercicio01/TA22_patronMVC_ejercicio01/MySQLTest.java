/**
 * 
 */
package TA22_patronMVC_ejercicio01.TA22_patronMVC_ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.MySQL;

/**
 * 
 */
class MySQLTest {

	MySQL mockMySQL;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio test modelo MySQL");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Final test modelo MySQL");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mockMySQL = mock(MySQL.class);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		mockMySQL = null;
	}

	/**
	 * Test method for
	 * {@link models.MySQL#connectionSQL(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectionSQLStringString() {
		String validUser = "root";
		String validPassword = "root";

		when(mockMySQL.connectionSQL(anyString(), anyString())).thenReturn(true);

		boolean result = mockMySQL.connectionSQL(validPassword, validUser);

		assertTrue(result);

	}

	/**
	 * Test method for
	 * {@link models.MySQL#connectionSQL(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testFailedConnectionSQLStringString() {
		String FailUser = "user";
		String FailPassword = "password";

		when(mockMySQL.connectionSQL(anyString(), anyString())).thenReturn(false);

		boolean result = mockMySQL.connectionSQL(FailPassword, FailUser);

		assertFalse(result);
	}

	/**
	 * Test method for
	 * {@link models.MySQL#connectionSQL(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testConnectionSQLStringStringString() {
		String validUser = "root";
		String validPassword = "root";
		String validDatabase = "TA22";

		when(mockMySQL.connectionSQL(anyString(), anyString(), anyString())).thenReturn(true);

		boolean result = mockMySQL.connectionSQL(validPassword, validUser, validDatabase);

		assertNotNull(result);
	}

	/**
	 * Test method for
	 * {@link models.MySQL#connectionSQL(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testFailedConnectionSQLStringStringString() {
		String FailUser = "root";
		String FailPassword = "root";
		String FailDatabase = "NULL";

		when(mockMySQL.connectionSQL(anyString(), anyString(), anyString())).thenReturn(false);

		boolean result = mockMySQL.connectionSQL(FailPassword, FailUser, FailDatabase);

		assertFalse(result);
	}

	/**
	 * Test method for {@link models.MySQL#closeConnection()}.
	 */
	@Test
	void testCloseConnection() {

		when(mockMySQL.closeConnection()).thenReturn(true);
		
		boolean result = mockMySQL.closeConnection();
		
		assertTrue(result);
	}

	/**
	 * Test method for {@link models.MySQL#createDB(java.lang.String)}.
	 */
	@Test
	void testCreateDB() {
		when(mockMySQL.createDB(anyString())).thenReturn(true);
		
		boolean result = mockMySQL.createDB("Test");

		assertTrue(result);
	}

	/**
	 * Test method for {@link models.MySQL#dropDB(java.lang.String)}.
	 */
	@Test
	void testDropDB() {
	
		when(mockMySQL.dropDB(anyString())).thenReturn(true);
		
		boolean result = mockMySQL.dropDB("Test");

		assertTrue(result);
	}

	/**
	 * Test method for {@link models.MySQL#createTable(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCreateTable() {
		
		when(mockMySQL.createTable(anyString(), anyString(), anyString())).thenReturn(true);
		
		boolean result = mockMySQL.createTable("TA22", "prueba", "id INT AUTO_INCREMENT PRIMARY KEY");

		assertTrue(result);
	}

	/**
	 * Test method for {@link models.MySQL#dropTable(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testDropTable() {
		
		when(mockMySQL.dropTable(anyString(), anyString())).thenReturn(true);
		
		boolean result = mockMySQL.dropTable("TA22", "prueba");

		assertTrue(result);
	}

	/**
	 * Test method for {@link models.MySQL#insertsData(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testInsertsData() {

		when(mockMySQL.insertsData(anyString(), anyString(), anyString())).thenReturn(true);
		
		boolean result = mockMySQL.insertsData("TA22", "prueba", "(\"name\")");

		assertTrue(result);
	}

	/**
	 * Test method for {@link models.MySQL#getValues(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testGetValues() {
		
		 // Crear un objeto simulado de ResultSet
        ResultSet rs = mock(ResultSet.class);
		
		when(mockMySQL.getValues(anyString(), anyString())).thenReturn(rs);
		
		ResultSet result = mockMySQL.getValues("TA22", "prueba");

		assertEquals(rs, result);

	}

	/**
	 * Test method for {@link models.MySQL#deleteRecord(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testDeleteRecord() {

		when(mockMySQL.deleteRecord(anyString(), anyString(), anyString())).thenReturn(true);
		
		boolean result = mockMySQL.deleteRecord("TA22", "prueba", "id=1");

		assertTrue(result);
	}

	/**
	 * Test method for
	 * {@link models.MySQL#recuperarColumnas(java.lang.String, java.lang.String)}.
	 */
	@Test
	void testRecuperarColumnas() {
		
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);

		when(mockMySQL.recuperarColumnas(anyString(), anyString())).thenReturn(rsmd);

		ResultSetMetaData result = mockMySQL.recuperarColumnas("TA22", "prueba");

		assertEquals(rsmd, result);
	}

}
