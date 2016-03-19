package br.com.delfos.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginApp extends Application {

	@SuppressWarnings("unused")
	private static Stage stage;

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LoginApp.class.getResource("/fxml/LoginOverview.fxml"));

		AnchorPane pane = (AnchorPane) loader.load();
		stage.setScene(new Scene(pane));
		stage.setTitle("Software Delfos - Autenticação de Usuário");
		stage.setResizable(false);
		stage.show();
		LoginApp.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
