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


public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JButton añadirCliente;
	public JButton editarCliente;
	public JButton eliminarCliente;
	public JButton mostrarClientes;
	public JTable table;
	public JScrollPane scrollPane;

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
		
		añadirCliente = new JButton("A\u00F1adir Cliente");
		añadirCliente.setBounds(375, 61, 150, 25);
		contentPane.add(añadirCliente);
		
		table = new JTable();
		table.setBounds(10, 97, 500, 153);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 97, 860, 450);
	    contentPane.add(scrollPane);
	}
}
