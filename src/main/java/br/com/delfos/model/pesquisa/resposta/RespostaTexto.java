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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		if (!(obj instanceof RespostaTexto)) {
			return false;
		}
		RespostaTexto other = (RespostaTexto) obj;
		if (texto == null) {
			if (other.texto != null) {
				return false;
			}
		} else if (!texto.equals(other.texto)) {
			return false;
		}
		return true;
	}

}
