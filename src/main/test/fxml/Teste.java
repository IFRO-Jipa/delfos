package fxml;

import java.util.Arrays;

import br.com.delfos.view.ListSelection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Teste extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Button btn = new Button("Filtrar");
		btn.setOnAction(evt -> {
			ListSelection<String> seletor = new ListSelection<>(Arrays.asList("A", "B", "C"));
			seletor.setSelecionados(FXCollections.observableArrayList(Arrays.asList("D", "E", "F")));
			seletor.showAndWait();

		});
		BorderPane pane = new BorderPane(btn);

		stage.setScene(new Scene(pane));
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
