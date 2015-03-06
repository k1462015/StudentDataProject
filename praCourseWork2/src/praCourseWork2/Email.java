package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Email extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel main;
	//private JList list;
	private JTable table;
	private JPanel west;
	private JPanel buttons;
	private JPanel listPanel;
	private ArrayList<Student> student;

	public Email(ArrayList<Student> students){
		JButton selectAll = new JButton("select all");
		selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(true));
			}
			
		});
		
		JButton selectNone = new JButton("select none");
		selectNone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(false));
			}
			
		});
		student = new ArrayList<Student>(students);
		main = new JPanel(new BorderLayout());
		buttons = new JPanel();
		listPanel = new JPanel();
		west = new JPanel(new BorderLayout());

		setSize(550, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttons.add(selectNone);
		buttons.add(selectAll);
		
		listPanel.add(createTable(false));
		
		west.add(buttons,BorderLayout.NORTH);
		west.add(listPanel, BorderLayout.CENTER);
		
		main.add(west, BorderLayout.WEST);
		
		setVisible(true);
		add(new JScrollPane(main));
		}
	
	public JScrollPane createTable(boolean b){
		MyTableModel model = new MyTableModel();
		
		for (Student s : student) {
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
	
	public class MyTableModel extends DefaultTableModel {

	    public MyTableModel() {
	      super(new String[]{"Student Name","Check"}, 0);
	    }

	    @Override
	    public Class<?> getColumnClass(int columnIndex) {
	      Class clazz = String.class;
	      switch (columnIndex) {
	        case 0:
	          clazz = String.class;
	          break;
	        case 1:
	          clazz = Boolean.class;
	          break;
	      }
	      return clazz;
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	      return column == 1;
	    }

	    @Override
	    public void setValueAt(Object aValue, int row, int column) {
	      if (aValue instanceof Boolean && column == 1) {
	        System.out.println(aValue);
	        Vector rowData = (Vector)getDataVector().get(row);
	        rowData.set(1, (boolean)aValue);
	        fireTableCellUpdated(row, column);
	      }
	    }

	}

}

