package praCourseWork2;

public class Student {
	protected String name;
	protected String email;
	protected int studentNumber;
	protected String tutor;
	
	public Student(String name,String email,int studentNumber,String tutor){
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
}
