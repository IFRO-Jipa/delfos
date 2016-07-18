package br.com.delfos.model.relatorio;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

import br.com.delfos.model.pesquisa.Pesquisa;
import br.com.delfos.model.pesquisa.Questionario;

public class PesquisaReport {

	private Pesquisa pesquisa;

	private Map<Questionario, Frequencia> frequencias;

	private Map<Questionario, Map<Month, Number>> respostasAgrupadosPelaData;

	private String[] meses;

	private PesquisaReport(Pesquisa pesquisa, Map<Questionario, Map<Month, Number>> respostas) {
		this.pesquisa = pesquisa;
		this.respostasAgrupadosPelaData = respostas;
	}

	public void setRespostasAgrupadosPelaData(Map<Questionario, Map<Month, Number>> respostasAgrupadosPelaData) {
		this.respostasAgrupadosPelaData = respostasAgrupadosPelaData;
	}

	public Map<Questionario, Frequencia> getFrequencias() {
		return frequencias;
	}

	public String[] getMeses() {
		meses = new String[Month.values().length];

		for (int i = 0; i < meses.length; i++)
			meses[i] = Month.of(i + 1).getDisplayName(TextStyle.SHORT, Locale.getDefault());

		return meses;
	}

	public Pesquisa getPesquisa() {
		return pesquisa;
	}

}
