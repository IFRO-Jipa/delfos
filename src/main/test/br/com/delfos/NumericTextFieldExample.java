package br.com.delfos;

import br.com.delfos.util.Regex;
import br.com.delfos.util.view.TextFieldFormatter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class NumericTextFieldExample extends Application {

	@Override
	public void start(Stage primaryStage) {
		TextField textField = new TextField();

		TextFormatter<Integer> form = TextFieldFormatter.getFormatter(new IntegerStringConverter(), 0,
		        Regex.APENAS_NUMEROS);

		form.valueProperty().addListener((obs, oldValue, newValue) -> {
			System.out.println("New double value " + newValue);
		});

		textField.setTextFormatter(form);

		StackPane root = new StackPane(textField);
		root.setPadding(new Insets(24));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}