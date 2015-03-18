package data;

import java.util.ArrayList;

import student.Student;
import studentdata.Connector;
import studentdata.DataTable;
/**
 * Connects to server and retrieves student data
 * @author TMH
 *
 */
public class ServerConnect {

	public ServerConnect(ArrayList<Student> students) {
		// Create a Connector object and open the connection to the server
		Connector server = new Connector();
		boolean success = server.connect("TMH","944ff2da7cd193c64ec9459a42f38786");

		if (success == false) {
			System.out.println("Fatal error: could not open connection to server");
			System.exit(1);
		}

		DataTable data = server.getData();

		int rowCount = data.getRowCount();
		ArrayList<String> studentDetails = new ArrayList<String>();
		String tempWord = "";
		// Loops through all data from server and puts into a studentDetail
		// arrayList
		for (int row = 0; row < rowCount; ++row) {
			for (int col = 0; col < 4; ++col) {
				if (col > 0) {
					tempWord += ",";
				}
				tempWord += data.getCell(row, col);
			}
			studentDetails.add(tempWord);
			// Makes tempWord blank again
			tempWord = "";
		}
		for (int i = 0; i < studentDetails.size(); i++) {
			String temp = studentDetails.get(i);
			// Splits the student details according to where the comma is
			String[] studentDetails1 = temp.split(",");
			int studentNumber = Integer.parseInt(studentDetails1[2]);

			Student temp1 = new Student(studentDetails1[0], studentDetails1[1],studentNumber, studentDetails1[3]);
			students.add(temp1);

		}
	}

}
