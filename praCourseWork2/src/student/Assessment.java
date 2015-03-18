package student;

import java.util.ArrayList;
/**
 * Assessment containing Arraylist of results
 * @author TMH
 *
 */
public class Assessment {
	private ArrayList<Result> results;
	
	public Assessment(){
		results = new ArrayList<Result>();
	}
	
	/**
	 * Adds result to ArrayList of results
	 * @param r - result object 
	 */
	public void addResult(Result r){
		results.add(r);
	}
	
	/**
	 * Retrieves results from arraylist of results using index number
	 * @param index
	 * @return
	 */
	public Result getIndex(int index){
		return results.get(index);
	}
	
	/**
	 * 
	 * @return Results ArrayList
	 */
	public ArrayList<Result> getResults(){
		return results;
	}
}
