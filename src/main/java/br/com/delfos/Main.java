package br.com.delfos;

import br.com.delfos.app.LoginApp;
import br.com.delfos.view.AlertBuilder;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// Iniciar a tela de splash
			primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWING, evt -> {
				handleAfterLoading(evt);
			});
			new LoginApp().start(primaryStage);
		} catch (Exception ex) {
			AlertBuilder.error(ex);
		}

	}

	private void handleAfterLoading(WindowEvent evt) {
		System.out.println("Terminou de carregar o Spring e conectou com o banco de dados.");
	}

	public static void main(String[] args) {
		Main.launch(args);
	}
}
