package student;
/**
 * Class to hold student result data
 * @author TMH
 *
 */
public class Result {
	private String year,name,moduleCode,assessment,candKey,grade;
	private int mark;
	
	/**
	 * 
	 * @param year - Year
	 * @param moduleCode - Module Code
	 * @param assessment - Assessment Number
	 * @param candKey - Candidate Key
	 * @param name - Student Name
	 * @param mark - Student mark
	 * @param grade - Student grde
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
	 * Returns module code
	 * @return moduleCode
	 */
	public String getModuleCode(){
		return moduleCode;
	}
	/**
	 * Returns Assessment Number
	 * @return assessment- Assessment number as string
	 */
	public String getAssessment(){
		return assessment;
	}
	/**
	 * Returns Candidate Key
	 * @return candKye - Candidate Key
	 */
	public String getCandKey(){
		return candKey;
	}
	/**
	 * Returns mark
	 * @return mark - Mark
	 */
	public int getMark(){
		return mark;
	}
	/**
	 * Returns grade
	 * @return grade - Grade
	 */
	public String getGrade(){
		return grade;
	}
	/**
	 * Return year
	 * @return year - Year
	 */
	public String getYear(){
		return year;
	}

	/**
	 * Returns students name
	 * @return name - Student name
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
