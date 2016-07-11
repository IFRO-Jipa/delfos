package br.com.delfos.fxml;

import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SpreadsheetViewTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private SpreadsheetView spreadsheet;

	@Override
	public void start(Stage primaryStage) throws Exception {
		spreadsheet = getSpreadsheet();
		spreadsheet.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				System.out.println(spreadsheet.getSelectionModel().getSelectedCells());
			}
		});
		BorderPane pane = new BorderPane(spreadsheet);
		primaryStage.setScene(new Scene(pane, 500, 300));
		primaryStage.setTitle("Example to use a spreadsheet");
		primaryStage.show();
	}

	private SpreadsheetView getSpreadsheet() {
		int rowCount = 15;
		int columnCount = 10;
		GridBase grid = new GridBase(rowCount, columnCount);

		ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();
		for (int row = 0; row < grid.getRowCount(); ++row) {
			final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
			for (int column = 0; column < grid.getColumnCount(); ++column) {
				list.add(SpreadsheetCellType.STRING.createCell(row, column, 1, 1, "value"));
			}
			rows.add(list);
		}
		grid.setRows(rows);

		return new SpreadsheetView(grid);
	}

}
