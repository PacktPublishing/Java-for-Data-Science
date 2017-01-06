package packt.com.packt.java.gral;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.plots.PiePlot.PieSliceRenderer;
import de.erichseifert.gral.plots.colors.LinearGradient;
import de.erichseifert.gral.plots.legends.ValueLegend;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;

import static java.lang.System.out;

public class DonutPlotExample extends JFrame {

	public DonutPlotExample() {

		String fileName = "C://Jenn Personal//Packt Data Science//Chapter 4 Statistical Analysis//AgeOfMarriageDonut.csv";
		try {
			//Don't include this part in book - just populating array data
			int[][] ageCount = new int[2][12];
			int col = 0;
			for(int x = 19; x < 31; x++){
				ageCount[0][col] = x;
				col++;
			}

			Scanner file = new Scanner(new File(fileName));
			int age = 0;
			while(file.hasNext()){
				age = file.nextInt();
				for(int loc = 0; loc < 12; loc++){
					if(ageCount[0][loc] == age){
						ageCount[1][loc]++;
					}
				}
			}

			DataTable donutData = new DataTable(Integer.class, Integer.class);
			for(int y = 0; y < ageCount[0].length; y++){
				//This options adds blank space if age is zero
				if(ageCount[1][y] == 0){
					donutData.add(-3, ageCount[0][y]);
				}else{
					donutData.add(ageCount[1][y], ageCount[0][y]);
				}

			}
			// Create new pie plot
			PiePlot testPlot = new PiePlot(donutData);
			// Format plot
			//Sets legend to be second column
			((ValueLegend) testPlot.getLegend()).setLabelColumn(1);
			testPlot.getTitle().setText("Donut Plot Example");
			// Change relative size of pie
			testPlot.setRadius(0.9);
			// Display a legend
			testPlot.setLegendVisible(true);
			// Add some margin to the plot area
			testPlot.setInsets(new Insets2D.Double(20.0, 20.0, 20.0, 20.0));

			PieSliceRenderer renderPie =
					(PieSliceRenderer) testPlot.getPointRenderer(donutData);
			// Change relative size of inner region
			//In GRAL a donut or doughnut plot is merely a pie plot with a hole in its center. 
			//Use the innerRradius property to change the radius relative to the outer radius:
			renderPie.setInnerRadius(0.4);
			// Change the width of gaps between segments
			renderPie.setGap(0.2);
			// Change the colors
			LinearGradient colors = new LinearGradient(Color.blue, Color.green);
			renderPie.setColor(colors);
			// Show labels
			renderPie.setValueVisible(true);
			renderPie.setValueColor(Color.WHITE);
			renderPie.setValueFont(Font.decode(null).deriveFont(Font.BOLD));

			// Add plot to Swing component
			add(new InteractivePanel(testPlot), BorderLayout.CENTER);
			setSize(1500, 700);
			setVisible(true);


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	@Override
	public String getTitle() {
		return "Donut plot";
	}

	public static void main(String[] args) {
		new DonutPlotExample();
	}




}
