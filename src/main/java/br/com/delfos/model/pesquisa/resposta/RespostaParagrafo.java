package br.com.delfos.model.pesquisa.resposta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.pergunta.Paragrafo;

@Entity
@DiscriminatorValue("PARAGRAFO")
public class RespostaParagrafo extends Resposta<Paragrafo> implements RespostaImpl<String> {

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