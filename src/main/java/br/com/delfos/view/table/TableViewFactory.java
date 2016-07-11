package br.com.delfos.view.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewFactory<Type> {

	private ChangeListener<? super Type> listener;

	public TableView<Type> criaTableView(List<Type> itens) {
		Class<? extends Object> type = itens.get(0).getClass();
		List<TableColumn<Type, ?>> columns = getColumns(type);

		TableView<Type> tabela = new TableView<>();
		columns.forEach(column -> tabela.getColumns().add(column));
		tabela.setItems(FXCollections.observableArrayList(itens));

		tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabela.getSelectionModel().selectedItemProperty().addListener(listener);
		
		return tabela;
	}
	
	public void setListenerSelectedItem(ChangeListener<? super Type> listener) {
		this.listener = listener;
	}

	private List<TableColumn<Type, ?>> configuraOrdem(List<TableColumn<Type, ?>> columns) {
		List<TableColumn<Type, ?>> result = new ArrayList<>();
		columns.forEach(column -> {
			if (column.getText().equals("ID")) {
				result.add(column);
			}
		});

		columns.remove(result.get(0));
		columns.forEach(column -> result.add(column));

		return result;
	}

	private List<TableColumn<Type, ?>> getColumns(Class<? extends Object> clazz) {
		List<TableColumn<Type, ?>> columns = new ArrayList<>();

		do {
			columns.addAll(getColumns(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		} while (clazz != Object.class);

		return configuraOrdem(columns);
	}

	private List<TableColumn<Type, ?>> getColumns(Field[] fields) {
		List<TableColumn<Type, ?>> columns = new ArrayList<>();
		for (Field f : fields) {
			if (f.isAnnotationPresent(br.com.delfos.view.table.TableColumnConfig.class)) {

				br.com.delfos.view.table.TableColumnConfig annotation = f
						.getAnnotation(br.com.delfos.view.table.TableColumnConfig.class);
				TableColumn<Type, ?> tableColumn = criaColuna(f.getType(), annotation.alias(), annotation.name());

				columns.add(tableColumn);
			}
		}
		return columns;
	}

	private TableColumn<Type, ?> criaColuna(Class<?> type, String alias, String name) {
		TableColumn<Type, ?> column;
		if (type == String.class) {
			column = new TableColumn<Type, String>(alias);
		} else if (type == Long.class) {
			column = new TableColumn<Type, Long>(alias);
		} else if (type == Integer.class) {
			column = new TableColumn<Type, Integer>(alias);
		} else if (type == Boolean.class) {
			column = new TableColumn<Type, Object>(alias);
		} else if (type == Double.class) {
			column = new TableColumn<Type, Object>(alias);
		} else
			column = new TableColumn<Type, Object>(alias);

		configuraColuna(column, name);

		return column;
	}

	private void configuraColuna(TableColumn<Type, ?> column, String name) {
		column.setCellValueFactory(new PropertyValueFactory<>(name));
	}
}
