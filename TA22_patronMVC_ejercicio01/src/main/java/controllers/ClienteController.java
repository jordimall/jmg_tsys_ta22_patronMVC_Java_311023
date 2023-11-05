/**
 * 
 */
package controllers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
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
		this.view.editarRegistro.addActionListener(editar);
		this.view.eliminarRegistro.addActionListener(eliminar);

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
			Object[] rowData = { cliente.getId(), cliente.getNombre(), cliente.getApellido(), cliente.getDireccion(),
					cliente.getDni(), cliente.getFecha() };
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

				if (campo.equals("fecha")) {
					JDateChooser dateChooser = new JDateChooser();
					dateChooser.setDateFormatString("yyyy-MM-dd");
					component = dateChooser;
				} else {
					JTextField textField = new JTextField(20);
					if (label.getText().equals("id")) {
						textField.setEditable(false);
					}
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

		view.contentPaneForm.botonGuardar = new JButton("Guardar");
		view.contentPaneForm.botonGuardar.addActionListener(insertarRegistro);
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		view.contentPaneForm.add(view.contentPaneForm.botonGuardar, gbc);

	}

	private String[] recuperarCamposTabla(String tabla) {
		String campos[] = mysql.buscarColumnas(tabla);
		return campos;
	}

	ActionListener añadir = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.contentPaneForm.botonGuardar
					.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
			view.contentPaneForm.botonGuardar.setText("guardar");
			view.contentPaneForm.botonGuardar.addActionListener(insertarRegistro);
			desbloquarFormulario();

			for (JComponent componente : view.contentPaneForm.componentes) {

				if (componente instanceof JTextField) {
					JTextField textField = (JTextField) componente;
					textField.setText("");
				} else if (componente instanceof JDateChooser) {
					JDateChooser dateChooser = (JDateChooser) componente;
					dateChooser.setDate(null);
				}
			}
		}
	};

	ActionListener insertarRegistro = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String arrayContenido[] = agruparContenido();
			Cliente cli = crearCliente(arrayContenido);

			if (cli != null) {
				mysql.insertarDatos("cliente", cli.toStringAdd());
				crearTabla();
				limpiarCampos();
				cli = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}
		}
	};

	ActionListener editar = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.contentPaneForm.botonGuardar
					.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
			view.contentPaneForm.botonGuardar.setText("editar");
			view.contentPaneForm.botonGuardar.addActionListener(editarRegistro);
			desbloquarFormulario();

			int filaSeleccionada = view.contentPaneRegistros.table.getSelectedRow();

			if (filaSeleccionada != -1) {
				int i = 0;
				for (JComponent componente : view.contentPaneForm.componentes) {
					if (componente instanceof JTextField) {
						JTextField textField = (JTextField) componente;
						String text = "";
						if (view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i) instanceof Integer) {
							text = Integer
									.toString((int) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i));
						} else {
							text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);
						}
						textField.setText(text);
					} else if (componente instanceof JDateChooser) {
						JDateChooser dateChooser = (JDateChooser) componente;
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						String date = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);
						try {
							dateChooser.setDate(formato.parse(date));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					i++;
				}

			} else {
				JOptionPane.showMessageDialog(view, "Ninguna fila seleccionada");
			}
		}
	};

	ActionListener editarRegistro = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String arrayContenido[] = agruparContenido();
			Cliente cli = crearCliente(arrayContenido);

			if (cli != null) {
				String condition = "id=" + cli.getId();
				mysql.actualizarDatos("cliente", cli.toStringUpdate(), condition);
				crearTabla();
				limpiarCampos();
				cli = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}
		}
	};

	ActionListener eliminar = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.contentPaneForm.botonGuardar
					.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
			view.contentPaneForm.botonGuardar.setText("eliminar");
			view.contentPaneForm.botonGuardar.addActionListener(eliminarRegistro);
			bloquerFormulario();

			int filaSeleccionada = view.contentPaneRegistros.table.getSelectedRow();

			if (filaSeleccionada != -1) {
				int i = 0;
				for (JComponent componente : view.contentPaneForm.componentes) {
					if (componente instanceof JTextField) {
						JTextField textField = (JTextField) componente;
						String text = "";
						if (view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i) instanceof Integer) {
							text = Integer
									.toString((int) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i));
						} else {
							text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);
						}
						textField.setText(text);
					} else if (componente instanceof JDateChooser) {
						JDateChooser dateChooser = (JDateChooser) componente;
						SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
						String date = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);
						try {
							dateChooser.setDate(formato.parse(date));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					i++;
				}

			} else {
				JOptionPane.showMessageDialog(view, "Ninguna fila seleccionada");
			}
		}
	};

	ActionListener eliminarRegistro = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String arrayContenido[] = agruparContenido();
			Cliente cli = crearCliente(arrayContenido);
			int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			boolean respuestaUsuario = opcion == JOptionPane.YES_OPTION;

			if (cli != null && respuestaUsuario) {
				String condition = "id=" + cli.getId();
				mysql.actualizarDatos("cliente", cli.toStringUpdate(), condition);
				crearTabla();
				limpiarCampos();
				cli = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}

		}
	};

	private Cliente crearCliente(String[] arrayList) {
		Cliente cli = new Cliente();

		if (!arrayList[0].isEmpty()) {
			cli.setId(Integer.parseInt(arrayList[0]));
		}

		if (validators.validarMaxCaracteres(arrayList[1], 255) && validators.validarNoCadenaVacia(arrayList[1])) {
			cli.setNombre(arrayList[1]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el nombre, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[2], 255) && validators.validarNoCadenaVacia(arrayList[2])) {
			cli.setApellido(arrayList[2]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar los apellidos, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[3], 11) && validators.validarNoCadenaVacia(arrayList[3])) {
			cli.setDireccion(arrayList[3]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar la dirección, el campo no ha de estar vació y a de tener un maximo de 11 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[4], 11) && validators.validarNumero(arrayList[4])
				&& validators.validarNoCadenaVacia(arrayList[4])) {
			cli.setDni(Integer.parseInt(arrayList[4]));
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el DNI, el campo no ha de estar vació y a de ser un número entero");
			return null;
		}

		if (validators.validarNoCadenaVacia(arrayList[5])) {
			cli.setFecha(arrayList[5]);
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

			}

		}
	}

	private void bloquerFormulario() {
		for (JComponent componente : view.contentPaneForm.componentes) {

			if (componente instanceof JTextField) {
				JTextField textField = (JTextField) componente;
				textField.setEditable(false);
			} else if (componente instanceof JDateChooser) {
				JDateChooser dateChooser = (JDateChooser) componente;
				dateChooser.setFocusable(false);

			}

		}
	}

	private void desbloquarFormulario() {
		String campos[] = recuperarCamposTabla("cliente");
		int i = 0;
		for (JComponent componente : view.contentPaneForm.componentes) {

			if (componente instanceof JTextField) {
				
				JTextField textField = (JTextField) componente;
				
				if (!campos[i].equals("ud")) {
					
					textField.setEditable(true);
					
				}

			} else if (componente instanceof JDateChooser) {
				
				JDateChooser dateChooser = (JDateChooser) componente;
				dateChooser.setFocusable(true);

			}
			
			i++;
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
