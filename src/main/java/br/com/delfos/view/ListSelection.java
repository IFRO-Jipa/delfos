package br.com.delfos.view;

import java.util.List;
import java.util.function.Function;

import org.controlsfx.control.ListSelectionView;

import br.com.delfos.util.TableCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ListSelection<T> extends Dialog<List<T>> {

	private ListSelectionView<T> view;

	/**
	 * 
	 * @param data
	 *            registros disponíveis para seleção
	 */
	public ListSelection(List<T> data) {
		this("Seletor de " + data.get(0).getClass().getSimpleName() + "s", data);
	}

	public ListSelection(String title) {
		this.initConfig(title, null);
	}

	public final void setDisponiveis(ObservableList<T> value) {
		view.setSourceItems(value);
	}

	public final void setSelecionados(ObservableList<T> value) {
		view.setTargetItems(value);
	}

	public ListSelection(String title, List<T> data) {
		initConfig(title, data);
	}

	private void initConfig(String title, List<T> data) {
		setTitle("Seletor de informações");
		setHeaderText(title);
		initLayout(data);
	}

	private void initLayout(List<T> data) {
		getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

		this.getDialogPane().setContent(getPane(data));

		this.setResultConverter(button -> {
			if (button == ButtonType.OK) {
				if (!this.view.getTargetItems().isEmpty()) {
					return this.view.getTargetItems();
				}
			}
			return null;
		});

		this.setResizable(true);
	}

	public final void textFormat(Function<T, String> predicate) {
		view.setCellFactory(new TableCellFactory<T>().getCellFactory(predicate));
	}

	private Pane getPane(List<T> data) {
		view = new ListSelectionView<>();
		view.applyCss();
		this.setDisponiveis(FXCollections.observableArrayList(data));

		return new BorderPane(view);
	}

}
