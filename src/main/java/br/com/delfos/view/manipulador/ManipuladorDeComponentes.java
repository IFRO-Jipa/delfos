package br.com.delfos.view.manipulador;

import java.lang.reflect.Field;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ManipuladorDeComponentes {

	public static boolean validaCampos(Object element) {
		boolean valid = true;

		try {
			Class<?> clazz = element.getClass();

			for (Field field : clazz.getDeclaredFields()) {
				if (valid)
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

		return true;
	}

	public static boolean valida(CheckBox obj) {
		if (!obj.isSelected())

			throw new ValidationException("O campo " + obj.getId() + " é de preenchimento obrigatório");
		return false;
	}

	public static boolean valida(ComboBox<?> obj) {
		if (obj.getSelectionModel().getSelectedItem() != null)
			throw new ValidationException("O campo " + obj.getId() + " é de preenchimento obrigatório");
		return false;
	}

	public static boolean valida(TextArea obj) {
		if (!obj.getText().isEmpty()) {
			throw new ValidationException("O campo " + obj.getId() + " é de preenchimento obrigatório");
		}
		return false;
	}	

	public static boolean valida(TextField obj) {
		return !obj.getText().isEmpty();
	}

}
