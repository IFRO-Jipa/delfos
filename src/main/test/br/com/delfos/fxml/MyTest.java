package br.com.delfos.fxml;

import br.com.delfos.util.LeitorDeFXML;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MyTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = LeitorDeFXML.load("br/com/delfos/fxml/MyApp.fxml");

		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("My Title");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
