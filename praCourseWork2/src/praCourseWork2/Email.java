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

public class Email extends JFrame {
	private JPanel main;
	private JTable list;
	private JPanel west;
	private JPanel buttons;
	private JPanel listPanel;

	public Email(ArrayList<Student> students){
		JButton selectAll = new JButton("select all");
		JButton selectNone = new JButton("select none");
		
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
	
}

