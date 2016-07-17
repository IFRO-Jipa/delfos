package br.com.delfos.fxml;

import java.text.ParseException;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class ToolTipOnLineChart extends Application {

	@Override
	public void start(Stage stage) throws ParseException {
		stage.setTitle("Line Chart Sample");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		yAxis.setLabel("Events");

		final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("Events");

		XYChart.Series<String, Number> series = new XYChart.Series<>();
		series.setName("Events this Year 2");
		series.getData().add(new XYChart.Data<>("Jan", 23));
		series.getData().add(new XYChart.Data<>("Fev", 14));
		series.getData().add(new XYChart.Data<>("Mar", 15));
		series.getData().add(new XYChart.Data<>("Abr", 24));
		series.getData().add(new XYChart.Data<>("Mai", 34));
		series.getData().add(new XYChart.Data<>("Jun", 36));
		series.getData().add(new XYChart.Data<>("Jul", 22));
		series.getData().add(new XYChart.Data<>("Ago", 45));
		series.getData().add(new XYChart.Data<>("Set", 43));
		series.getData().add(new XYChart.Data<>("Out", 17));
		series.getData().add(new XYChart.Data<>("Nov", 29));
		series.getData().add(new XYChart.Data<>("Dez", 25));

		XYChart.Series<String, Number> series2 = new XYChart.Series<>();
		series2.setName("Events this Year3");
		series2.getData().add(new XYChart.Data<>("Jan", 31));
		series2.getData().add(new XYChart.Data<>("Fev", 22));
		series2.getData().add(new XYChart.Data<>("Mar", 35));
		series2.getData().add(new XYChart.Data<>("Apr", 54));
		series2.getData().add(new XYChart.Data<>("May", 24));
		series2.getData().add(new XYChart.Data<>("Jun", 26));
		series2.getData().add(new XYChart.Data<>("Jul", 44));
		series2.getData().add(new XYChart.Data<>("Aug", 42));
		series2.getData().add(new XYChart.Data<>("Sep", 45));
		series2.getData().add(new XYChart.Data<>("Oct", 16));
		series2.getData().add(new XYChart.Data<>("Nov", 12));
		series2.getData().add(new XYChart.Data<>("Dec", 44));

		XYChart.Series<String, Number> series3 = new XYChart.Series<>();
		series3.setName("Events this Year4");
		series3.getData().add(new XYChart.Data<>("Jan", 24));
		series3.getData().add(new XYChart.Data<>("Fev", 21));
		series3.getData().add(new XYChart.Data<>("Mar", 22));
		series3.getData().add(new XYChart.Data<>("Apr", 25));
		series3.getData().add(new XYChart.Data<>("May", 28));
		series3.getData().add(new XYChart.Data<>("Jun", 82));
		series3.getData().add(new XYChart.Data<>("Jul", 27));
		series3.getData().add(new XYChart.Data<>("Aug", 24));
		series3.getData().add(new XYChart.Data<>("Sep", 42));
		series3.getData().add(new XYChart.Data<>("Oct", 33));
		series3.getData().add(new XYChart.Data<>("Nov", 92));
		series3.getData().add(new XYChart.Data<>("Dec", 72));

		Scene scene = new Scene(lineChart, 800, 600);
		// scene.getStylesheets().add();
		scene.getStylesheets()
				.add(getClass().getClassLoader().getResource("br/com/delfos/fxml/chart.css").toExternalForm());
		lineChart.getData().addAll(series, series2, series3);
		stage.setScene(scene);
		stage.show();

		addTooltip(lineChart, d -> d.getXValue().toString() + "\n" + "Número de participações: " + d.getYValue());
	}

	/**
	 * Browsing through the Data and applying ToolTip
	 * as well as the class on hover
	 */
	private void addTooltip(final LineChart<String, Number> lineChart,
			Function<Data<String, Number>, String> consumidor) {
		for (XYChart.Series<String, Number> s : lineChart.getData()) {
			for (XYChart.Data<String, Number> d : s.getData()) {
				Tooltip.install(d.getNode(),
						// new Tooltip(d.getXValue().toString() + "\n" + "Number Of Events : " +
						// d.getYValue()));
						new Tooltip(consumidor.apply(d)));

				// Adding class on hover
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

				// Removing class on exit
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}