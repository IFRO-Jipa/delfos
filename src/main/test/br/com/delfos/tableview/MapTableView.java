package br.com.delfos.tableview;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MapTableView extends Application {

	@Override
	public void start(Stage stage) {

		// sample data
		ObservableMap<String, Double> map = FXCollections.observableHashMap();
		map.put("one", 3.0);
		map.put("two", 0.4);
		map.put("three", 0.7);

		// use fully detailed type for Map.Entry<String, String>
		TableColumn<ObservableMap.Entry<String, Double>, String> column1 = new TableColumn<>("Key");
		column1.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));

		TableColumn<ObservableMap.Entry<String, Double>, Double> column2 = new TableColumn<>("Value");
		column2.setCellValueFactory(p -> new SimpleObjectProperty<Double>(p.getValue().getValue()));

		ObservableList<ObservableMap.Entry<String, Double>> items = FXCollections.observableArrayList(map.entrySet());
		final TableView<ObservableMap.Entry<String, Double>> table = new TableView<>(items);

		table.getColumns().addAll(column1, column2);

		Scene scene = new Scene(table, 400, 400);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}