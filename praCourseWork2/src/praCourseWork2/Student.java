package praCourseWork2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Student {
	protected String name;
	protected String email;
	protected int studentNumber;
	protected String tutor;
	protected String aMC;
	protected ArrayList<String> assessMarks;
	protected HashMap<String,Integer> marks;
	protected double average;
	
	public Student(String email,String name,int studentNumber,String tutor){
		this.name =  name;
		this.email = email;
		this.studentNumber = studentNumber;
		this.tutor = tutor;
		this.average = 0;
		
		aMC = "";
		
		assessMarks = new ArrayList<String>();
		marks = new HashMap<String,Integer>();

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
		System.out.println(temp);
		assessMarks.add(temp);
		marks.put(modAss, mark);
	}
	
	public ArrayList<String> getMarks(){
		return assessMarks; 
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
	
	
}
