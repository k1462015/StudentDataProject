package student;

import java.util.ArrayList;

import praCourseWork2.Result;

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
