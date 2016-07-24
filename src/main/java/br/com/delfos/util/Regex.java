package br.com.delfos.util;

import java.util.regex.Pattern;

public enum Regex {
	APENAS_NUMEROS("^[0-9]*$"), MOEDA_DUAS_CASAS("-?((\\\\d*)|(\\\\d+\\\\.\\\\d*))"), DECIMAL("\\d*\\.\\d*");

	private String regex;

	private Regex(String pattern) {
		this.regex = pattern;
	}

	public Pattern getPattern() {
		return Pattern.compile(regex);
	}

	public String getRegex() {
		return regex;
	}

	public static Pattern generate(String pattern) {
		return Pattern.compile(pattern);
	}

}
