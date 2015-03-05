package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;

public class Email extends JFrame {
	private JPanel main;
	private JList list;
	
	public Email(ArrayList<Student> students){
		main = new JPanel();
		setSize(550, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLayout(new BorderLayout());
		list = new JList(students.toArray());
		list.setCellRenderer(new CheckboxListCellRenderer());
		main.add(new JScrollPane(list), BorderLayout.WEST);
		setVisible(true);
		add(main);
	}
	
	public class CheckboxListCellRenderer extends JCheckBox implements ListCellRenderer {

	    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	    	
	        // need this in order to actually select the JCheckBox
	        setSelected(isSelected);
	        setEnabled(list.isEnabled());
	        // need this in order to display the student with checkbox
	        setText(value == null ? "" : value.toString());  
	        // make the background and Foreground the same as the list
	        setBackground(list.getBackground());
	        setForeground(list.getForeground());
	        return this;
	    }
	}
	
}
