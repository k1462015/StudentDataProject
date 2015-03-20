package KEATSScraper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Frame containing browser and ability to scrape data
 * @author TMH
 *
 */
public class WebviewFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private String url;
	private Browser browser;
	private JButton btnFetch;
	/**
	 * Displays given URL in a browser
	 * And constructs frame
	 * @param url
	 */
	public WebviewFrame(String url){
		super("KEAT Scraper");
		this.url = url;
		initComponents();
	}
	
	
	private void initComponents(){
		//Adds bottom panel for fetch button and instructions
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BorderLayout());
		add(bottomPane,BorderLayout.SOUTH);
		
		JPanel boxIns = new JPanel();
		boxIns.setLayout(new BoxLayout(boxIns,BoxLayout.PAGE_AXIS));
		
		//Instructions
		JLabel title = new JLabel("Instructions");
		title.setFont(new Font("Calibri",Font.BOLD,30));
		
		Font font = new Font("Calibri",Font.PLAIN,20);
		JLabel instr1 = new JLabel("1.Log into Keats");
		instr1.setFont(font);
		JLabel instr2 = new JLabel("2.Go to neccasary page");
		instr2.setFont(font);
		JLabel instr3 = new JLabel("3.Click fetch data once page is 100% loaded");
		instr3.setFont(font);
		boxIns.add(title);
		boxIns.add(instr1);
		boxIns.add(instr2);
		boxIns.add(instr3);
		
		
		bottomPane.add(boxIns,BorderLayout.WEST);
		
		btnFetch = new JButton("Fetch data");
		btnFetch.setFont(new Font("Calibri",Font.BOLD,60));
		//Calls readDocument method in order to read website page
		btnFetch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				browser.readDocument();

			}

		});
		bottomPane.add(btnFetch,BorderLayout.CENTER);
		
        SwingUtilities.invokeLater(new Runnable() {

            @Override
			public void run() {
                browser = new Browser();
                add(browser,BorderLayout.CENTER);
                browser.loadURL(url);
           }     
       });
        
        bottomPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.red), bottomPane.getBorder()));
		
		
		//Default stuff
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(900,700);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	/**
	 * Returns ArrayList of Student Emails fetched from website
	 * @return emails - ArrayList of Emails
	 */
	public ArrayList<String> getEmails(){
		return browser.getEmails();
	}
	/**
	 * Returns ArrayList of Student Access times fetched from website
	 * @return arraylist of durations
	 */
	public ArrayList<String> getAccessTimes(){
		return browser.getAccessTimes();
	}
	/**
	 * Returns Browser
	 * @return browser - SwingBrowser
	 */
	public Browser getBrowser(){
		return browser;
	}
	/**
	 * Returns fetch button
	 * @return btnFetch - Fetch Button
	 */
	public JButton getButton(){
		return btnFetch;
	}
	
	
	
 	

}
