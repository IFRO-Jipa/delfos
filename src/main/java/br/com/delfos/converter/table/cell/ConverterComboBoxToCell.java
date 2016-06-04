package br.com.delfos.converter.table.cell;

import java.util.function.Function;

import javafx.util.StringConverter;

public final class ConverterComboBoxToCell<T> {

	private Function<T, String> consumeToString;
	private Function<T, String> consumeFromString;

	public StringConverter<T> convert() {
		return new StringConverter<T>() {

			@Override
			public T fromString(String string) {
				return null;
			}

			@Override
			public String toString(T object) {
				return consumeToString.apply(object);
			}

		};

	}

	public ConverterComboBoxToCell<T> setToString(Function<T, String> consume) {
		this.consumeToString = consume;
		return copyInstance();
	}

	private ConverterComboBoxToCell<T> copyInstance() {
		return new ConverterComboBoxToCell<T>().setFromString(consumeFromString).setToString(consumeToString);
	}

	public ConverterComboBoxToCell<T> setFromString(Function<T, String> consume) {
		this.consumeFromString = consume;
		return copyInstance();
	}

}
