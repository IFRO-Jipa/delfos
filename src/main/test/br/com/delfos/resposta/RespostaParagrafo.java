package br.com.delfos.resposta;

import br.com.delfos.model.pesquisa.pergunta.Paragrafo;

public class RespostaParagrafo extends Resposta<Paragrafo, String> {

	private String paragrafo;

	@Override
	public void setEscolha(String t) {
		if (!t.isEmpty()) {
			this.paragrafo = t;
		}
	}

	@Override
	public String getEscolha() {
		return paragrafo;
	}

}
