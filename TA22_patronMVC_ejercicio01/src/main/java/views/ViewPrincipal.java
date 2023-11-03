package views;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;


public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton añadirRegistro;
	public JButton editarCliente;
	public JButton eliminarCliente;
	public JButton mostrarClientes;
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
		
		añadirRegistro = new JButton("A\u00F1adir Registro");
		añadirRegistro.setBounds(375, 61, 150, 25);
		contentPane.add(añadirRegistro);
		
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
