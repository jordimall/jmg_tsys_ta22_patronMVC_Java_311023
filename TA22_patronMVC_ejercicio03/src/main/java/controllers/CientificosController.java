/**
 * 
 */
package controllers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import models.Cientificos;
import models.MySQL;
import models.User;
import utils.UtilsGeneral;
import utils.UtilsMysql;
import utils.utilsValidatorsForm;
import views.ViewPrincipal;

/**
 * 
 */
public class CientificosController {
	private Cientificos cientificos;
	private ViewPrincipal view;
	private UtilsMysql mysql;
	private utilsValidatorsForm validators = new utilsValidatorsForm();
	private UtilsGeneral utg;
	private String nameTable = "cientificos";
	private String contentTable = "DNI varchar(8), NomAples NVARCHAR(255) DEFAULT NULL, PRIMARY KEY (DNI)";

	/**
	 * @param cientificos
	 * @param view
	 * @param mysql
	 * @param validators
	 * @param utg
	 */
	public CientificosController(Cientificos cientificos, ViewPrincipal view, User user, MySQL mysql) {
		this.cientificos = cientificos;
		this.view = view;
		this.utg = new UtilsGeneral(view);
		this.mysql = new UtilsMysql(mysql, user);
		this.mysql.crearBaseDatos();
		this.mysql.crearTabla(nameTable, contentTable);
		this.view.añadirRegistro.addActionListener(añadir);
		this.view.editarRegistro.addActionListener(editar);
		this.view.eliminarRegistro.addActionListener(eliminar);
		this.view.btnCientifico.addActionListener(modificarTablaForm);
	}

	public void iniciarVista() {
		view.setTitle("Ejercicio 3");
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
		mostrarCientificos();
	}

	private void crearColumnas() {

		String columnas[] = recuperarCamposTabla(nameTable);
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnas.length; i++) {
			if (columnas[i] != null) {
				model.addColumn(columnas[i]);
			}
		}
		view.contentPaneRegistros.table.setModel(model);
	}

	private void mostrarCientificos() {
		@SuppressWarnings("unchecked")
		List<Cientificos> cientificos = (List<Cientificos>) mysql.RecuperarTodosLosDatos(nameTable,
				this.cientificos.getClass());
		DefaultTableModel model = (DefaultTableModel) view.contentPaneRegistros.table.getModel();
		for (Cientificos cientifico : cientificos) {
			Object[] rowData = { cientifico.getDNI(), cientifico.getNomApels() };
			model.addRow(rowData);
		}
	}

	private void crearFormulario() {
		String campos[] = recuperarCamposTabla(nameTable);

		view.contentPaneForm.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		JComponent component;
		for (String campo : campos) {
			if (campo != null) {
				JLabel label = new JLabel(campo);

				JTextField textField = new JTextField(20);

				component = textField;

				gbc.gridx = 0;
				gbc.gridy++;
				gbc.fill = GridBagConstraints.HORIZONTAL; 
	            gbc.weightx = 1.0;
				view.contentPaneForm.add(label, gbc);

				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
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

		view.contentPaneForm.revalidate();
		view.contentPaneForm.repaint();

	}

	private String[] recuperarCamposTabla(String tabla) {
		String campos[] = mysql.buscarColumnas(tabla);
		return campos;
	}

	private Cientificos crearRegistro(String[] arrayList) {
		Cientificos cie = new Cientificos();

		if (!arrayList[0].isEmpty() && validators.validarMaxCaracteres(arrayList[0], 8)) {
			cie.setDNI(arrayList[0]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el DNI, el campo no ha de estar vació y a de tener un maximo de 8 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[1], 255) && validators.validarNoCadenaVacia(arrayList[1])) {
			cie.setNomApels(arrayList[1]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el NomAppels, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		return cie;
	}

	ActionListener modificarTablaForm = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.contentPaneRegistros.table.setModel(new DefaultTableModel());
			view.contentPaneForm.componentes.clear();
			view.contentPaneForm.removeAll();
			view.contentPaneForm.botonGuardar
					.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
			view.contentPaneForm.botonGuardar.setText("guardar");
			view.añadirRegistro.removeActionListener(view.añadirRegistro.getActionListeners()[0]);
			view.añadirRegistro.addActionListener(añadir);
			view.editarRegistro.removeActionListener(view.editarRegistro.getActionListeners()[0]);
			view.editarRegistro.addActionListener(editar);
			view.eliminarRegistro.removeActionListener(view.eliminarRegistro.getActionListeners()[0]);
			view.eliminarRegistro.addActionListener(eliminar);
			view.lblTabla.setText("Tabla Cientificos");

			view.validate();
			view.repaint();

			crearTabla();
			crearFormulario();

		}
	};

	ActionListener añadir = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.contentPaneForm.botonGuardar
					.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
			view.contentPaneForm.botonGuardar.setText("Guardar");
			view.contentPaneForm.botonGuardar.addActionListener(insertarRegistro);
			utg.desbloquarFormulario(recuperarCamposTabla(nameTable));

			for (JComponent componente : view.contentPaneForm.componentes) {

				JTextField textField = (JTextField) componente;
				textField.setText("");

			}
		}
	};

	ActionListener insertarRegistro = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String arrayContenido[] = utg.agruparContenido();
			Cientificos cli = crearRegistro(arrayContenido);

			if (cli != null) {
				mysql.insertarDatos(nameTable, cli.toStringAdd());
				crearTabla();
				utg.limpiarCampos();
				cli = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}
		}
	};

	ActionListener editar = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			int filaSeleccionada = view.contentPaneRegistros.table.getSelectedRow();

			if (filaSeleccionada != -1) {
				view.contentPaneForm.botonGuardar
						.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
				view.contentPaneForm.botonGuardar.setText("editar");
				view.contentPaneForm.botonGuardar.addActionListener(editarRegistro);
				utg.desbloquarFormulario(recuperarCamposTabla(nameTable));

				int i = 0;
				for (JComponent componente : view.contentPaneForm.componentes) {

					JTextField textField = (JTextField) componente;
					String text = "";

					text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);

					textField.setText(text);

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
			String arrayContenido[] = utg.agruparContenido();
			Cientificos cli = crearRegistro(arrayContenido);

			if (cli != null) {
				String condition = "DNI=" + cli.getDNI();
				mysql.actualizarDatos(nameTable, cli.toStringUpdate(), condition);
				crearTabla();
				utg.limpiarCampos();
				cli = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al editar el campo");
			}
		}
	};

	ActionListener eliminar = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			int filaSeleccionada = view.contentPaneRegistros.table.getSelectedRow();

			if (filaSeleccionada != -1) {
				view.contentPaneForm.botonGuardar
						.removeActionListener(view.contentPaneForm.botonGuardar.getActionListeners()[0]);
				view.contentPaneForm.botonGuardar.setText("eliminar");
				view.contentPaneForm.botonGuardar.addActionListener(eliminarRegistro);
				utg.bloquerFormulario();

				int i = 0;
				for (JComponent componente : view.contentPaneForm.componentes) {

					JTextField textField = (JTextField) componente;
					String text = "";

					text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);

					textField.setText(text);

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
			String arrayContenido[] = utg.agruparContenido();
			Cientificos cli = crearRegistro(arrayContenido);
			int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			boolean respuestaUsuario = opcion == JOptionPane.YES_OPTION;

			if (cli != null && respuestaUsuario) {
				String condition = "DNI=" + cli.getDNI();
				mysql.eliminarDatos(nameTable, condition);
				crearTabla();
				utg.limpiarCampos();
				cli = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al eliminar el campo");
			}

		}
	};

}
