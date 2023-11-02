/**
 * 
 */
package controllers;

import java.awt.event.ActionEvent;

/**
 * 
 */

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import models.Cliente;
import utils.UtilsMysql;
import views.ViewPrincipal;

public class ClienteController implements ActionListener {

	private Cliente client;
	private ViewPrincipal view;
	private UtilsMysql mysql;

	/**
	 * @param client
	 * @param view
	 */
	public ClienteController(Cliente cliente, ViewPrincipal view) {
		this.client = cliente;
		this.view = view;
		// this.view.añadirCliente.addActionListener(this);
		// this.view.editarCliente.addActionListener(this);
		// this.view.eliminarCliente.addActionListener(this);
		// this.view.mostrarClientes.addActionListener(this);
	}

	public void iniciarVista() {
		view.setTitle("Ejercicio 1");
		view.setBounds(100, 100, 900, 600);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setLocationRelativeTo(null);
		mysql.iniciarSessionBaseDatos();
		crearColumnas();
		mostrarClientes();
		view.setVisible(true);
		mysql.crearBaseDatos("prueba");
	}

	private void crearColumnas() {

		String columnas[] = mysql.buscarColumnas("cliente");
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnas.length; i++) {
			if (columnas[i] != null) {
				model.addColumn(columnas[i]);
			}
		}

		model.addColumn("operaciones");
		view.table.setModel(model);
	}
	
	private void mostrarClientes() {
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = (List<Cliente>) mysql.RecuperarTodosLosDatos("cliente", client.getClass());
		DefaultTableModel model = (DefaultTableModel) view.table.getModel();
		for (Cliente cliente : clientes) {
	        Object[] rowData = {
	            cliente.getNombre(),
	            cliente.getApellido(),
	            cliente.getDireccion(),
	            cliente.getDni(),
	            cliente.getFecha()
	        };
	        model.addRow(rowData);
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
