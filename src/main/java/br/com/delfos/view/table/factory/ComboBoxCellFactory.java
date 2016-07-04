package br.com.delfos.view.table.factory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

public class ComboBoxCellFactory<T, S> extends TableCell<T, S> {

	private ComboBox<S> comboBox;
	private StringConverter<S> converter;
	private ObservableList<S> data;

	public ComboBoxCellFactory(ObservableList<S> data) {
		this.data = data;
	}

	public ComboBoxCellFactory(ObservableList<S> data, StringConverter<S> converter) {
		super();
		this.converter = converter;
		this.data = data;
	}

	public ComboBoxCellFactory() {
		data = FXCollections.emptyObservableList();
		createSimpleConverter();
	}

	private void createSimpleConverter() {
		if (converter == null) {
			converter = new StringConverter<S>() {

				@Override
				public S fromString(String string) {
					return null;
				}

				@Override
				public String toString(S object) {
					return object.toString();
				}
			};
		}
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			createComboBox(this.data, this.converter);
			setText(null);
			setGraphic(comboBox);
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		setText(converter.toString(getValue()));
		setGraphic(null);
	}

	@Override
	public void updateItem(S item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (comboBox != null) {
					comboBox.setValue(getValue());
				}
				setText(converter.toString(getValue()));
				setGraphic(comboBox);
			} else {
				setText(converter.toString(getValue()));
				setGraphic(null);
			}
		}
	}

	private void createComboBox(ObservableList<S> values, StringConverter<S> converter) {
		comboBox = new ComboBox<>(values);
		comboBox.setConverter(converter);
		comboBox.valueProperty().set(getValue());
		comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
		comboBox.setOnAction((e) -> {
			System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
			commitEdit(comboBox.getSelectionModel().getSelectedItem());
		});
	}

	private S getValue() {
		return getItem() == null ? null : getItem();
	}
}