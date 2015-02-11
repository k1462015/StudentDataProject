package praCourseWork2;

import java.util.ArrayList;

import javax.swing.*;


public class StudentFrame extends JFrame{
	
	public StudentFrame(){
		//Initialises frame and sets title to team name
		super("PRA Coursework - TMH");
		setSize(400,400);//MR:added size
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);//MR:added location
		
		
		JPanel panel = new JPanel();//panel to contain other components
		
		//array of Students to test the JList
		Student mus = new Student("Musta Rohman","mustarohman@gmail.com", 21234, "Mr Dude");
		Student gus = new Student("Musta Rohman","mustarohman@gmail.com", 21234, "Mr Dude");
		Student dus = new Student("Musta Rohman","mustarohman@gmail.com", 21234, "Mr Dude");
		Student jus = new Student("Musta Rohman","mustarohman@gmail.com", 21234, "Mr Dude");
		Student rus = new Student("Musta Rohman","mustarohman@gmail.com", 21234, "Mr Dude");
		ArrayList<Student> students = new ArrayList<Student>();
		
		JList list = createJList(students);
		
		//creates scrollbar and adds list to scrollbar
		 JScrollPane scroller = new JScrollPane(list,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			list.setFixedCellHeight(30);//cell formatting
			list.setFixedCellWidth(150);//same thing
			
		panel.add(list);
		panel.add(scroller);
		this.add(panel);
		setVisible(true);
		
	}
	
	public JList createJList(ArrayList<Student> students){
		
		DefaultListModel defListMod  = new DefaultListModel();//create a list of items that are editable. original list
		//does not allow you to do that. this allows more flexibility
		
		//goes through arraylist of Student objects, calls toString and adds the Strings to DefaultListModel (DLM)
		 for (Student s : students){
			 defListMod.addElement(s.toString());
		 }
		 
		 JList list = new JList(defListMod);//creates a new JList using the DLM
			
		 return list;
		 
		 
		 
	}

}
