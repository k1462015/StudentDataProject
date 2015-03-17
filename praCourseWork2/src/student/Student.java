package student;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Contains all information on student
 * @author TMH
 *
 */
public class Student {
	protected String name;
	protected String email;
	protected int studentNumber;
	private String tutor;
	protected String aMC;
	private ArrayList<String> assessMarks;
	protected HashMap<String,Integer> marks;
	protected double average;
	private ArrayList<String> participation;

	
	public Student(String email,String name,int studentNumber,String tutor){
		this.name =  name;
		this.email = email;
		this.studentNumber = studentNumber;
		this.tutor = tutor;
		this.average = 0;
		setParticipation(new ArrayList<String>());

		
		aMC = "";
		
		setAssessMarks(new ArrayList<String>());
		marks = new HashMap<String,Integer>();

	}
	
	public String getName(){
		return name;
	}
	
	public int getStudentNumber(){
		return studentNumber;
	}
	
	public String getEmail(){
		return email;
		
	}
	
	@Override
	public String toString(){
		return name + " (" + studentNumber + ")";
	}
	
	public void setAMC(String anon){
		this.aMC = anon;
	}
	
	public String getAMC(){
		return aMC;
	}
	
	public void addMarks(String modAss, int mark){
		String temp = modAss + " " + mark;
		System.out.println(temp);
		assessMarks.add(temp);
		marks.put(modAss, mark);
	}
	
	
	/**
	 * Calculates average of marks
	 * @return average mark
	 */
	public double calcAverage(){
		
		int temp = 0;//to store total of the marks
		
		//Gets all of the keys of the hashmap, loops through each of them and gets 
		//the value, and finally adds the value to the temp var.
		for (String mod : marks.keySet()){
			temp += marks.get(mod);
		}
		
		average = temp/marks.size();
		System.out.println(average);//testing
		return average;
	}
	
	/**
	 * Adds participation data
	 * @param participation
	 */
	public void addParticipation(String participation){
		getParticipationArray().add(participation);
	}
	
	/**
	 * 
	 * @return Arraylist of participation data
	 */
	public ArrayList<String> getParticipationArray() {
		return participation;
	}
	
	/**
	 * 
	 * @param participation
	 */
	public void setParticipation(ArrayList<String> participation) {
		this.participation = participation;
	}
	
	/**
	 * 
	 * @return Assessment marks
	 */
	public ArrayList<String> getAssessMarks() {
		return assessMarks;
	}
	
	/**
	 * 
	 * @param assessMarks
	 */
	public void setAssessMarks(ArrayList<String> assessMarks) {
		this.assessMarks = assessMarks;
	}
	/**
	 * 
	 * @return tutor
	 */
	public String getTutor() {
		return tutor;
	}
	
	/**
	 * 
	 * @param tutor
	 */
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	/**
	 * 
	 * @return average mark
	 */
	public double getAverage(){
		return average;
	}

	
	
}
