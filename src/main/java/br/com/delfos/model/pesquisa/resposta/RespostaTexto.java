package br.com.delfos.model.pesquisa.resposta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.pergunta.Texto;

@Entity
@DiscriminatorValue("TEXTO")
public class RespostaTexto extends Resposta<Texto> implements RespostaImpl<String> {

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
