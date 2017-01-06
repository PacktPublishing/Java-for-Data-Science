package packt.scatterchartmaven2;

/*
    <dependencies>
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>3.7</version>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-math3</artifactId>
            <version>3.6.1</version>
        </dependency>
    </dependencies>
*/

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
        final NumberAxis xAxis = new NumberAxis(0, 100, 10);
        final NumberAxis yAxis = new NumberAxis(0, 130000, 10000);        
        final ScatterChart<Number,Number> sc = new
            ScatterChart<Number,Number>(xAxis,yAxis);
        xAxis.setLabel("Age");                
        yAxis.setLabel("Income");
        sc.setTitle("Camping Inclination");
       
        /*
23,45600,1
26,32000,0
45,65700,1
29,25300,0
72,55600,1
24,28700,1
56,125300,1
22,34200,1
28,32800,1
32,24600,1
25,36500,1
67,76800,0
25,14500,1
86,58900,0
        */
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Camps");
        series1.getData().add(new XYChart.Data(23,45600));
        series1.getData().add(new XYChart.Data(45,65700));
        series1.getData().add(new XYChart.Data(72,55600));
        series1.getData().add(new XYChart.Data(24,28700));
        series1.getData().add(new XYChart.Data(22,34200));
        series1.getData().add(new XYChart.Data(28,32800));
        series1.getData().add(new XYChart.Data(32,24600));
        series1.getData().add(new XYChart.Data(25,36500));
        series1.getData().add(new XYChart.Data(22,43600));
        series1.getData().add(new XYChart.Data(78,125700));
        series1.getData().add(new XYChart.Data(73,56500));
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Doesn't Camp");
        series2.getData().add(new XYChart.Data(26,91000));
        series2.getData().add(new XYChart.Data(29,85300));
        series2.getData().add(new XYChart.Data(67,76800));
        series2.getData().add(new XYChart.Data(86,58900));
        series2.getData().add(new XYChart.Data(56,125300));
        series2.getData().add(new XYChart.Data(25,125000));
        series2.getData().add(new XYChart.Data(29,87600));
        series2.getData().add(new XYChart.Data(65,79300));
 
        sc.getData().addAll(series1, series2);
        Scene scene  = new Scene(sc, 500, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
