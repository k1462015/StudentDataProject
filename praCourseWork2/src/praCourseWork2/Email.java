package praCourseWork2;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Email extends JFrame {
	private JPanel main;
	
	public Email(){
		setVisible(true);
		main = new JPanel();
		main.setSize(300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
