package packt.simplelinearregressionmaven;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class MainApp extends Application {

    final XYChart.Series<String, Number> series = new XYChart.Series<>();
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis(8000000, 11000000, 1000000);
    final static String belgium = "Belgium";

    @Override
    public void start(Stage stage) {
        simpleIndexChart(stage);
    }

    public void simpleIndexChart(Stage stage) {
        stage.setTitle("Index Chart");
        final LineChart<String, Number> lineChart
                = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Belgium Population");
        yAxis.setLabel("Population");

        series.setName("Population");
        addDataItem(series, "1950", 8639369);
        addDataItem(series, "1960", 9118700);
        addDataItem(series, "1970", 9637800);
        addDataItem(series, "1980", 9846800);
        addDataItem(series, "1990", 9969310);
        addDataItem(series, "2000", 10263618);

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);
        stage.setScene(scene);
        stage.show();
    }

    public void addDataItem(XYChart.Series<String, Number> series,
            String x, Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    public static void main(String[] args) {
        launch(args);
    }

}

