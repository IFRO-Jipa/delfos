package br.com.delfos.util.view;

import java.util.regex.Pattern;

import br.com.delfos.util.Regex;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class TextFieldFormatter {

	public static <T> TextFormatter<T> getFormatter(StringConverter<T> converter, T defaultValue, Pattern pattern) {
		TextFormatter<T> formatter = new TextFormatter<>(converter, defaultValue, change -> {
			// return null;
			String newText = change.getControlNewText();

			if (pattern.matcher(newText).matches()) {
				return change;
			} else
				return null;
		});

		return formatter;
	}

	public static <T> TextFormatter<T> getFormatter(StringConverter<T> converter, T defaultValue, Regex regex) {
		return getFormatter(converter, defaultValue, regex.getPattern());
	}

}
