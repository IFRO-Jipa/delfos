package fxml;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.ToggleSwitch;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MaskerPaneExample extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();

		MaskerPane mask = new MaskerPane();
		mask.setText("Analisando...");
		ToggleSwitch switched = new ToggleSwitch();
		switched.selectedProperty().addListener((obs, oldV, newV) -> {
			mask.setVisible(newV);
		});

		pane.setCenter(mask);
		pane.setTop(switched);

		primaryStage.setScene(new Scene(pane));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
