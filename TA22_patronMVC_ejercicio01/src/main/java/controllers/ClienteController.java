/**
 * 
 */
package controllers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import models.Cliente;
import models.MySQL;
import models.User;
import utils.UtilsMysql;
import utils.utilsValidatorsForm;
import views.ViewPrincipal;

public class ClienteController implements ActionListener {

	private Cliente client;
	private ViewPrincipal view;
	private UtilsMysql mysql;
	private utilsValidatorsForm validators = new utilsValidatorsForm();

	/**
	 * @param client
	 * @param view
	 */
	public ClienteController(Cliente cliente, ViewPrincipal view, User user, MySQL mysql) {
		this.client = cliente;
		this.view = view;
		this.mysql = new UtilsMysql(mysql, user);
		this.view.añadirRegistro.addActionListener(añadir);
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
		crearTabla();
		crearFormulario();
		view.setVisible(true);
	}

	private void crearTabla() {
		crearColumnas();
		mostrarClientes();
	}

	private void crearColumnas() {

		String columnas[] = recuperarCamposTabla("cliente");
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnas.length; i++) {
			if (columnas[i] != null) {
				model.addColumn(columnas[i]);
			}
		}
		view.contentPaneRegistros.table.setModel(model);
	}

	private void mostrarClientes() {
		@SuppressWarnings("unchecked")
		List<Cliente> clientes = (List<Cliente>) mysql.RecuperarTodosLosDatos("cliente", client.getClass());
		DefaultTableModel model = (DefaultTableModel) view.contentPaneRegistros.table.getModel();
		for (Cliente cliente : clientes) {
			Object[] rowData = { cliente.getNombre(), cliente.getApellido(), cliente.getDireccion(), cliente.getDni(),
					cliente.getFecha() };
			model.addRow(rowData);
		}
	}

	private void crearFormulario() {
		String campos[] = recuperarCamposTabla("cliente");

		view.contentPaneForm.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		JComponent component;

		for (String campo : campos) {
			if (campo != null) {
				JLabel label = new JLabel(campo);

				if (campo.equals("fecha")) { // Supongamos que "fecha" es el campo de la fecha
					JDateChooser dateChooser = new JDateChooser();
					dateChooser.setDateFormatString("yyyy-MM-dd");
					component = dateChooser;
				} else {
					JTextField textField = new JTextField(20);
					component = textField;
				}

				gbc.gridx = 0;
				gbc.gridy++;
				view.contentPaneForm.add(label, gbc);

				gbc.gridx = 1;
				view.contentPaneForm.add(component, gbc);
				view.contentPaneForm.componentes.add(component);
			}
		}
	}

	private String[] recuperarCamposTabla(String tabla) {
		String campos[] = mysql.buscarColumnas(tabla);
		return campos;
	}

	ActionListener añadir = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String arrayContenido[] = agruparContenido();
			Cliente cli = crearCliente(arrayContenido);

			if (cli != null) {
				mysql.insertarDatos("cliente", cli.toString());
				crearTabla();
				limpiarCampos();
			}
		}
	};

	private Cliente crearCliente(String[] arrayList) {
		Cliente cli = new Cliente();

		if (validators.validarMaxCaracteres(arrayList[0], 255) && validators.validarNoCadenaVacia(arrayList[0])) {
			cli.setNombre(arrayList[0]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el nombre, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[1], 255) && validators.validarNoCadenaVacia(arrayList[1])) {
			cli.setApellido(arrayList[1]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar los apellidos, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[2], 11) && validators.validarNoCadenaVacia(arrayList[2])) {
			cli.setDireccion(arrayList[2]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar la dirección, el campo no ha de estar vació y a de tener un maximo de 11 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[3], 11) && validators.validarNumero(arrayList[3])
				&& validators.validarNoCadenaVacia(arrayList[3])) {
			cli.setDni(Integer.parseInt(arrayList[3]));
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el DNI, el campo no ha de estar vació y a de ser un número entero");
			return null;
		}

		if (validators.validarNoCadenaVacia(arrayList[4])) {
			cli.setFecha(arrayList[4]);
		} else {
			JOptionPane.showMessageDialog(view, "Error al validar la fecha, el campo no ha de estar vació");
			return null;
		}

		return cli;
	}

	private String[] agruparContenido() {
		String array[] = new String[view.contentPaneForm.componentes.size()];
		int i = 0;
		for (JComponent componente : view.contentPaneForm.componentes) {

			if (componente instanceof JTextField) {
				JTextField textField = (JTextField) componente;
				array[i] = textField.getText();
			} else if (componente instanceof JDateChooser) {
				JDateChooser dateChooser = (JDateChooser) componente;
				SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
				array[i] = formatoFecha.format(dateChooser.getDate());
			}
			i++;
		}
		return array;
	}

	private void limpiarCampos() {
		for (JComponent componente : view.contentPaneForm.componentes) {

			if (componente instanceof JTextField) {
				JTextField textField = (JTextField) componente;
				textField.setText("");
			} else if (componente instanceof JDateChooser) {
				JDateChooser dateChooser = (JDateChooser) componente;
				dateChooser.setDate(null);
				;
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
