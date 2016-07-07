package br.com.delfos.stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Modals extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button windowModal = new Button("Window Modal");
		windowModal.setOnAction(event -> {
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			stage.setTitle("Window Modal");
			stage.showAndWait();
		});
		Button applicationModal = new Button("Application Modal");
		applicationModal.setOnAction(event -> {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initStyle(StageStyle.UTILITY);
			stage.setTitle("Application Modal");
			stage.centerOnScreen();
			stage.showAndWait();
		});

		VBox pane = new VBox();
		pane.getChildren().addAll(applicationModal, windowModal);

		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("Modals");
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
