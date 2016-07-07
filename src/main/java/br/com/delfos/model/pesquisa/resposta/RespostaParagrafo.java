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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((paragrafo == null) ? 0 : paragrafo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RespostaParagrafo)) {
			return false;
		}
		RespostaParagrafo other = (RespostaParagrafo) obj;
		if (paragrafo == null) {
			if (other.paragrafo != null) {
				return false;
			}
		} else if (!paragrafo.equals(other.paragrafo)) {
			return false;
		}
		return true;
	}

}