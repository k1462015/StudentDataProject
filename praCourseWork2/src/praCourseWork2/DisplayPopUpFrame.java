package praCourseWork2;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DisplayPopUpFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel name;
	private JLabel emailAddress;
	private JLabel studentNumber;
	private JLabel tutor;
	private JPanel main;
	private JPanel bottom;
	
	public DisplayPopUpFrame(Student student){
		super("Student Information - Display");
		//Initialises all required fields with constructor arguments
		this.name = new JLabel(student.name);
		this.emailAddress = new JLabel(student.email);
		this.studentNumber = new JLabel("Student No. :   "+student.studentNumber);
		this.tutor = new JLabel("Tutor:     "+student.tutor);
		
		//Initialises requried JPanels
				main = new JPanel();
				bottom = new JPanel();

	
	}

}
