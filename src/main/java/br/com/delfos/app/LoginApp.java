package br.com.delfos.app;

import org.springframework.stereotype.Service;

import br.com.delfos.util.LeitorDeFXML;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@Service
public class LoginApp extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		AnchorPane pane = (AnchorPane) LeitorDeFXML.load("fxml/auditoria/LoginOverview.fxml");

		stage.setScene(new Scene(pane));

		stage.setTitle("Delfos - Autenticação de Usuário");
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.setMaximized(false);
		stage.centerOnScreen();

		SplashScreenApp.close();
		stage.show();
		stage.setOnCloseRequest(event -> System.exit(0));
		LoginApp.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

}
