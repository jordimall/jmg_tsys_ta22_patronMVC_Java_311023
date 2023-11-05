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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import models.MySQL;
import models.Proyecto;
import models.User;
import utils.UtilsGeneral;
import utils.UtilsMysql;
import utils.utilsValidatorsForm;
import views.ViewPrincipal;

/**
 * 
 */
public class ProyectoController {
	private Proyecto proyecto;
	private ViewPrincipal view;
	private UtilsMysql mysql;
	private utilsValidatorsForm validators = new utilsValidatorsForm();
	private UtilsGeneral utg;
	private String nameTable = "proyecto";
	private String contentTable = "Id char(4), Nombre NVARCHAR(255) DEFAULT NULL, Horas int DEFAULT 0, PRIMARY KEY (Id)";

	/**
	 * @param proyecto
	 * @param view
	 * @param mysql
	 * @param utg
	 */
	public ProyectoController(Proyecto proyecto, ViewPrincipal view, User user, MySQL mysql) {
		this.proyecto = proyecto;
		this.view = view;
		this.utg = new UtilsGeneral(view);
		this.mysql = new UtilsMysql(mysql, user);
		this.mysql.crearBaseDatos();
		this.mysql.crearTabla(nameTable, contentTable);
		this.view.btnProyecto.addActionListener(modificarTablaForm);
	}

	public void iniciarController() {
		this.mysql.crearBaseDatos();
		this.mysql.crearTabla(nameTable, contentTable);

	}

	private void crearTabla() {
		crearColumnas();
		mostrarProyectos();
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

	private void mostrarProyectos() {
		@SuppressWarnings("unchecked")
		List<Proyecto> proyectos = (List<Proyecto>) mysql.RecuperarTodosLosDatos(nameTable, this.proyecto.getClass());
		DefaultTableModel model = (DefaultTableModel) view.contentPaneRegistros.table.getModel();
		for (Proyecto proyecto : proyectos) {
			Object[] rowData = { proyecto.getId(), proyecto.getNombre(), proyecto.getHoras() };
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

		view.contentPaneForm.revalidate();
		view.contentPaneForm.repaint();

	}

	private String[] recuperarCamposTabla(String tabla) {
		String campos[] = mysql.buscarColumnas(tabla);
		return campos;
	}

	private Proyecto crearRegistro(String[] arrayList) {
		Proyecto pro = new Proyecto();

		if (!arrayList[0].isEmpty() && validators.validarMaxCaracteres(arrayList[0], 4)) {
			pro.setId(arrayList[0]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el Id, el campo no ha de estar vació y a de tener un maximo de 4 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[1], 255) && validators.validarNoCadenaVacia(arrayList[1])) {
			pro.setNombre(arrayList[1]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el Nombre, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		if (validators.validarNumero(arrayList[2]) && validators.validarNoCadenaVacia(arrayList[2])) {
			pro.setHoras(Integer.parseInt(arrayList[2]));
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar las horas, el campo no ha de estar vació y a de ser un número entero");
			return null;
		}

		return pro;
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
			view.lblTabla.setText("Tabla Proyecto");

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
			Proyecto pro = crearRegistro(arrayContenido);

			if (pro != null) {
				mysql.insertarDatos(nameTable, pro.toStringAdd());
				crearTabla();
				utg.limpiarCampos();
				pro = null;
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

					if (view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i) instanceof Integer) {
						text = Integer.toString((int) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i));
					} else {
						text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);
					}

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
			Proyecto pro = crearRegistro(arrayContenido);

			if (pro != null) {
				String condition = "Id=" + pro.getId();
				mysql.actualizarDatos(nameTable, pro.toStringUpdate(), condition);
				crearTabla();
				utg.limpiarCampos();
				pro = null;
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
					if (view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i) instanceof Integer) {
						text = Integer.toString((int) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i));
					} else {
						text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);
					}

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
			Proyecto pro = crearRegistro(arrayContenido);
			int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			boolean respuestaUsuario = opcion == JOptionPane.YES_OPTION;

			if (pro != null && respuestaUsuario) {
				String condition = "Id=" + pro.getId();
				mysql.eliminarDatos(nameTable, condition);
				crearTabla();
				utg.limpiarCampos();
				pro = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}

		}
	};
}
