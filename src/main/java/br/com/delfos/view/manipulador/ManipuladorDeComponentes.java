package br.com.delfos.view.manipulador;

import java.lang.reflect.Field;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ManipuladorDeComponentes {

	private static final String INVALID_FIELD_MESSAGE = "O campo %s é de preenchimento obrigatório.";

	public static boolean validaCampos(Object element) {
		boolean valid = true;

		try {
			Class<?> clazz = element.getClass();

			for (Field field : clazz.getDeclaredFields()) {
				if (!valid)
					break;
				if (field.isAnnotationPresent(NotNull.class)) {
					field.setAccessible(true);
					Object obj = field.get(element);
					valid = validaComponent((Node) obj);

				}
			}

		} catch (SecurityException e) {
			System.out.println(e);

		} catch (IllegalArgumentException | IllegalAccessException e) {
			System.out.println(e);
		}
		return valid;
	}

	private static boolean validaComponent(Node obj) {
		if (obj instanceof TextField) {
			return valida((TextField) obj);
		} else if (obj instanceof CheckBox) {
			return valida((CheckBox) obj);
		} else if (obj instanceof ComboBox<?>) {
			return valida((ComboBox<?>) obj);
		} else if (obj instanceof TextArea) {
			return valida((TextArea) obj);
		}

		return false;
	}

	public static boolean valida(CheckBox obj) {
		if (!obj.isSelected())
			lancaException(obj);
		return true;
	}

	private static void lancaException(Control obj) {
		obj.requestFocus();
		throw new ValidationException(getMessage(obj));
	}

	public static boolean valida(ComboBox<?> obj) {
		if (obj.getSelectionModel().getSelectedItem() == null)
			lancaException(obj);
		return true;
	}

	public static boolean valida(TextArea obj) throws ValidationException {
		if (obj.getText().isEmpty())
			lancaException(obj);
		return true;
	}

	public static boolean valida(TextField obj) {
		if (obj.getText().isEmpty())
			lancaException(obj);
		return true;
	}

	private static String getMessage(Control name) {
		return String.format(INVALID_FIELD_MESSAGE,
		        name.getTooltip() != null ? name.getTooltip().getText() : name.getId());
	}

}
