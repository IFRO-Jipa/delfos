package fxml;

import br.com.delfos.util.view.MaskFieldUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Panel extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		TextField date = new TextField();

		MaskFieldUtil.decimalField(date);

		pane.setCenter(date);

		javafx.scene.control.Button btn = new javafx.scene.control.Button();
		pane.setBottom(btn);
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
