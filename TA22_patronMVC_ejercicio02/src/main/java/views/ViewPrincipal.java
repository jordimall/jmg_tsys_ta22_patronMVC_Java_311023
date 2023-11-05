package views;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton a�adirRegistro;
	public JButton editarRegistro;
	public JButton eliminarRegistro;
	public ViewMostrarRegistros contentPaneRegistros;
	public ViewForm contentPaneForm;

	/**
	 * Create the frame.
	 */
	public ViewPrincipal() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ejercicio 1");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(340, 0, 220, 50);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		a�adirRegistro = new JButton("A\u00F1adir");
		a�adirRegistro.setBounds(490, 60, 100, 25);
		contentPane.add(a�adirRegistro);
		
		editarRegistro = new JButton("Modificar");
		editarRegistro.setBounds(620, 60, 100, 25);
		contentPane.add(editarRegistro);
		
		eliminarRegistro = new JButton("Eliminar");
		eliminarRegistro.setBounds(740, 60, 100, 25);
		contentPane.add(eliminarRegistro);
		
		contentPaneRegistros = new ViewMostrarRegistros();
		contentPaneRegistros.scrollPane.setSize(450, 430);
		contentPaneRegistros.scrollPane.setLocation(10, 10);
		contentPaneRegistros.setBounds(0, 90, 470, 450);
		contentPaneRegistros.setVisible(true);
	    contentPane.add(contentPaneRegistros);
	    
	    contentPaneForm = new ViewForm();
		contentPaneForm.setSize(300, 430);
		contentPaneForm.setLocation(500, 90);
		contentPaneForm.setVisible(true);
		contentPane.add(contentPaneForm);    
	   
	}
}
