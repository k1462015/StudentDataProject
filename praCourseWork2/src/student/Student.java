package student;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class to store all information on student
 * @author TMH
 *
 */
public class Student {
	private String name,email,tutor,aMC;
	private int studentNumber;
	private ArrayList<String> assessMarks;
	private HashMap<String,Integer> assessMarksMap;
	private ArrayList<String> lastAccessArray;
	private double average;

	/**
	 * Sets student details
	 * @param email - Student email
	 * @param name - Student name
	 * @param studentNumber - Student Number
	 * @param tutor - Student Tutor
	 */
	public Student(String email,String name,int studentNumber,String tutor){
		this.name =  name;
		this.email = email;
		this.studentNumber = studentNumber;
		this.tutor = tutor;
		average = 0;
		aMC = "";

		assessMarks = new ArrayList<String>();
		assessMarksMap = new HashMap<String,Integer>();
		lastAccessArray = new ArrayList<String>();

	}
	/**
	 * Gets Student Name
	 * @return name - Student Name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Gets Student Number
	 * @return studentNumber - Student number
	 */
	public int getStudentNumber(){
		return studentNumber;
	}
	/**
	 * Gets student Email
	 * @return email - Student Email
	 */
	public String getEmail(){
		return email;
		
	}
	/**
	 * Sets Anonymous Marking code
	 * @param anon - Anonymous Marking Code
	 */
	public void setAMC(String anon){
		this.aMC = anon;
	}
	/**
	 * Gets Students Anonymous Code
	 * @return aMC - Student Anonymous Code
	 */
	public String getAMC(){
		return aMC;
	}
	/**
	 * Adds corresponding mark to student
	 * @param modCode - Module  Code
	 * @param mark - Module mark
	 */
	public void addMarks(String modCode, int mark){
		String temp = modCode + " " + mark;
		assessMarks.add(temp);
		assessMarksMap.put(modCode, mark);
	}
	
	
	/**
	 * Calculates Average of student marks
	 * @return average - Student Average Mark
	 */
	public double calcAverage(){
		int temp = 0;
		//Loops through all module marks and calculates running total
		for (String mod : assessMarksMap.keySet()){
			temp += assessMarksMap.get(mod);
		}
		//Calculates Average
		average = temp/assessMarksMap.size();
		return average;
	}
	
	/**
	 * Adds last Access data
	 * @param lastAccess - Last Access String
	 */
	public void addLastAccess(String lastAccess){
		lastAccessArray.add(lastAccess);
	}
	
	/**
	 * Gets Last Access ArrayList
	 * @return ArrayList of Last Access
	 */
	public ArrayList<String> getLastAccessArray() {
		return lastAccessArray;
	}
	
	/**
	 * Gets Assessment Mark
	 * @return assessMarks - Assessment marks
	 */
	public ArrayList<String> getAssessMarks() {
		return assessMarks;
	}
	
	/**
	 * Gets Students tutor
	 * @return tutor - Students tutor email
	 */
	public String getTutor() {
		return tutor;
	}
	
	/**
	 * Sets students tutor email address
	 * @param tutor - Tutor Email Address
	 */
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	/**
	 * Returns Students average mark across all modules
	 * @return average - Student Average Mark
	 */
	public double getAverage(){
		return average;
	}
	
	@Override
	public String toString(){
		return name + " (" + studentNumber + ")";
	}

	
	
}
