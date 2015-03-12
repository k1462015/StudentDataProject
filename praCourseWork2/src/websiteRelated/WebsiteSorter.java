package websiteRelated;

import java.util.ArrayList;


public class WebsiteSorter {
	private ArrayList<String> emails;
	private ArrayList<String> durations;
	private ArrayList<String> emailsSorted;
	private ArrayList<String> durationsSorted;

	
	public WebsiteSorter(ArrayList<String> emails,ArrayList<String> durations){
		this.emails = emails;
		this.durations = durations;
		
		sort();
	}
	
	public void sort(){
		ArrayList<String> emailsSorted = new ArrayList<String>();
		ArrayList<String> durationsSorted = new ArrayList<String>();
		for(String s:emails){
			System.out.println(s.substring(s.lastIndexOf("c3\">")+4, s.indexOf("</TD>")));
			emailsSorted.add(s.substring(s.lastIndexOf("c3\">")+4, s.indexOf("</TD>")));
		}
		
		for(String s:durations){
			System.out.println(s.substring(s.lastIndexOf("c6\">")+4, s.indexOf("</TD>")));
			durationsSorted.add(s.substring(s.lastIndexOf("c6\">")+4, s.indexOf("</TD>")));
		}
	}
	
	public ArrayList<String> getSortedEmails(){
		return emailsSorted;
	}
	
	public ArrayList<String> getSortedDurations(){
		return durationsSorted;
	}

}
