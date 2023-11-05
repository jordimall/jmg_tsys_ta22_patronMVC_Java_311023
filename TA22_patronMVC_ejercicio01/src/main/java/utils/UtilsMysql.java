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

	public String iniciarSessionBaseDatos() {

		boolean conexion = mysql.connectionSQL(user.getPassword(), user.getUserName(), dataBase);
		String message = "";

		if (conexion) {
			message = "Conectado a la base de datos: " + dataBase;
		} else {
			message = "Error al conectarse a la base de datos";
		}
		return message;
	}

	public String crearBaseDatos(String nameDatabase) {
		boolean condition = mysql.createDB(nameDatabase);
		String message = "";
		if (condition) {
			message = "Base de datos " + nameDatabase + " creada";
		} else {
			message = "Error al crear la base de datos " + nameDatabase;
		}

		return message;
	}

	public String crearTabla(String nameTable, String contentTable) {
		String message = "";
		boolean condition = mysql.createTable(this.dataBase, nameTable, contentTable);
		if (condition) {
			message = "Tabla " + nameTable + " creada";
		} else {
			message = "Error al crear la tabla " + nameTable;
		}
		return message;
	}

	public String insertarDatos(String nameTable, String contentInsert) {
		String message = "";
		boolean condition = mysql.insertsData(this.dataBase, nameTable, contentInsert);
		if (condition) {
			message = "Datos insertados correctamente";
		} else {
			message = "Error al insertar los datos";
		}
		return message;
	}

	public String actualizarDatos(String nameTable, String contentUpdate, String conditionUpdate) {
		String message = "";
		boolean condition = mysql.update(dataBase, nameTable, contentUpdate, conditionUpdate);
		if (condition) {
			message = "Datos insertados correctamente";
		} else {
			message = "Error al insertar los datos";
		}
		return message;
	}

	public String eliminarDatos(String nameTable, String conditionDelete) {
		String message = "";
		boolean condition = mysql.deleteRecord(this.dataBase, nameTable, conditionDelete);
		if (condition) {
			message = "Datos eliminados correctamente";
		} else {
			message = "Error al eliminar los datos";
		}
		return message;
	}

	public String cerrarSesion() {
		String message = "";
		boolean condition = mysql.closeConnection();
		if (condition) {
			message = "Sessión cerrada";
		} else {
			message = "Error al cerrar sesión";
		}
		return message;
	}

	public String[] buscarColumnas(String tabla) {
		ResultSetMetaData metaData = mysql.recuperarColumnas(this.dataBase, tabla);
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

		ResultSet resultSet = mysql.getValues(dataBase, tabla);

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
			JOptionPane.showMessageDialog(null, "Error en la adquisición de datos");
		}

		return results;

	}
}
