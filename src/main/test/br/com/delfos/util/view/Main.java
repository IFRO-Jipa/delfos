package br.com.delfos.util.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private RadioButton rb3;
	private RadioButton rb2;
	private RadioButton rb1;
	private ToggleGroup tButton;
	private VBox boxItems;

	@Override
	public void start(Stage primaryStage) throws Exception {
		create();
		config();

		primaryStage.setScene(new Scene(new AnchorPane(boxItems)));
		primaryStage.show();
	}

	private void config() {
		boxItems = new VBox(rb1, rb2, rb3);
		boxItems.getChildren().forEach(item -> VBox.setMargin(item, new Insets(0.0, 5.0, 6.0, 5.0)));
	}

	private void create() {
		rb1 = new RadioButton("Primeiro");
		rb2 = new RadioButton("Segundo");
		rb3 = new RadioButton("Terceiro");

		tButton = new ToggleGroup();
		rb1.setToggleGroup(tButton);
		rb2.setToggleGroup(tButton);
		rb3.setToggleGroup(tButton);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
