package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Font;

public class ViewPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPrincipal frame = new ViewPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ejercicio 1");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(105, 0, 220, 50);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNewLabel);
		
		JButton añadirCliente = new JButton("A\u00F1adir Cliente");
		añadirCliente.setBounds(162, 61, 110, 25);
		contentPane.add(añadirCliente);
		
		table = new JTable();
		table.setBounds(0, 97, 434, 164);
		contentPane.add(table);
	}
}
