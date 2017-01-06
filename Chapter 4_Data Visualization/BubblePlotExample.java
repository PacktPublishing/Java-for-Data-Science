package packt.com.packt.java.gral;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.XYPlot.XYPlotArea2D;
import de.erichseifert.gral.plots.points.SizeablePointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;


public class BubblePlotExample extends JFrame {

	public BubblePlotExample() {

		DataReader readType = DataReaderFactory.getInstance().get("text/csv");
		String fileName = "C://Jenn Personal//Packt Data Science//Chapter 4 Statistical Analysis//MarriageByYears.csv";
		try {
			DataTable bubbleData = (DataTable) readType.read(new FileInputStream(fileName), Integer.class, Integer.class, Integer.class);
			// Create a new data series (optional)
			DataSeries bubbleSeries = new DataSeries("Bubble", bubbleData);
			// Create a new xy-plot
			XYPlot testPlot = new XYPlot(bubbleSeries);
			// Format plot
			testPlot.setInsets(new Insets2D.Double(30.0));  // Add a margin to the plot
			testPlot.setBackground(new Color(0.75f, 0.75f, 0.75f));
			// Format plot area
			XYPlotArea2D areaProp = (XYPlotArea2D) testPlot.getPlotArea();
			areaProp.setBorderColor(null);   // Remove border of plot area
			areaProp.setMajorGridX(false);   // Disable vertical grid
			areaProp.setMajorGridY(false);   // Disable horizontal grid
			areaProp.setClippingArea(null);  // Disable clipping

			// Format axes
			testPlot.getAxisRenderer(XYPlot.AXIS_X).setShapeVisible(false);  // Disable x axis
			testPlot.getAxisRenderer(XYPlot.AXIS_X).setTicksVisible(false);  // Disable tick marks on x axis
			testPlot.getAxisRenderer(XYPlot.AXIS_Y).setShapeVisible(false);  // Disable y axis
			testPlot.getAxisRenderer(XYPlot.AXIS_Y).setTicksVisible(false);  // Disable tick marks on y axis
			testPlot.getAxis(XYPlot.AXIS_X).setRange(1940, 2020);  // Scale x axis from -10 to 10
			testPlot.getAxis(XYPlot.AXIS_Y).setRange(17, 30);  // Scale y axis from -10 to 10

			// Format data series
			Color color = GraphicsUtils.deriveWithAlpha(Color.black, 96);
			SizeablePointRenderer renderBubble = new SizeablePointRenderer();
			renderBubble.setShape(new Ellipse2D.Double(-3.5, -3.5, 4.0, 4.0));  // shape of data points
			renderBubble.setColor(color);  // color of data points
			renderBubble.setColumn(2);  // data column which determines the scaling of data point shapes
			testPlot.setPointRenderers(bubbleSeries, renderBubble);  // Assign the point renderer to the data series

			add(new InteractivePanel(testPlot), BorderLayout.CENTER);  // Add the plot to the Swing component
			setSize(new Dimension(1500, 700));
			setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	public String getTitle() {
		return "Spiral bubble plot";
	}


	public static void main(String[] args) {
		new BubblePlotExample();
	}

}
