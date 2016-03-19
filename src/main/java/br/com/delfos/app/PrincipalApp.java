package br.com.delfos.app;

import java.io.IOException;

import br.com.delfos.model.Usuario;
import br.com.delfos.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalApp extends Application {

	private static Stage stage;

	private static Usuario usuario;

	@Override
	public void start(Stage stage) throws IOException {

		BorderPane pane = (BorderPane) SpringFXMLLoader.load("/fxml/PrincipalView.fxml");

		stage.setScene(new Scene(pane));
		stage.setTitle("Software Delfos - Menu Principal");
		stage.setMaximized(true);
		stage.setOnCloseRequest(e -> System.exit(0));
		stage.show();

		PrincipalApp.stage = stage;
	}

	public static Usuario getUsuario() {
		return usuario;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

}