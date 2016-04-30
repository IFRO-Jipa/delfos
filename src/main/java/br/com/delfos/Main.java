package br.com.delfos;

import br.com.delfos.app.LoginApp;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	//teste

	@Override
	public void start(Stage primaryStage) throws Exception {
		new LoginApp().start(primaryStage);
	}

	public static void main(String[] args) {
		Main.launch(args);
	}
}
