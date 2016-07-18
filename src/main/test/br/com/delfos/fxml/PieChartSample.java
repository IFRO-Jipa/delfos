package br.com.delfos.fxml;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PieChartSample extends Application {

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Imported Fruits");
		stage.setWidth(700);
		stage.setHeight(700);

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data("Grapefruit", 13), new PieChart.Data("Oranges", 25), new PieChart.Data("Plums", 10),
				new PieChart.Data("Pears", 22), new PieChart.Data("Apples", 30));

		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle("Imported Fruits");
		final Label caption = new Label("");
		caption.setTextFill(Color.WHITE);
		caption.setStyle("-fx-font: 24 arial;");

		MenuItem menu = new MenuItem("Salvar como imagem");
		menu.setOnAction(evt -> print(chart));

		ContextMenu context = new ContextMenu(menu);
		chart.setOnMouseClicked(evt -> {
			if (MouseButton.SECONDARY.equals(evt.getButton())) {
				context.show(stage, evt.getScreenX(), evt.getScreenY());
			}
		});

		((Group) scene.getRoot()).getChildren().addAll(chart, caption);
		stage.setScene(scene);
		// scene.getStylesheets().add("piechartsample/Chart.css");
		stage.show();

		for (final PieChart.Data data : chart.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				caption.setTranslateX(e.getSceneX());
				caption.setTranslateY(e.getSceneY());
				caption.setText(String.valueOf(data.getPieValue()) + "%");
			});
		}

	}

	private void print(final PieChart chart) {
		WritableImage snapshot = chart.snapshot(new SnapshotParameters(), null);

		File file = new File("C:\\Users\\lhleo\\Chart.png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}