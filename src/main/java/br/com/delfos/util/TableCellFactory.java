package br.com.delfos.util;

import java.util.Optional;
import java.util.function.Function;

import br.com.delfos.view.AlertBuilder;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;

public class TableCellFactory<T> {

	private ListView<T> listView;

	public TableCellFactory(ListView<T> listView) {
		this.listView = listView;
	}

	public Callback<ListView<T>, ListCell<T>> getCellFactory(Function<T, String> predicate) {

		return p -> {
			ListCell<T> cell = configuraTextoNaCelula(predicate);

			ContextMenu menu = getContextMenuToListView(cell);

			cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
				// TODO: criar implementação correta.
				if (isNowEmpty) {
					cell.setContextMenu(null);
				} else {
					cell.setContextMenu(menu);
				}
			});

			return cell;
		};
	}

	private ContextMenu getContextMenuToListView(ListCell<T> cell) {
		MenuItem menuRemoveOnly = new MenuItem();
		menuRemoveOnly.setText("Remover");
		menuRemoveOnly.setOnAction(action -> {
			listView.getItems().remove(cell.getItem());
		});

		MenuItem menuRemoveAll = new MenuItem();
		menuRemoveAll.setText("Remover todos");
		menuRemoveAll.setOnAction(action -> {
			if (AlertBuilder.confirmation("Remover todas as informações selecionadas?")) {
				listView.getItems().remove(cell.getItem());
			}
		});

		ContextMenu menu = new ContextMenu(menuRemoveOnly, menuRemoveAll);
		return menu;
	}

	private ListCell<T> configuraTextoNaCelula(Function<T, String> predicate) {
		ListCell<T> cell = new ListCell<T>() {
			@Override
			protected void updateItem(T p, boolean bln) {
				super.updateItem(p, bln);
				Optional<String> result = Optional.ofNullable(p != null ? predicate.apply(p) : null);
				setText(result.isPresent() ? result.get() : null);
			}
		};
		return cell;
	}
}
