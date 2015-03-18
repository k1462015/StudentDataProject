package data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import student.Assessment;
import student.Result;
import student.Student;

/**
 * Creates JTable for Assessments & De-anonymises anon codes
 * @author TMH
 *
 */
public class ExamTable {
	JTable table;

	/**
	 * 
	 * @param Assesment
	 *            object containing results
	 * @param ArrayList
	 *            of all loaded Assessment
	 * @param ArrayList
	 *            of students
	 */
	public ExamTable(Assessment ass, ArrayList<Assessment> assesments,ArrayList<Student> students) {
		deAnnonymise(assesments, students);
		makeTable(ass);
	}
	
	/**
	 * Allows usage of readExamData method
	 */
	public ExamTable() {

	}

	/**
	 * Generates a JTable Using results from Assessment
	 * 
	 * @param Assesment
	 *            Obejct containing results
	 */
	public void makeTable(Assessment assessment) {
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Disables all cells from being editable
				return false;
			}
		};

		// Assigns column headings
		model.addColumn("Name/Anon Codes");
		model.addColumn("#Ass");
		model.addColumn("Module Code");
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
		for (Result r : assessment.getResults()) {
			String name = r.getName();
			if (r.getName().equals("")) {
				name = r.getCandKey();
			}
			model.addRow(new Object[] { name,r.getAssessment(),r.getModuleCode(), r.getMark(), r.getGrade() });
		}

		table.setPreferredScrollableViewportSize(new Dimension(200, 300));
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
	}
	
	/**
	 * Reads data from CSV file
	 * @param BufferedReader containg csv file from file
	 * @param ArrayList of currently load assessments
	 * @throws IOException
	 */
	public void readExamData(BufferedReader bf,ArrayList<Assessment> assessments)throws IOException {
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
			if (linesplit[i].equals("\"#Module\"") || linesplit[i].equals("#Module")) {
				moduleCol = i;
			} else if (linesplit[i].equals("\"#Ass#\"") || linesplit[i].equals("#Ass#")) {
				assCol = i;
			} else if (linesplit[i].equals("\"#Cand Key\"") || linesplit[i].equals("#Cand Key")) {
				candCol = i;
			} else if (linesplit[i].equals("\"Mark\"") || linesplit[i].equals("Mark")) {
				markCol = i;
			} else if (linesplit[i].equals("\"Grade\"") || linesplit[i].equals("Grade")) {
				gradeCol = i;
			} else if (linesplit[i].equals("\"Year\"") || linesplit[i].equals("Year")) {
				yearCol = i;
			} else if (linesplit[i].equals("\"Period\"") || linesplit[i].equals("Period")) {
				periodCol = i;
			} else if (linesplit[i].equals("\"Occ\"") || linesplit[i].equals("Occ")) {
				occCol = i;
			} else if (linesplit[i].equals("\"Name\"") || linesplit[i].equals("Name")) {
				nameCol = i;
			} else if (linesplit[i].equals("\"#Map\"") || linesplit[i].equals("#Map")) {
				mapCol = i;
			}

		}

		// Adds records to assessments
		while ((line = bf.readLine()) != null) {
			linesplit = line.split(",");
			String ass = linesplit[assCol].replaceAll("\"", "");
			Result temp = new Result(linesplit[yearCol].replaceAll("\"", ""), linesplit[periodCol].replaceAll("\"", ""), linesplit[moduleCol].replaceAll("\"", ""), linesplit[occCol], linesplit[mapCol].replaceAll("\"", ""), ass, linesplit[candCol].replaceAll("\"", ""), linesplit[nameCol].replaceAll("\"", ""), Integer.parseInt(linesplit[markCol]), linesplit[gradeCol]);
			// First checks if Assessment array is empty
			if (assessments.isEmpty()) {
				Assessment t1 = new Assessment();
				t1.addResult(temp);
				assessments.add(t1);
				// Now checks if there is already an assessment
				// object
				// With same assessment number
				// If not make new assessment object
				// Then add record
			} else if (!checkAllAss(temp.getAssessment(), assessments)) {
				Assessment t1 = new Assessment();
				t1.addResult(temp);
				assessments.add(t1);
			} else {
				// Since there is existing assessment object
				// Finds it, and adds record
				for (int i = 0; i < assessments.size(); i++) {
					if (assessments.get(i).getResults().get(0).getAssessment().equals(temp.getAssessment())) {
						assessments.get(i).addResult(temp);
					}
				}
			}

		}
	}

	/**
	 * Loops through all Assessment Objects within Assessment ArrayList
	 * If finds existing one, that matches string returns true
	 * @param assCode - Assessment code to check
	 * @return true - If have have existing assessment 
	 */
	public boolean checkAllAss(String assCode, ArrayList<Assessment> assessments) {
		for (Assessment t : assessments) {
			if (t.getResults().get(0).getAssessment().equals(assCode)) {
				// If does have assessment already
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Loops through all currently loaded Students
	 * Matches Anon Codes
	 * If matched then replaces anon codes with Student Name
	 * @param assessments - ArrayList of currently loaded Assessments
	 * @param students - Arraylist of all students
	 */
	public void deAnnonymise(ArrayList<Assessment> assessments,ArrayList<Student> students) {
		System.out.println("Starting deannonymising...");
		for (Assessment a : assessments) {
			for (Result t : a.getResults()) {
				String candKey = t.getCandKey();
				candKey = candKey.replaceAll("\"", "");

				// Checks if candKey is actually student number
				// If it's coursework, it will enter this if statement
				if (candKey.substring(candKey.length() - 2, candKey.length() - 1).equals("/")) {
					System.out.println("Coursework");
					// Removes the end /1 or /2 after student number
					candKey = candKey.substring(0, candKey.length() - 2);
					candKey = candKey.replaceAll("#", "");
					t.setCandKey(candKey);

					for (Student s : students) {
						candKey = candKey.replaceAll("#", "");
						if (candKey.equals(s.getStudentNumber())) {
							// Finds student with matching student numbers
							System.out.println("Found Student " + s.getStudentNumber() + " who matches on JTable with sNumber " + candKey);

							String modCode = t.getModuleCode().replaceAll("\"","");
							s.addMarks(modCode + " " + t.getAssessment(), t.getMark());
						}
					}
				} else {
					System.out.println("Normal exam");
					for (Student s : students) {
						candKey = candKey.replaceAll("#", "");
						if (candKey.equals(s.getAMC() + "")) {
							// Finds student with matching anonymous marking
							// code
							// Replaces it with student number
							t.setCandKey(s.getStudentNumber() + "");
							t.setName(s.getName());
							s.addMarks(t.getModuleCode().replaceAll("\"", "") + " " + t.getAssessment(), t.getMark());
						}
					}
				}
			}
		}

	}
	
	public void writeCSVFile(JTabbedPane tabbedPane){
		String filepath = "";
		JFileChooser chooser;
		chooser = new JFileChooser();
	    chooser.setDialogTitle("Select where you'd like to save the CSV file");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    chooser.setSelectedFile(new File(tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())));
	    chooser.setAcceptAllFileFilterUsed(true);

	    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	filepath = chooser.getSelectedFile()+".csv";
	      System.out.println("getCurrentDirectory(): "
	         +  chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : "
	         +  chooser.getSelectedFile());
	      }
	    else {
	      System.out.println("No Selection ");
	      }

		try {
			FileWriter writer = new FileWriter(filepath);
			JScrollPane currentScrollPane = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
			JViewport viewport = currentScrollPane.getViewport();
			JTable currentTable = (JTable) viewport.getView();

			for(int i = 0; i < currentTable.getColumnCount(); i++){
				writer.write(currentTable.getColumnName(i) + ",");
	        }
			writer.write("\n");

			for(int i=0; i< currentTable.getRowCount(); i++) {
	            for(int j=0; j < currentTable.getColumnCount(); j++) {
	            	writer.write(currentTable.getValueAt(i,j).toString()+",");
	            }
	            writer.write("\n");
	        }


			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @return JTable with collated data
	 */
	public JTable getTable() {
		return table;
	}

}
