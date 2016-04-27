package br.com.delfos.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.controlsfx.control.ListSelectionView;

import javafx.application.Application;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TestDialog extends Application {

	private ListSelectionView<String> selector;

	@Override
	public void start(Stage primaryStage) {
		ListSelection<String> list = new ListSelection<>(Arrays.asList("A", "B", "C"));
		Optional<List<String>> result = list.showAndWait();

		result.ifPresent(value -> {
			value.forEach(System.out::println);
		});
	}

	public void teste() {
		Dialog<List<String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Look, a Custom Login Dialog");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		AnchorPane pane = new AnchorPane();
		selector = new ListSelectionView<>();
		selector.getSourceItems().addAll("A", "B", "C", "D");
		pane.getChildren().add(selector);

		dialog.getDialogPane().setContent(pane);

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(teste(loginButtonType));

		Optional<List<String>> result = dialog.showAndWait();

		result.ifPresent(options -> {
			// TODO: fazer
			options.forEach(System.out::println);
		});

	}

	private Callback<ButtonType, List<String>> teste(ButtonType loginButtonType) {
		return dialogButton -> {
			if (dialogButton == loginButtonType) {
				ArrayList<String> result = new ArrayList<>();
				result.addAll(selector.getTargetItems());
				return result;
			}
			return null;
		};
	}

	public static void main(String[] args) {
		launch(args);
	}
}
