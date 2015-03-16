package praCourseWork2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ExamTable {
	JTable table;
	
	public ExamTable(Assessment ass){
		makeTable(ass);
	}

	public void makeTable(Assessment ass) {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Disables all cells from being editable
				return false;
			}
		};
		

		// Assigns column headings
		model.addColumn("Year");
		model.addColumn("Period");
		model.addColumn("Module Code");
		model.addColumn("Occ");
		model.addColumn("#Map");
		model.addColumn("#Ass");
		model.addColumn("Cand Key");
		model.addColumn("Name");
		model.addColumn("Mark");
		model.addColumn("Grade");

		table = new JTable(model);
		table.setFont(new Font("Calibri", Font.BOLD, 14));
		
		// Sets column header look
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Calibri", Font.BOLD, 16));
		header.setBackground(Color.black);
		header.setForeground(Color.WHITE);

		// Sets cell selection to single
		// So only one cell is selected
		// Also retrieves data when name is clicked
		table.setCellSelectionEnabled(true);

		System.out.println("Making JTable");
		// Fetches first assessment and adds to table
		// for (Assessment t : assessments) {
		for (Result r : ass.results) {
			model.addRow(new Object[] { r.getYear(), r.getPeriod(),
					r.getModuleCode(), r.getOcc(), r.getMap(),
					r.getAssessment(), r.getCandKey(), r.getName(),
					r.getMark(), r.getGrade() });
		}

		table.setPreferredScrollableViewportSize(new Dimension(200, 300));
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
		// JScrollPane scrollPane = new JScrollPane(table);
		// repaint();
		// revalidate();

	}
	
	public JTable getTable(){
		return table;
	}

}
