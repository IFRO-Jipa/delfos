package br.com.delfos.resposta;

import br.com.delfos.model.pesquisa.pergunta.Texto;

public class RespostaTexto extends Resposta<Texto, String> {

	private String texto;

	@Override
	public void setEscolha(String texto) {
		if (!texto.isEmpty()) {
			this.texto = texto;
		}
	}

	@Override
	public String getEscolha() {
		return texto;
	}

}
