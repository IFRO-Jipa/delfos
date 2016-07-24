package br.com.delfos.app;

import java.io.IOException;
import java.util.Optional;

import br.com.delfos.control.auditoria.Autenticador;
import br.com.delfos.control.basic.PrincipalController;
import br.com.delfos.util.LeitorDeFXML;
import br.com.delfos.view.AlertAdapter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalApp extends Application {

	private static Stage stage;

	private static Optional<PrincipalController> controller;

	private static final String LOCATION = "fxml/basic/PrincipalView.fxml";

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

	public static Stage getStage() {
		return stage;
	}

	public static void logout() throws IOException {
		stage.hide();
		Autenticador.logout();
		new LoginApp().start(stage);
	}

	public static Optional<PrincipalController> getController() throws IOException {
		return controller;
	}

	public static void openWindow(Pane pane, String title, String icon) {
		try {
			PrincipalController controller = PrincipalApp.getController().orElseThrow(
					() -> new IllegalArgumentException("PrincipalController não foi instanciado adequadamente."));

			controller.openWindow(pane, title, icon);
		} catch (IllegalArgumentException e) {
			AlertAdapter.unknownError(e);
		} catch (IOException e) {
			AlertAdapter.erroLoadFXML(e);
		}
	}

}