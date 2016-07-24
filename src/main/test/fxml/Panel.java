package fxml;

import java.time.format.DateTimeFormatter;

import br.com.delfos.util.view.MaskFieldUtil;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Panel extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = new BorderPane();
		DatePicker date = new DatePicker();

		MaskFieldUtil.datePickerField(date);

		pane.setCenter(date);

		javafx.scene.control.Button btn = new javafx.scene.control.Button();
		btn.setOnAction(ev -> {
			System.out.println("Valor no editor: " + date.getEditor().getText());
			System.out.println("Valor da data: " + date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		});
		pane.setBottom(btn);
		primaryStage.setScene(new Scene(pane));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
