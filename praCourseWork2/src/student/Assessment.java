package student;

import java.util.ArrayList;
/**
 * Assessment class containing ArrayList of results
 * @author TMH
 *
 */
public class Assessment {
	private ArrayList<Result> results;
	/**
	 * Initializes ArrayList of results
	 */
	public Assessment(){
		results = new ArrayList<Result>();
	}
	
	/**
	 * Adds to ArrayList of results
	 * @param r - result object 
	 */
	public void addResult(Result r){
		results.add(r);
	}
	
	/**
	 * Retrieves results from ArrayList of results using index number
	 * @param index - index required from ArrayList of results
	 * @return Results corresponding index
	 */
	public Result getResultAtIndex(int index){
		return results.get(index);
	}
	
	/**
	 * Returns Results ArrayList from Assessment class
	 * @return Results ArrayList
	 */
	public ArrayList<Result> getResults(){
		return results;
	}
}
