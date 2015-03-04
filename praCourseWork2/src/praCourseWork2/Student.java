package praCourseWork2;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Student {
	protected String name;
	protected String email;
	protected int studentNumber;
	protected String tutor;
	protected String aMC;
	protected ArrayList<String> assessMarks;

	
	public Student(String email,String name,int studentNumber,String tutor){
		this.name =  name;
		this.email = email;
		this.studentNumber = studentNumber;
		this.tutor = tutor;
		aMC = "";
		
		assessMarks = new ArrayList<String>();

	}
	
	public String getName(){
		return name;
	}
	
	public String getStudentNumber(){
		String s = Integer.toString(studentNumber);
		return s;
	}
	
	public String toString(){
		return name + " (" + studentNumber + ")";
	}
	
	public void setAMC(String anon){
		this.aMC = anon;
	}
	
	public void addMarks(String modAss, int mark){
		String temp = modAss + " " + mark;
		assessMarks.add(temp);
	}
}
