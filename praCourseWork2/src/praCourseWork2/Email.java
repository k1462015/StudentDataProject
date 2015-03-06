package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Email extends JFrame {
	private JPanel main;
	private JTable table;
	private JPanel west;
	private JPanel buttons;
	private JPanel listPanel;
	private ArrayList<Student> students;

	public Email(ArrayList<Student> s){
		JButton selectAll = new JButton("select all");
		JButton selectNone = new JButton("select none");
		students =  new ArrayList<Student>(s);
		main = new JPanel(new BorderLayout());
		buttons = new JPanel();
		listPanel = new JPanel();
		west = new JPanel(new BorderLayout());

		setSize(550, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buttons.add(selectNone);
		buttons.add(selectAll);
		
		listPanel.add(new JScrollPane(list));
		
		west.add(buttons,BorderLayout.NORTH);
		west.add(listPanel, BorderLayout.CENTER);
		
		main.add(west, BorderLayout.WEST);
		setVisible(true);
		add(main);
		}
	public JScrollPane createTable(boolean b){
		DefaultTableModel model = new DefaultTableModel();
		
		for (Student s : students) {
			model.addRow(new Object[] {s.getName().replaceAll("\"", ""), b });
		}
		table = new JTable(model);

		table.setPreferredScrollableViewportSize(new Dimension(300, 350));
		table.setFillsViewportHeight(true);
		table.setGridColor(Color.GRAY);
		JScrollPane scrollPane = new JScrollPane(table);
		
		repaint();
		revalidate();
		
		return scrollPane;
	}

}

