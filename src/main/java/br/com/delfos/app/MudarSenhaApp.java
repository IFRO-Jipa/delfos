package br.com.delfos.app;

import java.io.IOException;

import br.com.delfos.control.basic.MudarSenhaController;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertAdapter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MudarSenhaApp extends Application {

	private MudarSenhaController controller;
	private boolean requerMudanca = false;
	private Stage stage;

	@Override
	public void start(Stage stage) throws IOException {
		this.stage = stage;
		FXMLLoader loader = LeitorDeFXML.getLoader("fxml/generic/MudarSenhaView.fxml");
		AnchorPane pane = loader.load();
		controller = loader.getController();
		controller.setApplication(this);

		stage.setScene(new Scene(pane));
		stage.setTitle("Redefinir senha");
		stage.setResizable(false);
		stage.setFullScreen(false);
		stage.setMaximized(false);
		if (requerMudanca) {
			Platform.setImplicitExit(true);
			stage.setOnCloseRequest(event -> {
				if (controller.isSenhaModificada()) {
					event.consume();
				} else {
					AlertAdapter.information("Mudança de senha necessária",
							"Por ser a primeira vez que você acessa ao sistema, é necessário que crie uma nova senha.");
				}
			});
		}
		stage.centerOnScreen();
		stage.showAndWait();
	}

	public Stage getStage() {
		return stage;
	}

	public void requerMudanca(boolean flag) {
		this.requerMudanca = flag;

	}

}
