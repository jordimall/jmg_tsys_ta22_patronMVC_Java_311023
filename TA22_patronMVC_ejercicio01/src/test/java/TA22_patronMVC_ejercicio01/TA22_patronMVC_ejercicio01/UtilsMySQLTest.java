/**
 * 
 */
package TA22_patronMVC_ejercicio01.TA22_patronMVC_ejercicio01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.Cliente;
import models.MySQL;
import models.User;
import utils.UtilsMysql;

/**
 * 
 */
class UtilsMySQLTest {
	
	UtilsMysql utilMysql;
	User user;
	MySQL mockMySQL;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio test utils UtilMysql");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Final test utils UtilMysql");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mockMySQL = mock(MySQL.class);
		user = new User();
		utilMysql = new UtilsMysql(mockMySQL, user);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		mockMySQL = null;
		user = null;
		utilMysql = null;
	}

	/**
	 * Test method for {@link utils.UtilsMysql#iniciarSessionBaseDatos()}.
	 */
	@Test
	void testIniciarSessionBaseDatos() {
		String perspect = "Conectado a la base de datos: TA22";
		when(mockMySQL.connectionSQL(anyString(), anyString(), anyString())).thenReturn(true);
		String result = utilMysql.iniciarSessionBaseDatos();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#crearBaseDatos(java.lang.String)}.
	 */
	@Test
	void testCrearBaseDatos() {
		String perspect = "Base de datos prueba creada";
		when(mockMySQL.createDB(anyString())).thenReturn(true);
		
        String result = utilMysql.crearBaseDatos("prueba");
        assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#crearTabla(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testCrearTabla() {
		String perspect = "Tabla prueba creada";
		when(mockMySQL.createTable(anyString(), anyString(), anyString())).thenReturn(true);
		
		String result = utilMysql.crearTabla("prueba", "id INT AUTO_INCREMENT, name VARCHAR(25)");
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#insertarDatos(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testInsertarDatos() {
		String perspect = "Datos insertados correctamente";
		when(mockMySQL.insertsData(anyString(), anyString(), anyString())).thenReturn(true);
		
		String result = utilMysql.insertarDatos("prueba", "(\"name\")");
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#eliminarDatos(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	void testEliminarDatos() {
		String perspect = "Datos eliminados correctamente";
		when(mockMySQL.deleteRecord(anyString(), anyString(), anyString())).thenReturn(true);
		
		String result = utilMysql.eliminarDatos("prueba", "id=1");
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#cerrarSesion()}.
	 */
	@Test
	void testCerrarSesion() {
		String perspect = "Sessión cerrada";
		when(mockMySQL.closeConnection()).thenReturn(true);
		
		String result = utilMysql.cerrarSesion();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#buscarColumnas(java.lang.String)}.
	 */
	@Test
	void testBuscarColumnas() {
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);
		when(mockMySQL.recuperarColumnas(anyString(), anyString())).thenReturn(rsmd);
		
		String[] result = utilMysql.buscarColumnas("prueba");
		assertNotNull(result);
	}

	/**
	 * Test method for {@link utils.UtilsMysql#RecuperarTodosLosDatos(java.lang.String, java.lang.Class)}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	void testRecuperarTodosLosDatos() {
		Cliente cli = new Cliente();
		ResultSet rs = mock(ResultSet.class);
		ResultSetMetaData rsmd = mock(ResultSetMetaData.class);

		// Definir el comportamiento del objeto simulado
		when(mockMySQL.getValues(anyString(), anyString())).thenReturn(rs);
        try {
			when(rs.getMetaData()).thenReturn(rsmd);
			when(rsmd.getColumnCount()).thenReturn(1);
			when(rsmd.getColumnName(1)).thenReturn("nombre");
			when(rs.next()).thenReturn(true).thenReturn(false); // Devuelve true la primera vez, luego false
			when(rs.getObject(anyInt())).thenReturn("nombre");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Cliente> results = (List<Cliente>) utilMysql.RecuperarTodosLosDatos("prueba", cli.getClass());
		 assertEquals(1, results.size());
	}

}
