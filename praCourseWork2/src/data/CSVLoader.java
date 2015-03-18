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
import main.MainFrame;

public class CSVLoader {
	
	public CSVLoader(){
		
	}
	
	
	public boolean checkValidCSV(JFrame frame,ArrayList<Assessment> assesments) throws IOException{
		JFileChooser choosy = new JFileChooser();
		File f = new File("C://Users//Saif//workspace");
		choosy.setCurrentDirectory(f);

		// Creates filter so user can only select CSV file
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CSV Files", "csv");
		choosy.setFileFilter(filter);
		
		// Checks if a file has been opened
		int returnValue = choosy.showOpenDialog(frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			boolean isValidFile = false;

			// Sets file to chosen file
			File file = choosy.getSelectedFile();
			// First checks if file first row/column contains numbers
			// If it does then it is not exam file
			BufferedReader bf;
			try {
				bf = new BufferedReader(new FileReader(file));
				// Finds corresponding column indexes
				// Reads first line to get column headings
				String line = bf.readLine();
				String[] linesplit = line.split(",");
				if (!linesplit[0].matches(".*\\d.*") && (linesplit[0].matches("Year") || linesplit[0].matches("\"Year\""))) {
					System.out.println("First line is "+linesplit[0]);
					isValidFile = true;
				}else{
				System.out.println("Not a exam.csv file");
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
	
	public boolean loadAnonCode(JFrame frame,ArrayList<Student> students){
		boolean anonLoaded = false;
		JFileChooser choosy = new JFileChooser();

		File f = new File("C://Users");
		choosy.setCurrentDirectory(f);

		// Creates filter so user can only select CSV file
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"CSV Files", "csv");
		choosy.setFileFilter(filter);

		int returnValue = choosy.showOpenDialog(frame);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			boolean validFile = false;

			// Just some code to help with debugging later
			File file = choosy.getSelectedFile();
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
			if (validFile) {
				try {
					BufferedReader bf = new BufferedReader(new FileReader(
							file));
					while (bf.ready()) {
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
					String results = "Annonymous marking codes imported. "
							+ succesImport
							+ " codes were \nfor known students; "
							+ failedImports + " were or unknown students";
					JOptionPane.showMessageDialog(null,
							results);

					anonLoaded = true;

				} catch (FileNotFoundException p) {
					System.out.println("File not found");
				} catch (IOException g) {
					System.out.println("Error");
				}

				System.out.println("You have chosen "
						+ choosy.getSelectedFile().getName()
						+ " to be imported");
			} else {
				JOptionPane
						.showMessageDialog(null,
								"Please upload a valid \nAnonymous marking code csv file");
			}

		}
		return anonLoaded;

	}
	


}
