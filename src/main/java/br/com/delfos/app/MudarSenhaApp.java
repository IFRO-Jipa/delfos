package br.com.delfos.app;

import br.com.delfos.util.LeitorDeFXML;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MudarSenhaApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		AnchorPane pane = (AnchorPane) LeitorDeFXML.load("fxml/generic/MudarSenhaView.fxml");

		stage.setScene(new Scene(pane));
		stage.setTitle("Redefinir senha");
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.setMaximized(false);
		stage.centerOnScreen();
		stage.showAndWait();
	}

}
