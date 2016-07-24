package br.com.delfos.util.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.delfos.converter.date.DatePickerConverter;
import br.com.delfos.util.Regex;
import br.com.delfos.view.AlertAdapter;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;

public abstract class MaskFieldUtil {

	private static List<KeyCode> ignoreKeyCodes;

	static {
		ignoreKeyCodes = new ArrayList<>();
		Collections.addAll(ignoreKeyCodes, new KeyCode[] { KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, KeyCode.F5,
				KeyCode.F6, KeyCode.F7, KeyCode.F8, KeyCode.F9, KeyCode.F10, KeyCode.F11, KeyCode.F12 });
	}

	public static void ignoreKeys(final TextField textField) {
		textField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
			if (ignoreKeyCodes.contains(keyEvent.getCode())) {
				keyEvent.consume();
			}
		});
	}

	public static void datePickerField(final DatePicker date) {
		final String pattern = "dd/MM/yyyy";

		date.setConverter(new DatePickerConverter());
		MaskFieldUtil.dateField(date.getEditor());

		date.getEditor().focusedProperty()
				.addListener((ObservableValue<? extends Boolean> obs, Boolean oldV, Boolean newV) -> {
					if (!newV) {
						try {
							if (date.getEditor().getText().length() == pattern.length()) {
								date.setValue(LocalDate.parse(date.getEditor().getText(),
										DateTimeFormatter.ofPattern(pattern)));
							}
						} catch (DateTimeParseException e) {
							AlertAdapter.error("Data inválida",
									"A data informada não é válida ou não está em um padrão válido.");
						}
					}
				});

	}

	/**
	 * Monta a mascara para Data (dd/MM/yyyy).
	 *
	 * @param textField
	 *            TextField
	 */
	public static void dateField(final TextField textField) {
		maxField(textField, 10);

		textField.lengthProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
			if (newValue.intValue() < 11) {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
				value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
				textField.setText(value);
				positionCaret(textField);
			}
		});
	}

	public static void cpfField(final TextField textField) {

		maxField(textField, 14);

		textField.lengthProperty().addListener((ChangeListener<Number>) (obs, oldValue, newValue) -> {
			if (newValue.intValue() < 15) {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3}).(\\d{3})(\\d)", "$1.$2.$3");
				value = value.replaceFirst("(\\d{3}).(\\d{3}).(\\d{3})(\\d)", "$1.$2.$3-$4");
				textField.setText(value);
				positionCaret(textField);
			}
		});
	}

	public static void cepField(final TextField textField) {
		maxField(textField, 10);

		textField.lengthProperty().addListener((ChangeListener<Number>) (obs, oldValue, newValue) -> {
			if (newValue.intValue() < 11) {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{2}).(\\d{3})(\\d)", "$1.$2-$3");
				value = value.replaceFirst("(\\d{2}).(\\d{3})-(\\d)", "$1.$2-$3");
				textField.setText(value);
				positionCaret(textField);
			}
		});
	}

	/**
	 * Campo que aceita somente numericos.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void numericField(final TextField textField) {
		textField.lengthProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
			if (newValue.intValue() > oldValue.intValue()) {
				char ch = textField.getText().charAt(oldValue.intValue());
				if (!(ch >= '0' && ch <= '9')) {
					textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
				}
			}
		});
	}

	public static void decimalField(final TextField textField) {
		textField.setTextFormatter(TextFieldFormatter.getFormatter(new DoubleStringConverter(), 0.0, Regex.DECIMAL));
	}

	public static void numericFields(final List<TextField> fields) {
		fields.forEach(MaskFieldUtil::numericField);
	}

	public static void numericFields(final TextField... fields) {
		numericFields(Arrays.asList(fields));
	}

	/**
	 * Monta a mascara para Moeda.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void monetaryField(final TextField textField) {
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener((ChangeListener<Number>) (observable, oldValue, newValue) -> {
			String value = textField.getText();
			value = value.replaceAll("[^0-9]", "");
			value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
			textField.setText(value);
			positionCaret(textField);

			textField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observableValue, String oldValue,
						String newValue) {
					if (newValue.length() > 17)
						textField.setText(oldValue);
				}
			});
		});

		textField.focusedProperty().addListener((ChangeListener<Boolean>) (observableValue, aBoolean, fieldChange) -> {
			if (!fieldChange) {
				final int length = textField.getText().length();
				if (length > 0 && length < 3) {
					textField.setText(textField.getText() + "00");
				}
			}
		});
	}

	/**
	 * Monta as mascara para CPF/CNPJ. A mascara eh exibida somente apos o campo
	 * perder o foco.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void cpfCnpjField(final TextField textField) {

		textField.focusedProperty().addListener((ChangeListener<Boolean>) (observableValue, aBoolean, fieldChange) -> {
			String value = textField.getText();
			if (!fieldChange) {
				if (textField.getText().length() == 11) {
					value = value.replaceAll("[^0-9]", "");
					value = value.replaceFirst("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4");
				}
				if (textField.getText().length() == 14) {
					value = value.replaceAll("[^0-9]", "");
					value = value.replaceFirst("([0-9]{2})([0-9]{3})([0-9]{3})([0-9]{4})([0-9]{2})$", "$1.$2.$3/$4-$5");
				}
			}
			textField.setText(value);
			if (textField.getText() != value) {
				textField.setText("");
				textField.insertText(0, value);
			}

		});

		maxField(textField, 18);
	}

	/**
	 * Monta a mascara para os campos CNPJ.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void cnpjField(final TextField textField) {
		maxField(textField, 18);

		textField.lengthProperty().addListener((ChangeListener<Number>) (observableValue, number, number2) -> {
			String value = textField.getText();
			value = value.replaceAll("[^0-9]", "");
			value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
			value = value.replaceFirst("(\\d{2})\\.(\\d{3})(\\d)", "$1.$2.$3");
			value = value.replaceFirst("\\.(\\d{3})(\\d)", ".$1/$2");
			value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
			textField.setText(value);
			positionCaret(textField);
		});

	}

	/**
	 * Devido ao incremento dos caracteres das mascaras eh necessario que o
	 * cursor sempre se posicione no final da string.
	 *
	 * @param textField
	 *            TextField
	 */
	private static void positionCaret(final TextField textField) {
		Platform.runLater(() -> textField.positionCaret(textField.getText().length()));
	}

	/**
	 * @param textField
	 *            TextField.
	 * @param length
	 *            Tamanho do campo.
	 */
	private static void maxField(final TextField textField, final Integer length) {
		textField.textProperty().addListener((ChangeListener<String>) (observableValue, oldValue, newValue) -> {
			if (newValue.length() > length)
				textField.setText(oldValue);
		});
	}

}
