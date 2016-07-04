package br.com.delfos;

import java.io.IOException;

import br.com.delfos.app.LoginApp;
import javafx.stage.Stage;

public class Main {
	public static void main(String[] args) {
		try {
			new LoginApp().start(new Stage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
