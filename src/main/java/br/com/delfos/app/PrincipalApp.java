package br.com.delfos.app;

import java.io.IOException;

import br.com.delfos.util.AlertBuilder;
import br.com.delfos.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrincipalApp extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) throws IOException {

		AnchorPane pane = (AnchorPane) SpringFXMLLoader.load("/fxml/PrincipalView.fxml");

		stage.setScene(new Scene(pane));
		stage.setTitle("Software Delfos - Menu Principal");
		stage.setMaximized(true);
		stage.setOnCloseRequest(event -> System.exit(0));
		stage.show();

		PrincipalApp.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

	public static void destroy() {
		if (AlertBuilder.confirmation("Deseja realmente sair?")) {
			System.exit(0);
		}
	}

}