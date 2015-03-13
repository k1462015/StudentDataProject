package praCourseWork2;

public class Result {
	protected String moduleCode;
	protected String assessment;
	protected String candKey;
	protected int mark;
	protected String grade;
	
	public Result(String moduleCode,String assessment,String candKey,int mark,String grade){
		this.moduleCode = moduleCode;
		this.assessment = assessment;
		this.candKey = candKey;
		this.mark = mark;
		this.grade = grade;
	}
	
	public String getModuleCode(){
		return moduleCode;
	}
	
	public String getAssessment(){
		return assessment;
	}
	
	public String getCandKey(){
		return candKey;
	}
	
	public int getMark(){
		return mark;
	}
	
	public String getGrade(){
		return grade;
	}
	
	public String toString(){
		return moduleCode +" "+ assessment +" "+ candKey +" "+ mark +" "+ grade;
	}
}
