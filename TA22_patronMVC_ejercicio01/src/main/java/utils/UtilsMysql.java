package utils;

import models.MySQL;
import models.User;

public class UtilsMysql {
	
	MySQL mysql = new MySQL();
	User user = new User();

	public void iniciarSessionBaseDatos() {
		mysql.connectionSQL(user.getPassword(), user.getUserName());
	}

	public void crearBaseDatos(String nameDataBase) {
		mysql.createDB(nameDataBase);
	}

	public void crearTabla(String nameDataBase, String nameTable, String contentTable) {
		mysql.createTable(nameDataBase, nameTable, contentTable);
	}

	public void insertarDatos(String nameDataBase, String nameTable, String contentTable) {
		mysql.insertsData(nameDataBase, nameTable, contentTable);
	}

	public void mostrarDatos(String nameDataBase, String nameTable) {
		System.out.println("tabla: " + nameTable + "\n");
		mysql.getValues(nameDataBase, nameTable);
		System.out.println();
	}

	public void eliminarDatos(String nameDataBase, String nameTable, String condition) {
		mysql.deleteRecord(nameDataBase, nameTable, condition);
	}
	
	
	public void cerrarSesion() {
		mysql.closeConnection();
	}
}
