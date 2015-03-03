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
	


}
