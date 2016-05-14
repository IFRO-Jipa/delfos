package br.com.delfos.model.pesquisa;

import java.util.Arrays;
import java.util.List;

public enum TipoPergunta {
	TEXTO, PARAGRAFO, MULTIPLA_ESCOLHA, INTERVALO;

	public static List<TipoPergunta> getAll() {
		return Arrays.asList(TipoPergunta.values());
	}

}
