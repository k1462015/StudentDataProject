package praCourseWork2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

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
		this.setTutor(tutor);
		this.average = 0;
		setParticipation(new ArrayList<String>());

		
		aMC = "";
		
		setAssessMarks(new ArrayList<String>());
		marks = new HashMap<String,Integer>();

	}
	
	public String getName(){
		return name;
	}
	
	public String getStudentNumber(){
		String s = Integer.toString(studentNumber);
		return s;
	}
	
	public String getEmail(){
		return this.email;
		
	}
	
	public String toString(){
		return name + " (" + studentNumber + ")";
	}
	
	public void setAMC(String anon){
		this.aMC = anon;
	}
	
	public void addMarks(String modAss, int mark){
		String temp = modAss + " " + mark;
		System.out.println(temp);
		getAssessMarks().add(temp);
		marks.put(modAss, mark);
	}
	
	public ArrayList<String> getMarks(){
		return getAssessMarks(); 
	}
	
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
	
	public void addParticipation(String p){
		getParticipationArray().add(p);
	}

	public ArrayList<String> getParticipationArray() {
		return participation;
	}

	public void setParticipation(ArrayList<String> participation) {
		this.participation = participation;
	}

	public ArrayList<String> getAssessMarks() {
		return assessMarks;
	}

	public void setAssessMarks(ArrayList<String> assessMarks) {
		this.assessMarks = assessMarks;
	}

	public String getTutor() {
		return tutor;
	}

	public void setTutor(String tutor) {
		this.tutor = tutor;
	}

	
	
}
