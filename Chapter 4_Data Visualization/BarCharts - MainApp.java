package packt.barchartexamples;

import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class MainApp extends Application {

    final static String belgium = "Belgium";
    final static String france = "France";
    final static String germany = "Germany";
    final static String netherlands = "Netherlands";
    final static String sweden = "Sweden";
    final static String unitedKingdom = "United Kingdom";

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();

    final XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series3 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series4 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series5 = new XYChart.Series<>();
    final XYChart.Series<String, Number> series6 = new XYChart.Series<>();

    public void simpleBarChartByCountry(Stage stage) {
        stage.setTitle("Bar Chart");
        final BarChart<String, Number> barChart
                = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Country Summary");
        xAxis.setLabel("Country");
        yAxis.setLabel("Population");

        series1.setName("1950");
        addDataItem(series1, belgium, 8639369);
        addDataItem(series1, france, 42518000);
        addDataItem(series1, germany, 68374572);
        addDataItem(series1, netherlands, 10113527);
        addDataItem(series1, sweden, 7014005);
        addDataItem(series1, unitedKingdom, 50127000);

        series2.setName("1960");
        addDataItem(series2, belgium, 9118700);
        addDataItem(series2, france, 46584000);
        addDataItem(series2, germany, 72480869);
        addDataItem(series2, netherlands, 11486000);
        addDataItem(series2, sweden, 7480395);
        addDataItem(series2, unitedKingdom, 52372000);

        series3.setName("1970");
        addDataItem(series3, belgium, 9637800);
        addDataItem(series3, france, 51918000);
        addDataItem(series3, germany, 77783164);
        addDataItem(series3, netherlands, 13032335);
        addDataItem(series3, sweden, 8042803);
        addDataItem(series3, unitedKingdom, 55632000);

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1, series2, series3);
        stage.setScene(scene);
        stage.show();
    }

    public void simpleBarChartByYear(Stage stage) {
        stage.setTitle("Bar Chart");
        final BarChart<String, Number> barChart
                = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Year Summary");
        xAxis.setLabel("Year");
        yAxis.setLabel("Population");

        String year1950 = "1950";
        String year1960 = "1960";
        String year1970 = "1970";

        series1.setName(belgium);
        addDataItem(series1, year1950, 8639369);
        addDataItem(series1, year1960, 9118700);
        addDataItem(series1, year1970, 9637800);

        series2.setName(france);
        addDataItem(series2, year1950, 42518000);
        addDataItem(series2, year1960, 46584000);
        addDataItem(series2, year1970, 51918000);

        series3.setName(germany);
        addDataItem(series3, year1950, 68374572);
        addDataItem(series3, year1960, 72480869);
        addDataItem(series3, year1970, 77783164);

        series4.setName(netherlands);
        addDataItem(series4, year1950, 10113527);
        addDataItem(series4, year1960, 11486000);
        addDataItem(series4, year1970, 13032335);

        series5.setName(sweden);
        addDataItem(series5, year1950, 7014005);
        addDataItem(series5, year1960, 7480395);
        addDataItem(series5, year1970, 8042803);

        series6.setName(unitedKingdom);
        addDataItem(series6, year1950, 50127000);
        addDataItem(series6, year1960, 52372000);
        addDataItem(series6, year1970, 55632000);

        Scene scene = new Scene(barChart, 800, 600);
        barChart.getData().addAll(series1, series2, series3, series4, series5, series6);
        stage.setScene(scene);
        stage.show();
    }

    public void stackedGraphExample(Stage stage) {
        stage.setTitle("Stacked Bar Chart");
        final StackedBarChart<String, Number> stackedBarChart
                = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Country Population");
        xAxis.setLabel("Country");
        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList(belgium, germany, france,
                        netherlands, sweden, unitedKingdom)));
        yAxis.setLabel("Population");

        series1.setName("1950");
        addDataItem(series1, belgium, 8639369);
        addDataItem(series1, france, 42518000);
        addDataItem(series1, germany, 68374572);
        addDataItem(series1, netherlands, 10113527);
        addDataItem(series1, sweden, 7014005);
        addDataItem(series1, unitedKingdom, 50127000);

        series2.setName("1960");
        addDataItem(series2, belgium, 9118700);
        addDataItem(series2, france, 46584000);
        addDataItem(series2, germany, 72480869);
        addDataItem(series2, netherlands, 11486000);
        addDataItem(series2, sweden, 7480395);
        addDataItem(series2, unitedKingdom, 52372000);

        series3.setName("1970");
        addDataItem(series3, belgium, 9637800);
        addDataItem(series3, france, 51918000);
        addDataItem(series3, germany, 77783164);
        addDataItem(series3, netherlands, 13032335);
        addDataItem(series3, sweden, 8042803);
        addDataItem(series3, unitedKingdom, 55632000);

        Scene scene = new Scene(stackedBarChart, 800, 600);
        stackedBarChart.getData().addAll(series1, series2, series3);
        stage.setScene(scene);
        stage.show();
    }

    public void addDataItem(XYChart.Series<String, Number> series,
            String x, Number y) {
        series.getData().add(new XYChart.Data<>(x, y));
    }

    @Override
    public void start(Stage stage) {
        simpleBarChartByCountry(stage);
//        simpleBarChartByYear(stage);
//        stackedGraphExample(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
