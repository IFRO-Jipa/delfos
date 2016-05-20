package br.com.delfos.util;

import br.com.delfos.view.AlertBuilder;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.util.Callback;

public class TableCellFactory<T, S> {

	private ListView<T> listView;

	private Callback<ListView<S>, ListCell<S>> cellFactory() {

		return p -> {
			ListCell<S> cell = configuraTextoNaCelula();

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

	private ContextMenu getContextMenuToListView(ListCell<S> cell) {
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

	private ListCell<S> configuraTextoNaCelula() {
		ListCell<S> cell = new ListCell<S>() {
			@Override
			protected void updateItem(final S p, final boolean bln) {
				super.updateItem(p, bln);
				if (p != null) {
					setText(p.toString());
				} else {
					setText(null);
				}
			}

		};
		return cell;
	}
}
