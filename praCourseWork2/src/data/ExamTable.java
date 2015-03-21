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
import javax.swing.JOptionPane;
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
 * Creates JTable using Assessment data and De-anonymises anon codes
 * @author TMH
 *
 */
public class ExamTable {
	JTable table;

	/**
	 * Constructor used to create Table
	 * @param ass - Assesment object containing results
	 * @param assesments - ArrayList of all loaded Assessment
	 * @param students - ArrayList of students
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
	 * Generates a JTable using results from Assessment data
	 * @param assessment - Assessment object
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
		boolean isAnonymised = !assessment.getResults().get(0).getCandKey().contains("#");
		if(isAnonymised){
			model.addColumn("Name");
			model.addColumn("Student Number");
		}else{
			model.addColumn("Anon Codes");
		}
		
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

		// Sets cell selection to single so only one cell is selected
		table.setCellSelectionEnabled(true);
		System.out.println("Making JTable");
		// Fetches first assessment and adds results data to model
		for (Result r : assessment.getResults()) {
			String name = r.getName();
			if (r.getName().equals("")) {
				name = r.getCandKey();
			}
			if(isAnonymised){
				model.addRow(new Object[] { name,r.getCandKey(),r.getAssessment(),r.getModuleCode(), r.getMark(), r.getGrade() });
			}else{
				model.addRow(new Object[] {r.getCandKey(),r.getAssessment(),r.getModuleCode(), r.getMark(), r.getGrade() });
			}
		}

		table.setPreferredScrollableViewportSize(new Dimension(200, 300));
		table.setFillsViewportHeight(true);
		table.setShowGrid(false);
	}
	
	/**
	 * Reads data from CSV file and adds to Assessment ArrayList
	 * @param bf - BufferedReader containing CSV file from file
	 * @param assessments - ArrayList of currently loaded assessments
	 * @throws IOException - Throws IOException if can't read file
	 */
	public void readExamData(BufferedReader bf,ArrayList<Assessment> assessments)throws IOException {
		// Finds corresponding column indexes
		// Reads first line to get column headings
		String line = bf.readLine();
		String[] linesplit = line.split(",");
		int yearCol = 0,nameCol= 0,moduleCol = 0,assCol = 0,candCol = 0,markCol = 0,gradeCol = 0;
		for (int i = 0; i < linesplit.length; i++) {
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
			}  else if (linesplit[i].equals("\"Name\"") || linesplit[i].equals("Name")) {
				nameCol = i;
			} 

		}

		// Adds records to assessments
		while ((line = bf.readLine()) != null) {
			linesplit = line.split(",");
			Result temp = new Result(linesplit[yearCol].replaceAll("\"", ""), linesplit[moduleCol].replaceAll("\"", ""), linesplit[assCol].replaceAll("\"", ""), linesplit[candCol].replaceAll("\"", ""), linesplit[nameCol].replaceAll("\"", ""), Integer.parseInt(linesplit[markCol]), linesplit[gradeCol]);
			// First checks if Assessment array is empty
			if (assessments.isEmpty()) {
				Assessment t1 = new Assessment();
				t1.addResult(temp);
				assessments.add(t1);
				// Checks if there is already an assessment object with same assessment number
				// If not make new assessment object then adds record
			} else if (!checkAllAss(temp.getAssessment(), assessments)) {
				Assessment t1 = new Assessment();
				t1.addResult(temp);
				assessments.add(t1);
			} else {
				// Since there is existing assessment object finds it, and adds record
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
	 * @param assessments - ArrayList of assessments
	 * @return true - If have existing assessment 
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
	 * Loops through all currently loaded Students and matches Anon Codes
	 * If matched then replaces anon codes with Student Name
	 * @param assessments - ArrayList of currently loaded Assessments
	 * @param students - ArrayList of all students
	 */
	public void deAnnonymise(ArrayList<Assessment> assessments,ArrayList<Student> students) {
		for (Assessment a : assessments) {
			for (Result t : a.getResults()) {
				String candKey = t.getCandKey();
				// Checks if candKey is actually student number
				// If candKey is student Number, then is coursework file
				if (candKey.substring(candKey.length() - 2, candKey.length() - 1).equals("/")) {
					//Removes the end /1 or /2 after student number
					candKey = candKey.substring(0, candKey.length() - 2);
					candKey = candKey.replaceAll("#", "");
					
					for (Student s : students) {
						// Finds student with matching student numbers
						if (candKey.equals(s.getStudentNumber()+"")) {
							t.setCandKey(s.getStudentNumber() + "");
							s.addMarks(t.getModuleCode() + " " + t.getAssessment(), t.getMark());
						}
					}
				} else {
				// If candKey is anon code
					for (Student s : students) {
						candKey = candKey.replaceAll("#", "");
						if (candKey.equals(s.getAMC() + "")) {
							// Finds student with matching anonymous marking code replaces it with student number
							t.setCandKey(s.getStudentNumber() + "");
							t.setName(s.getName());
							s.addMarks(t.getModuleCode() + " " + t.getAssessment(), t.getMark());
						}
					}
				}
			}
		}

	}
	
	/**
	 * Exports currently selected table to file
	 * @param tabbedPane - Currently loaded tabbedPane
	 */
	public void exportCSVToFile(JTabbedPane tabbedPane){
		
		JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle("Select where you'd like to save the CSV file");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    String tabName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
	    chooser.setSelectedFile(new File(tabName.replaceAll("/", "-")));
	    chooser.setAcceptAllFileFilterUsed(true);
	    
	    //If user chooses valid directory then saves CSV there
	    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	String filepath = chooser.getSelectedFile()+".csv";      
	      try {
	    	  	//Uses a file choo
				FileWriter writer = new FileWriter(filepath);
				JScrollPane currentScrollPane = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
				JViewport viewport = currentScrollPane.getViewport();
				JTable currentTable = (JTable) viewport.getView();
				
				//Goes through column headings and adds to CSV file
				for(int i = 0; i < currentTable.getColumnCount(); i++){
					writer.write(currentTable.getColumnName(i) + ",");
		        }
				writer.write("\n");
				//Goes through rows and adds to CSV file
				for(int i=0; i< currentTable.getRowCount(); i++) {
		            for(int j=0; j < currentTable.getColumnCount(); j++) {
		            	writer.write(currentTable.getValueAt(i,j).toString()+",");
		            }
		            writer.write("\n");
		        }
				writer.close();
				JOptionPane.showMessageDialog(null, "Table data successfully exported as CSV file");
			} catch (IOException e) {
				e.printStackTrace();
			}
	      }

		
	}


	/**
	 * @return JTable with collated data
	 */
	public JTable getTable() {
		return table;
	}

}
