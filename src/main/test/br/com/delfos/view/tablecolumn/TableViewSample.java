package br.com.delfos.view.tablecolumn;

import java.util.Map;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class TableViewSample extends Application {

	@Override
	public void start(Stage primaryStage) {
		final ObservableMap<String, Number> obsMap = FXCollections.observableHashMap();
		for (int i = 0; i < 3; i++)
			obsMap.put("key " + i, i * 10d);

		final TableView<ObservableMap.Entry<String, Number>> tv = new TableView<>(
		        FXCollections.observableArrayList(obsMap.entrySet()));
		tv.setEditable(true);

		obsMap.addListener((MapChangeListener.Change<? extends String, ? extends Number> change) -> {
			tv.setItems(FXCollections.observableArrayList(obsMap.entrySet()));
		});

		TableColumn<ObservableMap.Entry<String, Number>, String> keyCol = new TableColumn<>("key");
		TableColumn<ObservableMap.Entry<String, Number>, Number> priceCol = new TableColumn<>("price");
		tv.getColumns().addAll(keyCol, priceCol);

		keyCol.setCellValueFactory((p) -> {
			return new SimpleStringProperty(p.getValue().getKey());
		});

		keyCol.setCellFactory(TextFieldTableCell.forTableColumn());
		keyCol.setOnEditCommit((TableColumn.CellEditEvent<Map.Entry<String, Number>, String> t) -> {
			final String oldKey = t.getOldValue();
			final Number oldPrice = obsMap.get(oldKey);
			obsMap.remove(oldKey);
			obsMap.put(t.getNewValue(), oldPrice);
		});

		priceCol.setCellValueFactory((p) -> {
			return new ReadOnlyObjectWrapper<>(p.getValue().getValue());
		});

		priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		priceCol.setOnEditCommit((TableColumn.CellEditEvent<Map.Entry<String, Number>, Number> t) -> {
			obsMap.put(t.getTableView().getItems().get(t.getTablePosition().getRow()).getKey(),// key
		            t.getNewValue());// val);
		});

		Button btn1 = new Button();
		btn1.setText("Add data");
		btn1.setOnAction((ActionEvent event) -> {
			obsMap.put("hi", 100);
		});

		Button btn2 = new Button();
		btn2.setText("verify data");
		btn2.setOnAction((ActionEvent event) -> {
			for (Map.Entry<String, Number> me : obsMap.entrySet())
				System.out.println("key " + me.getKey() + " val " + me.getValue());
		});

		VBox root = new VBox(tv, btn1, btn2);
		Scene scene = new Scene(root, 300, 250);

		primaryStage.setTitle("Map Table test");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}