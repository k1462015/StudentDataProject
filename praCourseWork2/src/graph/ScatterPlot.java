package graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
		
		PNGListener pngListen = new PNGListener();
		JPGListener jpgListen = new JPGListener();
		
		pngItem = new JMenuItem("PNG");
		pngItem.addActionListener(pngListen);
		
		jpgItem = new JMenuItem("JPG");
		jpgItem.addActionListener(jpgListen);
		
		exportMenu.add(pngItem); exportMenu.add(jpgItem);
		
		bar.add(exportMenu);
		
		dataset = new XYSeriesCollection();
		dataset.addSeries(data);
		
		chart = ChartFactory.createScatterPlot("Compare to Average", "Average of Student's Marks", moduleCode + " mark", dataset);
		chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
		setJMenuBar(bar);
		setVisible(true);
		setSize(800,400);
		
	}
	
	
	private class PNGListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JMenuItem temp = (JMenuItem) e.getSource();
			
			JFileChooser chooser = new JFileChooser() {
				public void approveSelection(){
					
					File filePath = super.getSelectedFile();
					
					String pathStr = filePath.getPath();
					
					if(!pathStr.endsWith(".png")){//if file path doesn't have ".png"...
						pathStr += ".png"; //...appends ".png" to the end
						filePath = new File(pathStr);//Updates filePath with new path
					}
					
					if (filePath.exists() && getDialogType() == SAVE_DIALOG){//If that file exists
						int decision = JOptionPane.showConfirmDialog(this, "This file already exists. Would you like to overwrite?", "File Exists", 
								JOptionPane.YES_NO_CANCEL_OPTION);
						switch (decision){
						case JOptionPane.YES_OPTION:
								try {
									
										ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
										JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
					
								} catch (IOException e1) {
										JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
									
							}
							super.approveSelection();//Goes ahead with the file saving
						case JOptionPane.NO_OPTION:
							return;
						case JOptionPane.CLOSED_OPTION:
							return;
						case JOptionPane.CANCEL_OPTION:
							cancelSelection();
							return;
						
						}
					}
					
					try {
						
							ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
							JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
						
					
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			};
			
			chooser.setSelectedFile(new File(moduleCode+"." + temp.getText().toLowerCase()));
			
			int option = chooser.showSaveDialog(null);
			
		}
		
	}
	
	private class JPGListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem temp = (JMenuItem) e.getSource();
			
			JFileChooser chooser = new JFileChooser(){
				
				public void approveSelection(){
				
				File filePath = super.getSelectedFile();
				
				String pathStr = filePath.getPath();
				
				if(!pathStr.endsWith(".jpg")){//if file path doesn't have ".jpg"...
					pathStr += ".jpg"; 
					filePath = new File(pathStr);
				}
				
				if (filePath.exists() && getDialogType() == SAVE_DIALOG){
					int decision = JOptionPane.showConfirmDialog(this, "This file already exists. Would you like to overwrite?", "File Exists", 
							JOptionPane.YES_NO_CANCEL_OPTION);
					switch (decision){
					case JOptionPane.YES_OPTION:
							try {
								ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
								JOptionPane.showMessageDialog(null, "Chart saved as JPG file", "Success", JOptionPane.INFORMATION_MESSAGE);
							
							} catch (IOException e1) {
								JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
							}
						super.approveSelection();//Goes ahead with the file saving
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;
					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					
					}
				}
				
				try {
						ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
						JOptionPane.showMessageDialog(null, "Chart saved as JPG file", "Success", JOptionPane.INFORMATION_MESSAGE);
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
				
				}
				
			};
			
			chooser.setSelectedFile(new File(moduleCode+"." + temp.getText().toLowerCase()));
			
			int option = chooser.showSaveDialog(null);
		}
		
	}
	
	
	/*OBSOLETE
	private class ExportListener implements ActionListener{

		
		//I need to split this up into PNGListener and JPGListener. It's gotten too messy
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JMenuItem temp = (JMenuItem) e.getSource();
			
			JFileChooser chooser = new JFileChooser(){
				
				public void approveSelection(){
					
					
					File filePath = super.getSelectedFile();
					
					
					String pathStr = filePath.getPath();
					//System.out.println(filePath.getPath());
					if (temp.getText().equals("PNG")){//If PNG button was clicked
						
						if(!pathStr.endsWith(".png")){//if file path doesn't have ".png"...
							pathStr += ".png"; //...appends ".png" to the end
							filePath = new File(pathStr);//Updates filePath with new path
						}
						
						if (filePath.exists() && getDialogType() == SAVE_DIALOG){//If that file exists
							int decision = JOptionPane.showConfirmDialog(this, "This file already exists. Would you like to overwrite?", "File Exists", 
									JOptionPane.YES_NO_CANCEL_OPTION);
							switch (decision){
							case JOptionPane.YES_OPTION:
								if (temp.getText().equals("PNG")){
									try {
										
											ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
											JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
						
									}catch (IOException e1) {
											JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
										}
								}
								super.approveSelection();//Goes ahead with the file saving
							case JOptionPane.NO_OPTION:
								return;
							case JOptionPane.CLOSED_OPTION:
								return;
							case JOptionPane.CANCEL_OPTION:
								cancelSelection();
								return;
							
							}
						}
						
						try {
							
								ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
								JOptionPane.showMessageDialog(null, "Chart saved as PNG file", "Success", JOptionPane.INFORMATION_MESSAGE);
							
						
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else{//IF JPG button was clicked
						
						if(!pathStr.endsWith(".jpg")){//if file path doesn't have ".jpg"...
							pathStr += ".jpg"; 
							filePath = new File(pathStr);
						}
						
						if (filePath.exists() && getDialogType() == SAVE_DIALOG){
							int decision = JOptionPane.showConfirmDialog(this, "This file already exists. Would you like to overwrite?", "File Exists", 
									JOptionPane.YES_NO_CANCEL_OPTION);
							switch (decision){
							case JOptionPane.YES_OPTION:
								if (temp.getText().equals("PNG")){
									try {
										ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
										JOptionPane.showMessageDialog(null, "Chart saved as JPG file", "Success", JOptionPane.INFORMATION_MESSAGE);
									
								} catch (IOException e1) {
									JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
								}
								} else {
									
								}
								super.approveSelection();//Goes ahead with the file saving
							case JOptionPane.NO_OPTION:
								return;
							case JOptionPane.CLOSED_OPTION:
								return;
							case JOptionPane.CANCEL_OPTION:
								cancelSelection();
								return;
							
							}
						}
						
						try {
								ChartUtilities.saveChartAsPNG(filePath, chart, 600, 400);
								JOptionPane.showMessageDialog(null, "Chart saved as JPG file", "Success", JOptionPane.INFORMATION_MESSAGE);
							
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "Failed to export chart", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					super.approveSelection();
				}
			};
			
			chooser.setSelectedFile(new File(moduleCode+"." + temp.getText().toLowerCase()));
			
			
			
			int option = chooser.showSaveDialog(null);
			
			
			
			try {
				ImageIO.write(chartImage, temp.getText(), filePath);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}*/

}
