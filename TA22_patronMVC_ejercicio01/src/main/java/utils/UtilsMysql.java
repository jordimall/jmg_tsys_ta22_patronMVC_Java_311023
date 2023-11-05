package utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import models.MySQL;
import models.User;

public class UtilsMysql {

	private MySQL mysql;
	private User user;
	private String dataBase = "TA22";

	public UtilsMysql(MySQL mySQL, User user) {
		this.mysql = mySQL;
		this.user = user;
	}
	
	public void iniciarSession() {
		mysql.connectionSQL(user.getPassword(), user.getUserName());
	}

	public void iniciarSessionBaseDatos() {

		mysql.connectionSQL(user.getPassword(), user.getUserName(), dataBase);

	}

	public void cerrarSesion() {

		mysql.closeConnection();

	}

	public void crearBaseDatos(String nameDatabase) {
		iniciarSessionBaseDatos();
		mysql.createDB(nameDatabase);
		cerrarSesion();
	}

	public void crearTabla(String nameTable, String contentTable) {
		iniciarSessionBaseDatos();
		mysql.createTable(this.dataBase, nameTable, contentTable);
		cerrarSesion();
	}

	public void insertarDatos(String nameTable, String contentInsert) {
		iniciarSessionBaseDatos();
		mysql.insertsData(this.dataBase, nameTable, contentInsert);
		cerrarSesion();
	}

	public void actualizarDatos(String nameTable, String contentUpdate, String conditionUpdate) {
		iniciarSessionBaseDatos();
		mysql.update(dataBase, nameTable, contentUpdate, conditionUpdate);
		cerrarSesion();
	}

	public void eliminarDatos(String nameTable, String conditionDelete) {
		iniciarSessionBaseDatos();
		mysql.deleteRecord(this.dataBase, nameTable, conditionDelete);
		cerrarSesion();
	}

	public String[] buscarColumnas(String tabla) {
		iniciarSessionBaseDatos();
		ResultSetMetaData metaData = mysql.recuperarColumnas(this.dataBase, tabla);
		cerrarSesion();
		try {

			String columnas[] = new String[metaData.getColumnCount()];

			for (int i = 1; i <= metaData.getColumnCount(); i++) {

				columnas[i - 1] = metaData.getColumnName(i);

			}

			return columnas;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public <T> List<T> RecuperarTodosLosDatos(String tabla, Class<T> type) {
		List<T> results = new ArrayList<>();
		iniciarSessionBaseDatos();
		ResultSet resultSet = mysql.getValues(dataBase, tabla);
		cerrarSesion();
		try {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int numColumnas = metaData.getColumnCount();

			while (resultSet.next()) {
				T obj = type.getDeclaredConstructor().newInstance();

				for (int i = 1; i <= numColumnas; i++) {
					String columnName = metaData.getColumnName(i);
					Field field = type.getDeclaredField(columnName);
					if (field != null) {
						field.setAccessible(true);

						Object value = resultSet.getObject(i);

						if (value instanceof Date && field.getType().equals(String.class)) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							String stringValue = dateFormat.format(value);
							field.set(obj, stringValue);
						} else {
							field.set(obj, value);
						}
					}
				}

				results.add(obj);
			}

		} catch (SQLException | ReflectiveOperationException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en la adquisici�n de datos");
		}

		return results;

	}
}
