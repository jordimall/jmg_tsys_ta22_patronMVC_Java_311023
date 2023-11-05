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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import models.MySQL;
import models.User;
import models.Videos;
import utils.UtilsGeneral;
import utils.UtilsMysql;
import utils.utilsValidatorsForm;
import views.ViewPrincipal;

/**
 * 
 */
public class VideosController {
	private Videos videos;
	private ViewPrincipal view;
	private UtilsMysql mysql;
	private utilsValidatorsForm validators = new utilsValidatorsForm();
	private UtilsGeneral utg;
	private String contentTable = "id INT(11) AUTO_INCREMENT, title VARCHAR(250) DEFAULT NULL,"
			+ "director VARCHAR(250) DEFAULT NULL, cli_id INT(11) DEFAULT NULL, PRIMARY KEY (id),"
			+ " CONSTRAINT videos_fk FOREIGN KEY (cli_id) REFERENCES cliente (id)";

	/**
	 * @param client
	 * @param view
	 */
	public VideosController(Videos videos, ViewPrincipal view, User user, MySQL mysql) {
		this.videos = videos;
		this.view = view;
		this.utg = new UtilsGeneral(view);
		this.mysql = new UtilsMysql(mysql, user);
	}

	public void iniciarController() {
		this.mysql.crearBaseDatos();
		this.mysql.crearTabla("videos", contentTable);
		this.view.btnvideos.addActionListener(modificarTablaForm);
	}

	private void crearTabla() {
		crearColumnas();
		mostrarRegistro();
	}

	private void crearColumnas() {

		String columnas[] = recuperarCamposTabla("videos");
		DefaultTableModel model = new DefaultTableModel();

		for (int i = 0; i < columnas.length; i++) {
			if (columnas[i] != null) {
				if (columnas[i].equals("cli_id")) {
					model.addColumn("cliente");
				} else {
					model.addColumn(columnas[i]);
				}
			}
		}
		view.contentPaneRegistros.table.setModel(model);
	}

	private void mostrarRegistro() {
		@SuppressWarnings("unchecked")
		List<Videos> videos = (List<Videos>) mysql.RecuperarTodosLosDatos("videos", this.videos.getClass());
		DefaultTableModel model = (DefaultTableModel) view.contentPaneRegistros.table.getModel();
		for (Videos video : videos) {
			Object[] rowData = { video.getId(), video.getTitle(), video.getDirector(), video.getCli_id() };
			model.addRow(rowData);
		}
	}

	private void crearFormulario() {
		String campos[] = recuperarCamposTabla("videos");

		view.contentPaneForm.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		JComponent component;

		for (String campo : campos) {
			if (campo != null) {
				JLabel label = new JLabel(campo);

				if (campo.equals("cli_id")) {
					JComboBox<String> combo = new JComboBox<String>();
					List<String> valores = mysql.RecuperarTodosLosDatos("cliente", "id");
					for (String valor : valores) {
						combo.addItem(valor);
					}
					component = combo;
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
		view.lblTabla.setText("Tabla Videos");
		view.contentPaneForm.revalidate();
		view.contentPaneForm.repaint();

	}

	private String[] recuperarCamposTabla(String tabla) {
		String campos[] = mysql.buscarColumnas(tabla);
		return campos;
	}

	private Videos crearVideo(String[] arrayList) {
		Videos vid = new Videos();

		if (!arrayList[0].isEmpty()) {
			vid.setId(Integer.parseInt(arrayList[0]));
		}

		if (validators.validarMaxCaracteres(arrayList[1], 255) && validators.validarNoCadenaVacia(arrayList[1])) {
			vid.setTitle(arrayList[1]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el titulo, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		if (validators.validarMaxCaracteres(arrayList[2], 255) && validators.validarNoCadenaVacia(arrayList[2])) {
			vid.setDirector(arrayList[2]);
		} else {
			JOptionPane.showMessageDialog(view,
					"Error al validar el director, el campo no ha de estar vació y a de tener un maximo de 255 caracteres");
			return null;
		}

		vid.setCli_id(Integer.parseInt(arrayList[3]));

		return vid;
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
			utg.desbloquarFormulario(recuperarCamposTabla("videos"));

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
			String arrayContenido[] = utg.agruparContenido();
			Videos vid = crearVideo(arrayContenido);

			if (vid != null) {
				mysql.insertarDatos("videos", vid.toStringAdd());
				crearTabla();
				utg.limpiarCampos();
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
				utg.desbloquarFormulario(recuperarCamposTabla("videos"));

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
			String arrayContenido[] = utg.agruparContenido();
			Videos vis = crearVideo(arrayContenido);

			if (vis != null) {
				String condition = "id=" + vis.getId();
				mysql.actualizarDatos("videos", vis.toStringUpdate(), condition);
				crearTabla();
				utg.limpiarCampos();
				vis = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
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
			String arrayContenido[] = utg.agruparContenido();
			Videos vid = crearVideo(arrayContenido);
			int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el registro?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			boolean respuestaUsuario = opcion == JOptionPane.YES_OPTION;

			if (vid != null && respuestaUsuario) {
				String condition = "id=" + vid.getId();
				mysql.eliminarDatos("videos", condition);
				crearTabla();
				utg.limpiarCampos();
				vid = null;
			} else {
				JOptionPane.showMessageDialog(view, "Error al insertar el campo");
			}

		}
	};
}
