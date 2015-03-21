package graph;


import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Frame with scatter plot graph
 * @author TMH
 *
 */
public class ScatterPlot extends JFrame {
	
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JMenuBar menuBar;
	private JMenu exportMenu;
	private JMenuItem pngItem,jpgItem;
	private String moduleCode;

	/**
	 * Initiliazes graph information
	 * @param appTitle - Title of frame
	 * @param chartTitle - Title of chart
	 * @param module - Module Code 
	 * @param data - Holds datapoints of graph
	 */
	public ScatterPlot(String appTitle, String chartTitle, String module,XYSeries data){
		super(appTitle);
		moduleCode = module;
		dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		
		initUi();

		
	}
	
	private void initUi(){
		menuBar = new JMenuBar();
		exportMenu = new JMenu("Export as...");
		
		//Creates scatter plot with title and axis and plots the datapoints on the graph
		chart = ChartFactory.createScatterPlot("Compare Student's " + moduleCode + " Mark to their Average Mark", "Average of Student's Marks", moduleCode + " mark", dataset);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
		//Adds PNG and JPG export option to menu and adds corresponding ActionListener 	
		pngItem = new JMenuItem("PNG");
		pngItem.addActionListener(new PNGListener(chart, moduleCode));
		jpgItem = new JMenuItem("JPG");
		jpgItem.addActionListener(new JPGListener(chart, moduleCode));
		
		exportMenu.add(pngItem); exportMenu.add(jpgItem);
		
		
		menuBar.add(exportMenu);
		
		setJMenuBar(menuBar);
		setSize(800,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
	
}
