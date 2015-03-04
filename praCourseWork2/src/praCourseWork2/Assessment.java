package praCourseWork2;

import java.util.ArrayList;

public class Assessment {
	protected ArrayList<Result> results;
	
	public Assessment(){
		results = new ArrayList<Result>();
	}
	
	public void addResult(Result r){
		results.add(r);
	}
	
	public String getModuleCode(Result r){
		return r.getModuleCode();
	}
	
	public String getAssessment(Result r){
		return r.getAssessment();
	}
	
	public Result getFirst(){
		return results.get(0);
	}
	
}
