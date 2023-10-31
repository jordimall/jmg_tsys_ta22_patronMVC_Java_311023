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
			System.out.println("Server Connected");
			System.out.println();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("No se ha podido conectar con mi base de datos");
			System.out.println(e);
		}
	}

	public void connectionSQLDatabase(String password, String user, String nameDatabase) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conexion = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/" + nameDatabase + "?useTimezone=true&serverTimezone=UTC", user,
					password);
			System.out.println("conectado ha la base de datos: " + nameDatabase);
			System.out.println();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("No se ha podido conectar con mi base de datos");
			System.out.println(e);
		}
	}

	public void closeConnection() {
		try {
			this.conexion.close();
			JOptionPane.showMessageDialog(null, "Se ha finalizado la conexión con el servidor");
		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void closeConnectionNotMessage() {
		try {
			this.conexion.close();
		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	private void useDatabase(String nameDatabase) {
		try {
			String querydb = "Use " + nameDatabase + ";";
			Statement stdb = this.conexion.createStatement();
			stdb.executeUpdate(querydb);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error conectando con la base de datos. ");
		}
	}

	public void createDB(String name) {
		try {
			Statement st = this.conexion.createStatement();
			String query = "DROP DATABASE IF EXISTS " + name;

			st.executeUpdate(query);
			query = "CREATE DATABASE " + name;
			st.executeUpdate(query);
			closeConnectionNotMessage();
			System.out.println("Base de datos creada exitosamente");
			System.out.println();
			connectionSQLDatabase("root", "root", name);
		} catch (SQLException e) {
			Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void createTable(String db, String nameTabla, String contenidoTabla) {
		try {
			useDatabase(db);

			String query = "CREATE TABLE " + nameTabla + "(" + contenidoTabla + ")";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			System.out.println("Tabla creada con exito!");
			System.out.println();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Error creando tabla.");
		}
	}

	public void insertsData(String db, String nameTabla, String insert) {
		try {
			useDatabase(db);

			String query = "INSERT INTO " + nameTabla + " (" + consegirColumnas(db, nameTabla) + ") VALUES " + insert
					+ ";";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			System.out.println("Datos almacenados correctamente");
			System.out.println();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en el almacenamiento");
		}
	}

	public void getValues(String db, String nameTabla) {
		try {
			useDatabase(db);

			String query = "SELECT * FROM " + nameTabla;
			Statement st = this.conexion.createStatement();
			java.sql.ResultSet resultSet;
			resultSet = st.executeQuery(query);

			java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
			int numColumnas = metaData.getColumnCount();

			while (resultSet.next()) {
				for (int i = 1; i <= numColumnas; i++) {
					if (i == numColumnas) {
						System.out.print(metaData.getColumnName(i) + ": "
								+ resultSet.getString(metaData.getColumnName(i)) + "\n");
					} else {
						System.out.print(metaData.getColumnName(i) + ": "
								+ resultSet.getString(metaData.getColumnName(i)) + " ");
					}
				}
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en la adquisición de datos");
		}
	}

	public void deleteRecord(String db, String nameTabla, String condition) {
		try {
			useDatabase(db);

			String query = "DELETE FROM " + nameTabla + " WHERE " + condition + ";";
			Statement st = this.conexion.createStatement();
			st.executeUpdate(query);
			System.out.println("Datos borrados exitosamente");
			System.out.println();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error borrando el registro especificado");
		}
	}

	private ResultSetMetaData recuperarColumnas(String db, String nameTabla) {
		ResultSetMetaData metaData = null;
		try {

			useDatabase(db);

			String query = "SELECT * FROM " + nameTabla + " LIMIT 0";
			Statement st = this.conexion.createStatement();

			ResultSet resultSet;
			resultSet = st.executeQuery(query);

			metaData = resultSet.getMetaData();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error consultando la base de datos");
		}

		return metaData;
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
