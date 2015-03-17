package student;

import java.util.ArrayList;

public class Assessment {
	protected ArrayList<Result> results;
	
	public Assessment(){
		results = new ArrayList<Result>();
	}
	
	public void addResult(Result r){
		results.add(r);
	}
	
	public String getAssessment(Result r){
		return r.getAssessment();
	}
	
	public String getModuleCode(Result r){
		return r.getModuleCode();
	}
	
	public Result getIndex(int count){
		return results.get(count);
	}
	
	public ArrayList<Result> getResults(){
		return results;
	}
}
