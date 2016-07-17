package br.com.delfos.view.graph;

import java.util.function.Function;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tooltip;

public class LineChartUtil {

	private static final String LOCATION_CSS = LineChartUtil.class.getClassLoader().getResource("css/Chart.css")
			.toExternalForm();

	/**
	 * Browsing through the Data and applying ToolTip
	 * as well as the class on hover
	 * 
	 * @param lineChart
	 * @param consumer
	 *            convert object from text tooltip
	 */
	public static void addTooltip(final LineChart<String, Integer> lineChart,
			Function<Data<String, Integer>, String> consumer) {

		lineChart.getStylesheets().add(LOCATION_CSS);

		for (XYChart.Series<String, Integer> s : lineChart.getData()) {
			for (XYChart.Data<String, Integer> d : s.getData()) {
				Tooltip.install(d.getNode(), new Tooltip(consumer.apply(d)));

				// Adding class on hover
				d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

				// Removing class on exit
				d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
			}
		}
	}

}
