package praCourseWork2;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ScatterPlot extends JFrame {
	
	private XYSeriesCollection dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	
	public ScatterPlot(String appTitle, String chartTitle, String module,XYSeries data){
		
		super(appTitle);
		
		dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		
		chart = ChartFactory.createScatterPlot("Compare to Average", "Average of Student's Marks", module + " mark", dataset);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
		setVisible(true);
		setSize(800,400);
		
	}

}
