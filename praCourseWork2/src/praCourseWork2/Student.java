package praCourseWork2;

public class Student {
	protected String name;
	protected String email;
	protected int studentNumber;
	protected String tutor;
	protected String aMC;

	
	public Student(String email,String name,int studentNumber,String tutor){
		this.name =  name;
		this.email = email;
		this.studentNumber = studentNumber;
		this.tutor = tutor;
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
