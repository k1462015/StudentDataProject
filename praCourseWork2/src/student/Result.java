package student;
/**
 * Contains student results data
 * @author TMH
 *
 */
public class Result {
	private String year;
	private String period;
	private String occ;
	private String map;
	private String name;
	private String moduleCode;
	private String assessment;
	private String candKey;
	private int mark;
	private String grade;
	
	public Result(String year,String period,String moduleCode,String occ,String map,String assessment,String candKey,String name,int mark,String grade){
		this.moduleCode = moduleCode;
		this.assessment = assessment;
		this.candKey = candKey;
		this.mark = mark;
		this.grade = grade;
		
		this.year = year;
		this.period = period;
		this.occ = occ;
		this.map = map;
		this.name = name;
		
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
	
	public String getYear(){
		return year;
	}
	
	public String getPeriod(){
		return period;
	}
	
	public String getOcc(){
		return occ;
	}
	
	public String getMap(){
		return map;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public void setCandKey(String s){
		candKey = s;
	}
	
	
	@Override
	public String toString(){
		return moduleCode + assessment + candKey + mark + grade;
	}
}
