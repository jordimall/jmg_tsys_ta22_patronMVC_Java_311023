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

	public boolean connectionSQL(String password, String user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexion = DriverManager
					.getConnection("jdbc:mysql://127.0.0.1:3306?useTimezone=true&serverTimezone=UTC", user, password);
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean connectionSQL(String password, String user, String nameDatabase) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/" + nameDatabase + "?useTimezone=true&serverTimezone=UTC", user,
					password);
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean closeConnection() {
		try {
			this.conexion.close();
			return true;
		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}

	private void useDatabase(String nameDatabase) {
		try {
			String querydb = "Use " + nameDatabase + ";";
			Statement stdb = this.conexion.createStatement();
			stdb.executeUpdate(querydb);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error conectando con la base de datos.");
		}
	}

	public boolean createDB(String name) {
		try {
			Statement st = this.conexion.createStatement();

			String query = "CREATE DATABASE " + name;
			st.executeUpdate(query);

			return true;

		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}

	public boolean dropDB(String name) {
		try {
			Statement st = this.conexion.createStatement();

			String query = "DROP DATABASE IF EXISTS " + name;
			st.executeUpdate(query);

			return true;

		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
			return false;
		}
	}

	public boolean createTable(String db, String nameTabla, String contenidoTabla) {
		try {
			useDatabase(db);

			String query = "CREATE TABLE " + nameTabla + "(" + contenidoTabla + ")";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean dropTable(String db, String nameTabla) {
		try {
			useDatabase(db);

			String query = "DROP TABLE " + nameTabla;
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean insertsData(String db, String nameTabla, String insert) {
		try {
			useDatabase(db);

			String query = "INSERT INTO " + nameTabla + " (" + consegirColumnas(db, nameTabla) + ") VALUES " + insert
					+ ";";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
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
			System.out.println(e.getMessage());
			return null;
		}

	}

	public boolean deleteRecord(String db, String nameTabla, String condition) {
		try {
			useDatabase(db);

			String query = "DELETE FROM " + nameTabla + " WHERE " + condition + ";";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
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
