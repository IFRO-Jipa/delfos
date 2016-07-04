package br.com.delfos.converter.table.cell;

import java.util.function.Function;

import javafx.util.StringConverter;

public final class ConverterComboBoxToCell<T> {

	private Function<T, String> consumeToString;
	private Function<String, T> consumeFromString;

	public StringConverter<T> convert() {
		return new StringConverter<T>() {

			@Override
			public T fromString(String string) {
				return consumeFromString.apply(string);
			}

			@Override
			public String toString(T object) {
				return consumeToString.apply(object);
			}

		};

	}

	public ConverterComboBoxToCell<T> setToString(Function<T, String> consume) {
		this.consumeToString = consume;
		return this;
	}

	@SuppressWarnings("unused")
	private ConverterComboBoxToCell<T> copyInstance() {
		return new ConverterComboBoxToCell<T>().setFromString(consumeFromString).setToString(consumeToString);
	}

	public ConverterComboBoxToCell<T> setFromString(Function<String, T> consume) {
		this.consumeFromString = consume;
		return this;
	}

}
