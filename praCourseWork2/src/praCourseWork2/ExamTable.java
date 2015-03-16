package praCourseWork2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ExamTable {
	JTable table;
	
	public ExamTable(Assessment ass,ArrayList<Assessment> assesments,ArrayList<Student> students){
		deAnnonymise(assesments, students);
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
	
	/**
	 * matches anonymous marking codes in records with students in arraylist If
	 * found, replaces with student numbers
	 */
	public void deAnnonymise(ArrayList<Assessment> assesments,ArrayList<Student> students) {
		System.out.println("Starting deannonymising...");
		for (Assessment a : assesments) {
			for (Result t : a.results) {
				String candKey = t.getCandKey();
				candKey = candKey.replaceAll("\"", "");

				// Checks if candKey is actually student number
				// If it's coursework, it will enter this if statement
				if (candKey.substring(candKey.length() - 2,
						candKey.length() - 1).equals("/")) {
					System.out.println("Coursework");
					// Removes the end /1 or /2 after student number
					candKey = candKey.substring(0, candKey.length() - 2);
					candKey = candKey.replaceAll("#", "");
					t.candKey = candKey;

					for (Student s : students) {
						candKey = candKey.replaceAll("#", "");
						if (candKey.equals(s.studentNumber + "")) {
							// Finds student with matching student numbers
							// System.out.println("Found Student "+s.studentNumber+" who matches on JTable with sNumber "+candKey);

							String modCode = t.getModuleCode().replaceAll("\"",
									"");
							s.addMarks(modCode + " " + t.getAssessment(),
									t.mark);
						}
					}
				} else
					for (Student s : students) {
						candKey = candKey.replaceAll("#", "");
						if (candKey.equals(s.aMC)) {
							// Finds student with matching anonymous marking
							// code
							// Replaces it with student number
							t.candKey = s.getStudentNumber();
							t.setName(s.getName());
							s.addMarks(t.getModuleCode().replaceAll("\"", "")
									+ " " + t.getAssessment(), t.mark);
						}
					}

			}
		}

	}
	
	public JTable getTable(){
		return table;
	}

}
