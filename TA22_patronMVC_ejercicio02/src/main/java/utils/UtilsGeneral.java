/**
 * 
 */
package utils;

import java.text.SimpleDateFormat;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import views.ViewPrincipal;

/**
 * 
 */
public class UtilsGeneral {
	
	private ViewPrincipal view;
	
	

	/**
	 * @param view
	 */
	public UtilsGeneral(ViewPrincipal view) {
		this.view = view;
	}

	public String[] agruparContenido() {
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

	public void limpiarCampos() {
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

	public void bloquerFormulario() {
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

	public void desbloquarFormulario(String[] columnas) {
		String campos[] = columnas;
		int i = 0;
		for (JComponent componente : view.contentPaneForm.componentes) {

			if (componente instanceof JTextField) {
				
				JTextField textField = (JTextField) componente;
				
				if (!campos[i].equals("id")) {
					
					textField.setEditable(true);
					
				}

			} else if (componente instanceof JDateChooser) {
				
				JDateChooser dateChooser = (JDateChooser) componente;
				dateChooser.setFocusable(true);

			}
			
			i++;
			
		}
	}
	
}
