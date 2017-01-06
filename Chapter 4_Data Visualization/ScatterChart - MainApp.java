package packt.scatterchartmavenexample;

import com.opencsv.CSVReader;
import java.io.FileReader;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Scatter Chart Sample");
        final NumberAxis yAxis = new NumberAxis(1400, 2100, 100);
        final NumberAxis xAxis = new NumberAxis(500000, 90000000, 1000000);
        final ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        xAxis.setLabel("Population");
        yAxis.setLabel("Decade");
        scatterChart.setTitle("Population Scatter Graph");

        XYChart.Series series = new XYChart.Series();

        try (CSVReader dataReader = new CSVReader(new FileReader("EuropeanScatterData.csv"), ',')) {
            String[] nextLine;
            while ((nextLine = dataReader.readNext()) != null) {
                int decade = Integer.parseInt(nextLine[0]);
                int population = Integer.parseInt(nextLine[1]);
                series.getData().add(new XYChart.Data(population, decade));
                System.out.println("Decade: " + decade + "  Population: " + population);
            }
        }

        scatterChart.getData().addAll(series);
        Scene scene = new Scene(scatterChart, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
