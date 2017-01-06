package packt.com.packt.java.gral;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.EnumeratedData;
import de.erichseifert.gral.data.statistics.Histogram;
import de.erichseifert.gral.data.statistics.Histogram1D;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Orientation;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;
import static java.lang.System.out;

public class HistogramExample extends JFrame {

	public HistogramExample() {


		DataReader readType = DataReaderFactory.getInstance().get("text/csv");
		String fileName = "C://Jenn Personal//Packt Data Science//Chapter 4 Statistical Analysis//AgeofMarriage.csv";
		try {
			DataTable histData = (DataTable) readType.read(new FileInputStream(fileName), Integer.class);
			// Create histogram from data
			Number ageRange[] = {19,20,21,22,23,24,25,26,27,28,29,30};
			Histogram sampleHisto = new Histogram1D(histData, Orientation.VERTICAL, ageRange);
			// Create a second dimension (x axis) for plotting
			DataSource sampleHistData = new EnumeratedData(sampleHisto, 19, 1);
			// Create new bar plot
			BarPlot testPlot = new BarPlot(sampleHistData);
			// Format plot
			testPlot.setInsets(new Insets2D.Double(20.0, 50.0, 50.0, 20.0));
			testPlot.getTitle().setText("Average Age of Marriage");
			testPlot.setBarWidth(0.7);

			// Format x axis
			testPlot.getAxis(BarPlot.AXIS_X).setRange(18, 30.0);
			testPlot.getAxisRenderer(BarPlot.AXIS_X).setTickAlignment(0.0);
			testPlot.getAxisRenderer(BarPlot.AXIS_X).setTickSpacing(1);
			testPlot.getAxisRenderer(BarPlot.AXIS_X).setMinorTicksVisible(false);
			// Format y axis
			testPlot.getAxis(BarPlot.AXIS_Y).setRange(0.0, 10.0);
			testPlot.getAxisRenderer(BarPlot.AXIS_Y).setTickAlignment(0.0);
			testPlot.getAxisRenderer(BarPlot.AXIS_Y).setMinorTicksVisible(false);
			testPlot.getAxisRenderer(BarPlot.AXIS_Y).setIntersection(0);

			// Format bars
			PointRenderer renderHist = testPlot.getPointRenderers(sampleHistData).get(0);
			renderHist.setColor(GraphicsUtils.deriveWithAlpha(Color.black, 128));
			renderHist.setValueVisible(true);

			// Add plot to Swing component
			InteractivePanel pan = new InteractivePanel(testPlot);
			pan.setPannable(false);
			pan.setZoomable(false);
			add(pan);
			setSize(1500, 700);
			this.setVisible(true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	@Override
	public String getTitle() {
		return "Sample Histogram";
	}


	public static void main(String[] args) {
		new HistogramExample();
	}
}