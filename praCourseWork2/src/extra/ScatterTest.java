package extra;

import org.jfree.data.xy.XYSeries;

import praCourseWork2.ScatterPlot;

public class ScatterTest {

	public static void main(String[] args) {
		XYSeries data = new XYSeries("Test");
		
		data.add(74, 70);
		data.add(69, 65);
		data.add(80, 70);
		
		ScatterPlot plot = new ScatterPlot("Test", "Test","test",data);

	}

}
