package student;
/**
 * Contains student results data
 * @author TMH
 *
 */
public class Result {
	private String year;
	private String name;
	private String moduleCode;
	private String assessment;
	private String candKey;
	private int mark;
	private String grade;
	
	/**
	 * Initializes all required fields for results class
	 * @param year
	 * @param period
	 * @param moduleCode
	 * @param occ
	 * @param map
	 * @param assessment
	 * @param candKey
	 * @param name
	 * @param mark
	 * @param grade
	 */
	public Result(String year,String moduleCode,String assessment,String candKey,String name,int mark,String grade){
		this.moduleCode = moduleCode;
		this.assessment = assessment;
		this.candKey = candKey;
		this.mark = mark;
		this.grade = grade;
		
		this.year = year;
		this.name = name;
		
	}
	
	/**
	 * 
	 * @return moduleCode
	 */
	public String getModuleCode(){
		return moduleCode;
	}
	/**
	 * 
	 * @return Assessment number as string
	 */
	public String getAssessment(){
		return assessment;
	}
	/**
	 * 
	 * @return candidate key
	 */
	public String getCandKey(){
		return candKey;
	}
	/**
	 * 
	 * @return mark
	 */
	public int getMark(){
		return mark;
	}
	/**
	 * 
	 * @return grade
	 */
	public String getGrade(){
		return grade;
	}
	/**
	 * 
	 * @return year
	 */
	public String getYear(){
		return year;
	}

	/**
	 * 
	 * @return name of Student
	 */
	public String getName(){
		return name;
	}
	/**
	 * Sets name of student
	 * @param n - name
	 */
	public void setName(String n){
		name = n;
	}
	/**
	 * Sets candidate key column
	 * @param s - candKey
	 */
	public void setCandKey(String s){
		candKey = s;
	}
	
	
	@Override
	public String toString(){
		return moduleCode + assessment + candKey + mark + grade;
	}
}
