/**
 * 
 */
package TA22_patronMVC_ejercicio01.TA22_patronMVC_ejercicio01;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import models.Cliente;

/**
 * 
 */
class ClienteTest {

	Cliente client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Inicio Test del modelo Cliente");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Fin Test del modelo Cliente");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		client = new Cliente();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		client = null;
	}

	private static Stream<Arguments> getFormatId() {
		return Stream.of(
				Arguments.of(1, 1), 
				Arguments.of(7, 7), 
				Arguments.of(5, 5));
	}

	/**
	 * Test method for {@link models.Cliente#getId()}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatId")
	void testGetId(int perspect, int id) {
		client.setId(id);
		int result = client.getId();
		assertEquals(perspect, result);
	}
	
	private static Stream<Arguments> getFormatNombre() {
		return Stream.of(
				Arguments.of("Jordi", "Jordi"), 
				Arguments.of("Laura", "Laura"), 
				Arguments.of("Pilar", "Pilar"));
	}

	/**
	 * Test method for {@link models.Cliente#getNombre()}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatNombre")
	void testGetNombre(String perspect, String nombre) {
		client.setNombre(nombre);
		String result = client.getNombre();
		assertEquals(perspect, result);
	}
	
	private static Stream<Arguments> getFormatApellido() {
		return Stream.of(
				Arguments.of("Mallafré", "Mallafré"), 
				Arguments.of("Gazquez", "Gazquez"), 
				Arguments.of("Jurio", "Jurio"));
	}

	/**
	 * Test method for {@link models.Cliente#getApellido()}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatApellido")
	void testGetApellido(String perspect, String apellido) {
		client.setApellido(apellido);
		String result = client.getApellido();
		assertEquals(perspect, result);
	}
	
	private static Stream<Arguments> getFormatDireccion() {
		return Stream.of(
				Arguments.of("Lopez Pelaez", "Lopez Pelaez"), 
				Arguments.of("Maria Cristina", "Maria Cristina"), 
				Arguments.of("Podaca", "Podaca"));
	}

	/**
	 * Test method for {@link models.Cliente#getDireccion()}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatDireccion")
	void testGetDireccion(String perspect, String direccion) {
		client.setDireccion(direccion);
		String result = client.getDireccion();
		assertEquals(perspect, result);
	}
	
	private static Stream<Arguments> getFormatDNI() {
		return Stream.of(
				Arguments.of(47771222, 47771222), 
				Arguments.of(12355978, 12355978), 
				Arguments.of(45781296, 45781296));
	}

	/**
	 * Test method for {@link models.Cliente#getDni()}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatDNI")
	void testGetDni(int perspect, int dni) {
		client.setDni(dni);
		int result = client.getDni();
		assertEquals(perspect, result);
	}
	
	private static Stream<Arguments> getFormatFecha() {
		return Stream.of(
				Arguments.of("2023-12-14", "2023-12-14"), 
				Arguments.of("2022-05-14", "2022-05-14"), 
				Arguments.of("2023-07-22", "2023-07-22"));
	}

	/**
	 * Test method for {@link models.Cliente#getFecha()}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatFecha")
	void testGetFecha(String perspect, String fecha) {
		client.setFecha(fecha);
		String result = client.getFecha();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link models.Cliente#setId(int)}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatId")
	void testSetId(int perspect, int id) {
		client.setId(id);
		int result = client.getId();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link models.Cliente#setNombre(java.lang.String)}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatNombre")
	void testSetNombre(String perspect, String nombre) {
		client.setNombre(nombre);
		String result = client.getNombre();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link models.Cliente#setApellido(java.lang.String)}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatApellido")
	void testSetApellido(String perspect, String apellido) {
		client.setApellido(apellido);
		String result = client.getApellido();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link models.Cliente#setDireccion(java.lang.String)}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatDireccion")
	void testSetDireccion(String perspect, String direccion) {
		client.setDireccion(direccion);
		String result = client.getDireccion();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link models.Cliente#setDni(int)}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatDNI")
	void testSetDni(int perspect, int dni) {
		client.setDni(dni);
		int result = client.getDni();
		assertEquals(perspect, result);
	}

	/**
	 * Test method for {@link models.Cliente#setFecha(java.lang.String)}.
	 */
	@ParameterizedTest
	@MethodSource("getFormatFecha")
	void testSetFecha(String perspect, String fecha) {
		client.setFecha(fecha);
		String result = client.getFecha();
		assertEquals(perspect, result);
	}

}
