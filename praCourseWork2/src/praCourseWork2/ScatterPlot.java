package praCourseWork2;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class ScatterPlot extends JFrame {
	
	public ScatterPlot(String appTitle, String chartTitle){
		
		super(appTitle);
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = new XYSeries("example");
		
		series.add(74, 70);
		series.add(73, 72);
		series.add(75, 69);
		series.add(65, 70);
		series.add(77, 80);
		
		dataset.addSeries(series);
		
		JFreeChart chart = ChartFactory.createScatterPlot("Grades", "Average", "Assessment Grade", dataset);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
		setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800,400);
		
	}

}
