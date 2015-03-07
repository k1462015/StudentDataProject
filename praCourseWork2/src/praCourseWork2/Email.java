package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Email extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel main;
	private JPanel mainNext;
	private JTable table;
	private JPanel west;
	private JPanel center;
	private JPanel buttons;
	private JPanel south;
	private JPanel listPanel;
	private ArrayList<Student> student;
	private JTextArea header;
	private JTextArea footer;
	private JButton selectAll;
	private JButton selectNone;
	private JButton next;	
	private JButton previous;
	private JButton send;
	private JTextArea viewEmail;
	
	public Email(ArrayList<Student> students){
		selectAll = new JButton("select all");
		selectAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(true));
				validate();
			}
			
		});
		
		selectNone = new JButton("select none");
		selectNone.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listPanel.removeAll();
				listPanel.add(createTable(false));
				validate();
			}
			
		});
		mainNext = new JPanel();
		viewEmail = new JTextArea();
		viewEmail.setPreferredSize(new Dimension(450, 410));
		mainNext.add(viewEmail);
		next = new JButton("Next");
		next.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.remove(west);
				main.remove(center);
				main.add(mainNext,BorderLayout.CENTER);
				validate();
				repaint();
			}
			
		});
		previous = new JButton("Previous");
		previous.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.remove(mainNext);
				main.add(west);
				main.add(center);
				validate();
				repaint();
				getCheckedRows();
			}
			
		});
		send = new JButton("Send");
		
		south = new JPanel(new BorderLayout());
		student = new ArrayList<Student>(students);
		main = new JPanel(new BorderLayout());
		buttons = new JPanel();
		listPanel = new JPanel();
		west = new JPanel(new BorderLayout());
		center = new JPanel(new GridLayout(2,1,0,10));
		
		header = new JTextArea();
		header.setPreferredSize(new Dimension(50, 50));
		footer = new JTextArea();
		footer.setPreferredSize(new Dimension(50, 50));
		
		setSize(600, 550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel SouthWest =  new JPanel();
		SouthWest.add(send);
		SouthWest.add(previous);
		
		JPanel SouthEast=  new JPanel();
		SouthEast.add(next);
		
		south.add(SouthWest,BorderLayout.WEST);;
		south.add(SouthEast,BorderLayout.EAST);
		buttons.add(selectNone);
		buttons.add(selectAll);
		
		listPanel.add(createTable(false));
		
		west.add(buttons,BorderLayout.NORTH);
		west.add(listPanel, BorderLayout.CENTER);
		
		center.add(header);
		center.add(footer);
		
		main.add(center,BorderLayout.CENTER);
		main.add(west, BorderLayout.WEST);
		main.add(south, BorderLayout.SOUTH);
		
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
	
	public void getCheckedRows(){
		ArrayList<String> selectedRows = new ArrayList<String>();
		for(int i = 0; i < table.getRowCount(); i++) {
		     if((Boolean) table.getValueAt(i, 1)) {
		         selectedRows.add((String) table.getValueAt(i, 0));
		     }
		}
		System.out.println(selectedRows);
	}
	
	public void getSelectedStudents(ArrayList<String> l){
		for(String s :l){
			//if(s.equals(anObject))
		}
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

