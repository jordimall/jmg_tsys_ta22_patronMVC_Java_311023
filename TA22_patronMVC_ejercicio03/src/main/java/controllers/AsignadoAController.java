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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import models.Asignado_a;
import models.MySQL;
import models.User;
import utils.UtilsGeneral;
import utils.UtilsMysql;
import views.ViewPrincipal;

/**
 * 
 */
public class AsignadoAController {
	private Asignado_a asignado;
	private ViewPrincipal view;
	private UtilsMysql mysql;
	private UtilsGeneral utg;
	private String nameTable = "asignado_a";
	private String contentTable = "Cientifico VARCHAR(8), Proyecto char(4), PRIMARY KEY (Cientifico, Proyecto),"
			+ "CONSTRAINT cientifico_fk FOREIGN KEY (Cientifico) REFERENCES cientificos (DNI),"
			+ "CONSTRAINT proyecto_fk FOREIGN KEY (Proyecto) REFERENCES proyecto (Id)";

	/**
	 * @param proyecto
	 * @param view
	 * @param mysql
	 * @param utg
	 */
	public AsignadoAController(Asignado_a asignado, ViewPrincipal view, User user, MySQL mysql) {
		this.asignado = asignado;
		this.view = view;
		this.utg = new UtilsGeneral(view);
		this.mysql = new UtilsMysql(mysql, user);
		this.mysql.crearBaseDatos();
		this.mysql.crearTabla(nameTable, contentTable);
		this.view.btnAsignadoA.addActionListener(modificarTablaForm);
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
		List<Asignado_a> asignados = (List<Asignado_a>) mysql.RecuperarTodosLosDatos(nameTable,
				this.asignado.getClass());
		DefaultTableModel model = (DefaultTableModel) view.contentPaneRegistros.table.getModel();
		for (Asignado_a asignado : asignados) {
			Object[] rowData = { asignado.getCientifico(), asignado.getProyecto() };
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

				if (campo.equals("Cientifico")) {
					JComboBox<String> combo = new JComboBox<String>();
					List<String> valores = mysql.RecuperarTodosLosDatos("cientificos", "DNI");
					for (String valor : valores) {
						combo.addItem(valor);
					}
					component = combo;
				} else {
					JComboBox<String> combo = new JComboBox<String>();
					List<String> valores = mysql.RecuperarTodosLosDatos("proyecto", "Id");
					for (String valor : valores) {
						combo.addItem(valor);
					}
					component = combo;
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

		view.contentPaneForm.revalidate();
		view.contentPaneForm.repaint();

	}

	private String[] recuperarCamposTabla(String tabla) {
		String campos[] = mysql.buscarColumnas(tabla);
		return campos;
	}

	private Asignado_a crearRegistro(String[] arrayList) {
		Asignado_a asig = new Asignado_a();

		asig.setCientifico(arrayList[0]);
		asig.setProyecto(arrayList[1]);

		return asig;
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
			view.lblTabla.setText("Tabla Asignado_a");

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

				JComboBox<?> combo = (JComboBox<?>) componente;
				combo.setSelectedItem(null);

			}
		}
	};

	ActionListener insertarRegistro = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String arrayContenido[] = utg.agruparContenido();
			Asignado_a asig = crearRegistro(arrayContenido);

			if (asig != null) {
				mysql.insertarDatos(nameTable, asig.toStringAdd());
				crearTabla();
				utg.limpiarCampos();
				asig = null;
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

					String text = "";

					text = (String) view.contentPaneRegistros.table.getValueAt(filaSeleccionada, i);

					JComboBox<?> combo = (JComboBox<?>) componente;
					combo.setSelectedItem(text);

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
			Asignado_a asig = crearRegistro(arrayContenido);

			if (asig != null) {
				String condition = "Cientifico=" + asig.getCientifico() + " AND Proyecto=" + asig.getProyecto();
				mysql.actualizarDatos(nameTable, asig.toStringUpdate(), condition);
				crearTabla();
				utg.limpiarCampos();
				asig = null;
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
			Asignado_a asig = crearRegistro(arrayContenido);
			int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			boolean respuestaUsuario = opcion == JOptionPane.YES_OPTION;

			if (asig != null && respuestaUsuario) {
				String condition = "Cientifico=" + asig.getCientifico() + " AND Proyecto=" + asig.getProyecto();
				mysql.eliminarDatos(nameTable, condition);
				crearTabla();
				utg.limpiarCampos();
				asig = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}

		}
	};
}
