package br.com.delfos.app;

import java.io.IOException;

import br.com.delfos.model.Usuario;
import br.com.delfos.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrincipalApp extends Application {

	private static Stage stage;

	private static Usuario usuario;

	public PrincipalApp(Usuario usuario) {
		super();
		PrincipalApp.usuario = usuario;
	}

	@Override
	public void start(Stage stage) throws IOException {

		AnchorPane pane = (AnchorPane) SpringFXMLLoader.load("/fxml/PrincipalView.fxml");

		stage.setScene(new Scene(pane));
		stage.setTitle("Software Delfos - Menu Principal");
		stage.setMaximized(true);
		stage.setOnCloseRequest(e -> System.exit(0));
		stage.show();

		System.out.println("Usuario " + usuario.getLogin() + " está autenticado.");

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