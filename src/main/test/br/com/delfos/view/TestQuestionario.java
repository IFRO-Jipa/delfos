package br.com.delfos.view;

import br.com.delfos.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestQuestionario extends Application {
	
	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		
		AnchorPane pane = (AnchorPane) SpringFXMLLoader.load("/fxml/QuestionarioView.fxml");

		stage.setScene(new Scene(pane));
		stage.setTitle("Teste Questionario");
		stage.setMaximized(true);
		stage.setOnCloseRequest(event -> System.exit(0));
		stage.show();

		TestQuestionario.stage = stage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}
	

}
