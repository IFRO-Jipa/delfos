package br.com.delfos.converter.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

public class DatePickerConverter extends StringConverter<LocalDate> {

	private String fullPattern = "dd/MM/yyyy";

	@Override
	public String toString(LocalDate object) {
		if (object != null) {
			return object.format(DateTimeFormatter.ofPattern(fullPattern));
		} else
			return null;
	}

	@Override
	public LocalDate fromString(String date) {
		if (date.contains("/") && date.length() == fullPattern.length()) {
			// full pattern or short pattern
			return LocalDate.parse(date, DateTimeFormatter.ofPattern(fullPattern));
		} else
			return null;
	}

}
