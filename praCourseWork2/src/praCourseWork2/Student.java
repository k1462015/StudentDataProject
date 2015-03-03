package praCourseWork2;

import java.util.ArrayList;

public class Student {
	protected String name;
	protected String email;
	protected int studentNumber;
	protected String tutor;
	protected String aMC;
	protected ArrayList<Integer> assessMarks;

	
	public Student(String email,String name,int studentNumber,String tutor){
		this.name =  name;
		this.email = email;
		this.studentNumber = studentNumber;
		this.tutor = tutor;
		this.tutor = "";
		aMC = "";
		
		assessMarks = new ArrayList<Integer>();

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
}
