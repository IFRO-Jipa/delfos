package br.com.delfos.view;

import java.util.List;
import java.util.function.Function;

import org.controlsfx.control.ListSelectionView;

import br.com.delfos.util.TableCellFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;

public class ListSelection<T> extends Dialog<List<T>> {

	private ListSelectionView<T> view;

	public ListSelection(List<T> options) {
		this("Seletor de " + options.get(0).getClass().getSimpleName() + "s", options);
	}

	public final void setSourceItems(ObservableList<T> value) {
		view.setSourceItems(value);
	}

	public final void setTargetItems(ObservableList<T> value) {
		view.setTargetItems(value);
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

	public final void textFormat(Function<T, String> predicate) {
		view.setCellFactory(new TableCellFactory<T>().getCellFactory(predicate));
	}

	private AnchorPane getPane(List<T> options) {
		AnchorPane pane = new AnchorPane();
		view = new ListSelectionView<>();
		view.getTargetItems().addAll(options);
		pane.getChildren().add(view);
		return pane;
	}

}
