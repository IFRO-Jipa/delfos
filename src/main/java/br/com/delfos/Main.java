package br.com.delfos;

import br.com.delfos.app.LoginApp;
import br.com.delfos.app.SplashScreenApp;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new SplashScreenApp().start(primaryStage);
		
		Runnable run = () -> {
			try {
				new LoginApp().start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

		// run.run();

	}

	private void handleAfterLoading(WindowEvent evt) {
		System.out.println("Terminou de carregar o Spring e conectou com o banco de dados.");
		SplashScreenApp.close();
	}

	public static void main(String[] args) {
		Main.launch(args);
	}
}
