package br.com.delfos.app;

import java.io.IOException;
import java.util.Optional;

import br.com.delfos.control.basic.PrincipalController;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrincipalApp extends Application {

	private static Stage stage;

	private static Optional<PrincipalController> controller;

	private static final String LOCATION = "/fxml/basic/PrincipalView.fxml";

	@Override
	public void start(Stage stage) throws IOException {

		FXMLLoader loader = LeitorDeFXML.getLoader(LOCATION);
		AnchorPane pane = (AnchorPane) loader.load();

		stage.setScene(new Scene(pane));
		stage.setTitle("Software Delfos - Menu Principal");
		stage.setMaximized(true);
		stage.setOnCloseRequest(event -> System.exit(0));
		stage.show();

		PrincipalApp.stage = stage;
		controller = Optional.ofNullable(loader.getController());
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Stage getStage() {
		return stage;
	}

	public static void destroy() {
		if (AlertBuilder.confirmation(AlertBuilder.ALERT_CONFIRM_EXIT)) {
			System.exit(0);
		}
	}

	public static Optional<PrincipalController> getController() throws IOException {
		return controller;
	}

	public static void openWindow(AnchorPane pane, String title) {
		try {
			PrincipalController controller = PrincipalApp.getController().orElseThrow(
					() -> new IllegalArgumentException("PrincipalController não foi instanciado adequadamente."));

			controller.openWindow(pane, title);
		} catch (IllegalArgumentException e) {
			AlertBuilder.error(e);
		} catch (IOException e) {
			AlertBuilder.error(e);
		}
	}

}