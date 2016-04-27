package br.com.delfos.view;

import java.util.List;

import org.controlsfx.control.ListSelectionView;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class ListSelection<T> extends Dialog<List<T>> {

	private ListSelectionView<T> view;

	public ListSelection(List<T> options) {
		this("Seletor de " + options.get(0).getClass().getSimpleName() + "s", options);
	}

	public ListSelection(String title, List<T> options) {
		initConfig(title, options);
	}

	private void initConfig(String title, List<T> options) {
		setTitle("Seletor de informações");
		setHeaderText(title);
		initLayout(options);
	}

	private void initLayout(List<T> options) {
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.getDialogPane().setContent(getPane(options));

		this.setResultConverter(button -> {
			if (button == ButtonType.OK) {
				if (!this.view.getTargetItems().isEmpty()) {
					return this.view.getTargetItems();
				}
			}
			return null;
		});

	}
	
	

	public final void setCellFactory(Callback<ListView<T>, ListCell<T>> value) {
		view.setCellFactory(value);
	}

	private AnchorPane getPane(List<T> options) {
		AnchorPane pane = new AnchorPane();
		view = new ListSelectionView<>();
		view.getSourceItems().addAll(options);
		pane.getChildren().add(view);
		return pane;
	}

}
