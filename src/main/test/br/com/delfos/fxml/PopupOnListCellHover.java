package br.com.delfos.fxml;

import java.util.stream.IntStream;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PopupOnListCellHover extends Application {

	private Popup popup;
	private Node popupContent;
	private Label titleLabel;
	private Label detailsLabel;
	private FadeTransition fadeOut;

	@Override
	public void start(Stage primaryStage) {
		ListView<Item> listView = new ListView<>();

		popup = new Popup();
		titleLabel = new Label();
		titleLabel.setStyle("-fx-font-size: 1.5em ; -fx-font-weight: bold;");
		detailsLabel = new Label();
		popupContent = new VBox(10, titleLabel, detailsLabel);
		popupContent
				.setStyle("-fx-background-color: -fx-background; " + "-fx-background: lightskyblue; -fx-padding:12px;");
		popup.getContent().add(popupContent);

		fadeOut = new FadeTransition(Duration.millis(500), popupContent);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> popup.hide());

		listView.setCellFactory(lv -> {
			ListCell<Item> cell = new ListCell<Item>() {
				@Override
				public void updateItem(Item item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(item.getName());
					}
				}
			};
			cell.setOnMouseEntered(e -> showPopup(cell));
			cell.setOnMouseExited(e -> hidePopup());

			return cell;
		});

		IntStream.rangeClosed(1, 100).mapToObj(i -> new Item("Item " + i, i)).forEach(listView.getItems()::add);

		BorderPane root = new BorderPane(listView);
		Scene scene = new Scene(root, 250, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void showPopup(ListCell<Item> cell) {
		fadeOut.stop();
		popupContent.setOpacity(1.0);
		Bounds bounds = cell.localToScreen(cell.getBoundsInLocal());
		popup.show(cell, bounds.getMaxX(), bounds.getMinY());
		Item item = cell.getItem();
		titleLabel.setText(item.getName());
		detailsLabel.setText(String.format("This isa aaaaaaa %s.%nIt has value %d.", item.getName(), item.getValue()));
	}

	private void hidePopup() {
		fadeOut.playFromStart();
	}

	public static class Item {
		private final int value;
		private final String name;

		public Item(String name, int value) {
			this.name = name;
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}