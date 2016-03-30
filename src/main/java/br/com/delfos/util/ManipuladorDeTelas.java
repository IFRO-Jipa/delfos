package br.com.delfos.util;

import java.util.Optional;

import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ManipuladorDeTelas {
	public static void limpaCampos(Pane pane) {
		Optional<Object[]> elements = Optional.ofNullable(pane.getChildren().toArray());

		limpaCampos(elements);
	}

	private static void limpaCampos(Optional<Object[]> elements) {
		for (Object element : elements.get()) {
			if (element instanceof TextField) {
				((TextField) element).setText("");
			} else if (element instanceof TextArea) {
				((TextArea) element).setText("");
			} else if (element instanceof CheckBox) {
				((CheckBox) element).setSelected(false);
			} else if (element instanceof DatePicker) {
				((DatePicker) element).setValue(null);
			} else if (element instanceof ComboBox<?>) {
				// TODO: LIMPAR COMBOBOX
			} else if (element instanceof Group) {
				limpaCampos((Group) element);
			} else if (element instanceof AnchorPane) {
				limpaCampos((Pane) element);
			} else if (element instanceof TabPane) {
				limpaCampos((TabPane) element);
			} else if (element instanceof Pane) {
				limpaCampos((Pane) element);
			} else if (element instanceof ComboBox<?>) {
				limpaCampos((ComboBox<?>) element);
			}
		}
	}

	private static void limpaCampos(ComboBox<?> element) {
		element.getSelectionModel().select(null);
	}

	private static void limpaCampos(Group group) {
		Optional<Object[]> optional = Optional.ofNullable(group.getChildren().toArray());
		limpaCampos(optional);
	}

	private static void limpaCampos(TabPane tabPane) {
		tabPane.getTabs().forEach(tab -> {
			Optional<AnchorPane> value = Optional.ofNullable(
		            tab.getContent() instanceof AnchorPane ? (AnchorPane) tab.getContent() : null);

			if (value.isPresent()) {
				Optional<Object[]> elements = Optional.of(value.get().getChildren().toArray());
				limpaCampos(elements);
			}
		});
	}

}
