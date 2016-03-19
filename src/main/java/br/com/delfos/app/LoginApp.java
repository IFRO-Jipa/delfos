package br.com.delfos.app;

import java.io.IOException;

import org.springframework.stereotype.Service;

import br.com.delfos.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@Service
public class LoginApp extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) throws IOException {

		AnchorPane pane = (AnchorPane) SpringFXMLLoader.load("/fxml/LoginOverview.fxml");

		pane.getStylesheets().add(LoginApp.class.getResource("/css/Login.css").toString());
		stage.setScene(new Scene(pane));
		stage.setTitle("Software Delfos - Autenticação de Usuário");
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(event -> System.exit(0));
		LoginApp.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

}
