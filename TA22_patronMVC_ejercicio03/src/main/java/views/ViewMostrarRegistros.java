package views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ViewMostrarRegistros extends JPanel {

	private static final long serialVersionUID = 1L;
	public JTable table;
	public JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public ViewMostrarRegistros() {
		setLayout(null);
		table = new JTable();
		table.setBounds(10, 97, 500, 153);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 97, 860, 450);
	    add(scrollPane);
	}

}
