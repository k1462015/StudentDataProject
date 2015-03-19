package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import student.Assessment;
import student.Student;
/**
 * Provides methods to read CSV data
 * @author TMH
 *
 */
public class CSVLoader {
	/**
	 * Empty constructor to allow access to loadExamCSV method and loadAnonCode method
	 */
	public CSVLoader(){
		
	}
	
	/**
	 * Checks if user has uploaded a valid CSV file, if they have it will call the read the data from the CSV
	 * @param assesments -ArrayList of assessments
	 * @return - true if valid file has been uploaded
	 * @throws IOException
	 */
	public boolean loadExamCSV(ArrayList<Assessment> assesments) throws IOException{
		JFileChooser fileChooser = new JFileChooser();

		// Creates filter so user can only select CSV file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(filter);
		
		// Checks if a file has been opened
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			boolean isValidFile = false;
			// Sets file to chosen file
			File file = fileChooser.getSelectedFile();
			// First checks if file first row/column contains numbers
			// If doesn't contain numbers then is valid exam file
			BufferedReader bf;
			try {
				bf = new BufferedReader(new FileReader(file));
				// Finds corresponding column indexes
				// Reads first line to get column headings
				String line = bf.readLine();
				String[] linesplit = line.split(",");
				if (!linesplit[0].matches(".*\\d.*") && (linesplit[0].matches("Year") || linesplit[0].matches("\"Year\""))) {
					isValidFile = true;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (isValidFile) {
				BufferedReader bf1 = new BufferedReader(new FileReader(file));
				new ExamTable().readExamData(bf1, assesments);		

			} else {
				JOptionPane.showMessageDialog(null,
						"Please upload a valid exam.csv file");
			}
			return isValidFile;
		}
		return false;
		
	}
	
	/**
	 * 
	 * @param students -  Arraylist of students to allow anonymous marking codes to be matched with student Numbers
	 * @return true - if user has uploaded a valid Anonymous marking code CSV
	 */
	public boolean loadAnonCode(ArrayList<Student> students){
		boolean anonLoaded = false;
		JFileChooser fileChooser = new JFileChooser();

		// Creates filter so user can only select CSV file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
		fileChooser.setFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			boolean validFile = false;

			// Just some code to help with debugging later
			File file = fileChooser.getSelectedFile();
			int succesImport = 0;
			int totalImports = 0;
			// First checks if valid file
			BufferedReader bfCheck;
			try {
				bfCheck = new BufferedReader(new FileReader(file));

				String line = bfCheck.readLine();
				String[] linesplit = line.split(",");
				if (linesplit[0].matches((".*\\d.*"))) {
					validFile = true;
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//If valid file, then reads data
			if (validFile) {
				try {
					BufferedReader bf = new BufferedReader(new FileReader(file));
					while (bf.ready()) {
						//Matches student Number with anon codes
						String[] line = bf.readLine().split(",");
						for (Student s : students) {
							int temp = Integer.parseInt(line[0]);
							if (temp == s.getStudentNumber()) {
								s.setAMC(line[1]);
								succesImport++;
							}
						}

						totalImports++;
					}
					int failedImports = totalImports - succesImport;
					String results = "Annonymous marking codes imported. " + succesImport + " codes were \nfor known students; " + failedImports + " were or unknown students";
					JOptionPane.showMessageDialog(null,results);

					anonLoaded = true;

				} catch (FileNotFoundException p) {
					System.out.println("File not found");
				} catch (IOException g) {
					System.out.println("Error");
				}
			} else {
				JOptionPane.showMessageDialog(null,"Please upload a valid \nAnonymous marking code csv file");
			}

		}
		return anonLoaded;

	}
	


}
