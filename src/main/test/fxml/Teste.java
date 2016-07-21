package fxml;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Teste extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		StackPane root = new StackPane();
		root.setStyle("-fx-background-image: url(/imgs/chart.png);");
		Scene scene = new Scene(root, 300, 250);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
