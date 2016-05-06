package br.com.delfos;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TeApp extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(TeApp.class.getResource("Te.fxml"));
		AnchorPane load = (AnchorPane) loader.load();

		primaryStage.setScene(new Scene(load));
		primaryStage.setTitle("Te");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
