package br.com.delfos.model.relatorio;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Frequencia {

	private Map<Month, Integer> frequencias;

	public Frequencia(Map<Month, Integer> frequencias) {
		this.initialize();
		this.frequencias.putAll(frequencias);
	}

	private void initialize() {
		this.frequencias = new HashMap<>();
		for (Month month : Month.values()) {
			this.frequencias.put(month, 0);
		}
	}

	public void add(Month month, Integer quantidade) {
		this.frequencias.put(month, quantidade);
	}

	public Integer getQuantidade(Month m) {
		return this.frequencias.get(m);
	}

	public Integer getQuantidade(String month) {
		Month m = Month.valueOf(month);
		return this.getQuantidade(m);
	}

	public String[] getMeses() {
		String[] meses = new String[Month.values().length];

		for (int i = 0; i < meses.length; i++)
			meses[i] = Month.of(i + 1).getDisplayName(TextStyle.SHORT, Locale.getDefault());

		return meses;
	}

}
