package packt.simplelinearregressionmaven;

/*
    <dependencies>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-math3</artifactId>
            <version>3.6.1</version>
        </dependency>
    </dependencies>
*/

import static java.lang.System.out;
import java.text.NumberFormat;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class MainApp extends Application {
    final XYChart.Series<Number, Number> originalSeries = new XYChart.Series<>();
    final XYChart.Series<Number, Number> projectedSeries = new XYChart.Series<>();
    final NumberAxis xAxis = new NumberAxis(1940, 2050, 10);
    final NumberAxis yAxis = new NumberAxis(8000000, 12000000, 1000000);
    final static String belgium = "Belgium";

    @Override
    public void start(Stage stage) {

//Belgium	1950	8639369
//Belgium	1960	9118700
//Belgium	1970	9637800
//Belgium	1980	9846800
//Belgium	1990	9969310
//Belgium	2000	10263618
        double[][] input = {
            {1950, 8639369},
            {1960, 9118700},
            {1970, 9637800},
            {1980, 9846800},
            {1990, 9969310},
            {2000, 10263618}};
        double[] predictionYears = {1950, 1960, 1970, 1980, 1990, 2000, 2010, 2020, 2030, 2040};

        NumberFormat yearFormat = NumberFormat.getNumberInstance();
        yearFormat.setMaximumFractionDigits(0);
        yearFormat.setGroupingUsed(false);
        NumberFormat populationFormat = NumberFormat.getNumberInstance();
        populationFormat.setMaximumFractionDigits(0);
        
        SimpleRegression regression = new SimpleRegression();
        regression.addData(input);
        projectedSeries.setName("Projected");
        for (int i = 0; i < predictionYears.length; i++) {
            out.println(yearFormat.format(predictionYears[i]) + "-"
                    + populationFormat.format(regression.predict(predictionYears[i])));
            addDataItem(projectedSeries, predictionYears[i],
                    regression.predict(predictionYears[i]));
        }

        displayAttribute("Slope",regression.getSlope());
        displayAttribute("Intercept", regression.getIntercept());
        displayAttribute("InterceptStdEr", regression.getInterceptStdErr());
        displayAttribute("MeanSquareError", regression.getMeanSquareError());
        displayAttribute("N", + regression.getN());
        displayAttribute("R", + regression.getR());
        displayAttribute("RSquare", regression.getRSquare());

        //Create index chart
        stage.setTitle("Simple Linear Regression");
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return (object.intValue()) + "";
            }

            @Override
            public Number fromString(String string) {
                return 0;
            }
        });

        final LineChart<Number, Number> lineChart
                = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Belgium Population");
        yAxis.setLabel("Population");

        originalSeries.setName("Actual");
        addDataItem(originalSeries, 1950, 8639369);
        addDataItem(originalSeries, 1960, 9118700);
        addDataItem(originalSeries, 1970, 9637800);
        addDataItem(originalSeries, 1980, 9846800);
        addDataItem(originalSeries, 1990, 9969310);
        addDataItem(originalSeries, 2000, 10263618);

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(originalSeries, projectedSeries);
        stage.setScene(scene);
        stage.show();
    }
    
    public void displayAttribute(String attribute, double value) {        
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        out.println(attribute + ": " + numberFormat.format(value));
    }

    public void addDataItem(XYChart.Series<Number, Number> series,
            Number x, Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
