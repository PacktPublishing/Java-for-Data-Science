package packt;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class PieChartSample extends Application {
    
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Europian Country Population");
        stage.setWidth(500);
        stage.setHeight(500);
 
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Belgium", 3),
                new PieChart.Data("France", 26),
                new PieChart.Data("Germany", 35),
                new PieChart.Data("Netherlands", 7),
                new PieChart.Data("Sweden", 4),
                new PieChart.Data("United Kingdom", 25));
        final PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Country Population");

        ((Group) scene.getRoot()).getChildren().add(pieChart);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
