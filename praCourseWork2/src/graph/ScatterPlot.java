package graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Frame with generated scatter plot
 * @author TMH
 *
 */
public class ScatterPlot extends JFrame {
	
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private JMenuBar bar;
	private JMenu exportMenu;
	private JMenuItem pngItem;
	private JMenuItem jpgItem;
	private String moduleCode;

	
	public ScatterPlot(String appTitle, String chartTitle, String module,XYSeries data){
		
		super(appTitle);
		moduleCode = module;
		bar = new JMenuBar();
		exportMenu = new JMenu("Export as...");
		
		
		
		dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		
		chart = ChartFactory.createScatterPlot("Compare to Average", "Average of Student's Marks", moduleCode + " mark", dataset);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
		PNGListener pngListen = new PNGListener(chart, moduleCode);
		JPGListener jpgListen = new JPGListener(chart, moduleCode);
		
		pngItem = new JMenuItem("PNG");
		pngItem.addActionListener(pngListen);
		
		jpgItem = new JMenuItem("JPG");
		jpgItem.addActionListener(jpgListen);
		
		exportMenu.add(pngItem); exportMenu.add(jpgItem);
		
		bar.add(exportMenu);
		
		setJMenuBar(bar);
		setVisible(true);
		setSize(800,400);
		
	}
	
	
	
	
}
