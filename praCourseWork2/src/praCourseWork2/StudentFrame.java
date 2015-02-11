package praCourseWork2;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.Position;


public class StudentFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public StudentFrame(){
		//Initialises frame and sets title to team name
		super("PRA Coursework - TMH");
		setSize(400,400);//MR:added size
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);//MR:added location
		
		JPanel panel = new JPanel();//panel to contain other components
		
		//array of Students to test the JList
		Student mus = new Student("Musta ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student gus = new Student("Rohman ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student dus = new Student("Musa ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student jus = new Student("uta ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student rus = new Student("Mta ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student dus1 = new Student("Mst ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student jus1 = new Student("tahmidul ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student rus1 = new Student("tahmi ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student dus2 = new Student("dul ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student jus2 = new Student("tam ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student rus2 = new Student("sta ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student dus3 = new Student("asdf ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student jus3 = new Student("nty ","mustarohman@gmail.com", 21234, "Mr Dude");
		Student rus3 = new Student("has","mustarohman@gmail.com", 21234, "Mr Dude");
		
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(mus);
		students.add(gus);
		students.add(dus);
		students.add(jus);
		students.add(rus);
		students.add(dus1);
		students.add(jus1);
		students.add(rus1);
		students.add(dus2);
		students.add(jus2);
		students.add(rus2);
		students.add(dus3);
		students.add(jus3);
		students.add(rus3);
		
		ArrayList<Student> searchStudents = new ArrayList<Student>();
		ArrayList<Student> empty = new ArrayList<Student>();
		JList list = createJList(students);
		
		
		JTextField search = new JTextField(10);
		search.addKeyListener(new KeyAdapter() {
	        public void keyReleased(KeyEvent e) {
	        	//clear the list
	        	DefaultListModel listModel = (DefaultListModel) list.getModel();
	            listModel.removeAllElements();
	            //get whatever user types into text field
	        	StringBuffer buffer = new StringBuffer(search.getText().substring(0,search.getText().length()));      
	        	//store all matching students in serachStudent arraylist
	        	for(Student i:students){
	        		if (i.getName().contains(buffer)){
	        			searchStudents.add(i);
	        		}
	        	}
	        	list = createJList(searchStudents);
	        	//System.out.println(buffer);
	        	                                  
	            }
	     });
			
			list.setFixedCellHeight(30);//cell formatting
			list.setFixedCellWidth(150);//same thing
			panel.add(search);
		panel.add(new JScrollPane(list));
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
