package praCourseWork2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import student.Assessment;
import student.Student;

public class ExamTable {
	JTable table;
	
	public ExamTable(Assessment ass,ArrayList<Assessment> assesments,ArrayList<Student> students){
		deAnnonymise(assesments, students);
		makeTable(ass);
	}
	
	public ExamTable(){
		
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
		for (Result r : ass.getResults()) {
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
	
	public void readExamData(BufferedReader bf,ArrayList<Assessment> assesments,String assessment) throws IOException{
		// Finds corresponding column indexes
					// Reads first line to get column headings
					String line = bf.readLine();
					String[] linesplit = line.split(",");
					int yearCol = 0;
					int periodCol = 0;
					int occCol = 0;
					int nameCol = 0;
					int mapCol = 0;

					int moduleCol = 0;
					int assCol = 0;
					int candCol = 0;
					int markCol = 0;
					int gradeCol = 0;
					for (int i = 0; i < linesplit.length; i++) {
						System.out.println(linesplit[i]);
						if (linesplit[i].equals("\"#Module\"")
								|| linesplit[i].equals("#Module")) {
							moduleCol = i;
						} else if (linesplit[i].equals("\"#Ass#\"")
								|| linesplit[i].equals("#Ass#")) {
							assCol = i;
						} else if (linesplit[i].equals("\"#Cand Key\"")
								|| linesplit[i].equals("#Cand Key")) {
							candCol = i;
						} else if (linesplit[i].equals("\"Mark\"")
								|| linesplit[i].equals("Mark")) {
							markCol = i;
						} else if (linesplit[i].equals("\"Grade\"")
								|| linesplit[i].equals("Grade")) {
							gradeCol = i;
						} else if (linesplit[i].equals("\"Year\"")
								|| linesplit[i].equals("Year")) {
							yearCol = i;
						} else if (linesplit[i].equals("\"Period\"")
								|| linesplit[i].equals("Period")) {
							periodCol = i;
						} else if (linesplit[i].equals("\"Occ\"")
								|| linesplit[i].equals("Occ")) {
							occCol = i;
						} else if (linesplit[i].equals("\"Name\"")
								|| linesplit[i].equals("Name")) {
							nameCol = i;
						} else if (linesplit[i].equals("\"#Map\"")
								|| linesplit[i].equals("#Map")) {
							mapCol = i;
						}

					}

					// Adds records to assessments
					while ((line = bf.readLine()) != null) {
						linesplit = line.split(",");
						String ass = linesplit[assCol].replaceAll("\"", "");
						Result temp = new Result(
								linesplit[yearCol].replaceAll("\"", ""),
								linesplit[periodCol].replaceAll("\"", ""),
								linesplit[moduleCol].replaceAll("\"", ""),
								linesplit[occCol], linesplit[mapCol].replaceAll("\"",
										""), ass, linesplit[candCol].replaceAll("\"",
										""), linesplit[nameCol].replaceAll("\"", ""),
								Integer.parseInt(linesplit[markCol]),
								linesplit[gradeCol]);
						assessment = temp.getModuleCode();
						// First checks if Assessment array is empty
						if (assesments.isEmpty()) {
							Assessment t1 = new Assessment();
							t1.addResult(temp);
							assesments.add(t1);
							// Now checks if there is already an assessment
							// object
							// With same assessment number
							// If not make new assessment object
							// Then add record
						} else if (!checkAllAss(temp.getAssessment(),assesments)) {
							Assessment t1 = new Assessment();
							t1.addResult(temp);
							assesments.add(t1);
						} else {
							// Since there is existing assessment object
							// Finds it, and adds record
							for (int i = 0; i < assesments.size(); i++) {
								if (assesments.get(i).getResults().get(0).getAssessment()
										.equals(temp.getAssessment())) {
									assesments.get(i).addResult(temp);
								}
							}
						}

					}
	}
	
	/**
	 * Checks through all Assessment Objects Within Assessment ArrayList If
	 * finds existing one, that matches string returns true
	 * 
	 * @param s
	 *            - assessment code
	 * @return
	 */
	public boolean checkAllAss(String s,ArrayList<Assessment> assesments) {
		for (Assessment t : assesments) {
			if (t.getResults().get(0).getAssessment().equals(s)) {
				// If does have assessment already
				return true;
			}
		}
		return false;
	}
	
	/**
	 * matches anonymous marking codes in records with students in arraylist If
	 * found, replaces with student numbers
	 */
	public void deAnnonymise(ArrayList<Assessment> assesments,ArrayList<Student> students) {
		System.out.println("Starting deannonymising...");
		for (Assessment a : assesments) {
			for (Result t : a.getResults()) {
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
						if (candKey.equals(s.getStudentNumber())) {
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
						if (candKey.equals(s.getAMC()+"")) {
							// Finds student with matching anonymous marking
							// code
							// Replaces it with student number
							t.candKey = s.getStudentNumber()+"";
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
