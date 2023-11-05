package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class MySQL {

	private Connection conexion;

	public void connectionSQL(String password, String user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexion = DriverManager
					.getConnection("jdbc:mysql://127.0.0.1:3306?useTimezone=true&serverTimezone=UTC", user, password);
		} catch (SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse al sistema de gestión de bases");
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void connectionSQL(String password, String user, String nameDatabase) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/" + nameDatabase + "?useTimezone=true&serverTimezone=UTC", user,
					password);

		} catch (SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + nameDatabase);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void closeConnection() {
		try {
			this.conexion.close();

		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
			JOptionPane.showMessageDialog(null, "Error al desconectarse");
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private void useDatabase(String nameDatabase) {
		try {
			String querydb = "Use " + nameDatabase + ";";
			Statement stdb = this.conexion.createStatement();
			stdb.executeUpdate(querydb);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + nameDatabase);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void createDB(String name) {
		try {
			Statement st = this.conexion.createStatement();

			String query = "CREATE DATABASE IF NOT EXISTS " + name;
			st.executeUpdate(query);

		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
			JOptionPane.showMessageDialog(null, "Error al crear la base de datos: " + name);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void createTable(String db, String nameTabla, String contenidoTabla) {
		try {
			useDatabase(db);

			String query = "CREATE TABLE IF NOT EXISTS " + nameTabla + "(" + contenidoTabla + ")";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error al crear la tabla: " + nameTabla);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void insertsData(String db, String nameTabla, String insert) {
		try {
			useDatabase(db);
			String query = "INSERT INTO " + nameTabla + " (" + consegirColumnas(db, nameTabla) + ") VALUES (" + insert
					+ ");";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Datos insertados correctamente");
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Error al insertar datos en la tabla: " + nameTabla);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public ResultSet getValues(String db, String nameTabla) {
		try {

			useDatabase(db);
			String query = "SELECT * FROM " + nameTabla;
			Statement st = this.conexion.createStatement();
			ResultSet resultSet = st.executeQuery(query);

			return resultSet;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al recuperar datos en la tabla: " + nameTabla);
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}

	}

	public void deleteRecord(String db, String nameTabla, String condition) {
		try {
			useDatabase(db);

			String query = "DELETE FROM " + nameTabla + " WHERE " + condition + ";";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Datos eliminados correctamente");
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Error al eliminar datos en la tabla: " + nameTabla);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public void update(String db, String nameTabla, String update, String condition) {
		try {
			useDatabase(db);

			String query = "UPDATE " + nameTabla + " SET " + update + " WHERE " + condition;
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Error al modificar datos en la tabla: " + nameTabla);
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	public ResultSetMetaData recuperarColumnas(String db, String nameTabla) {
		try {

			useDatabase(db);

			String query = "SELECT * FROM " + nameTabla + " LIMIT 0";
			Statement st = this.conexion.createStatement();

			ResultSet resultSet;
			resultSet = st.executeQuery(query);

			ResultSetMetaData metaData = resultSet.getMetaData();

			return metaData;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error consultando la base de datos");
			return null;
		}
	}

	private String consegirColumnas(String db, String nameTabla) {
		String columnas = "";
		try {
			ResultSetMetaData metaData = recuperarColumnas(db, nameTabla);

			int numColumnas = metaData.getColumnCount();

			for (int i = 1; i <= numColumnas; i++) {
				if (!metaData.isAutoIncrement(i)) {
					if (i == numColumnas) {
						columnas += metaData.getColumnName(i);
					} else {
						columnas += metaData.getColumnName(i) + ",";
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error consultando la base de datos");
		}

		return columnas;
	}
}
