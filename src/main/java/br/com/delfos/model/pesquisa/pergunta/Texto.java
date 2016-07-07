package br.com.delfos.model.pesquisa.pergunta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaTexto;

@Entity
@DiscriminatorValue("TEXTO")
public class Texto extends Alternativa {

	public Texto() {
		super(TipoPergunta.TEXTO);
	}

	@Override
	public String toString() {
		return "Texto [valor=" + valor + "]";
	}

	@Column(name = "valor_texto")
	private String valor;

	@Override
	public Resposta<?> createSimpleResposta() {
		return new RespostaTexto();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		if (!(obj instanceof Texto)) {
			return false;
		}
		Texto other = (Texto) obj;
		if (valor == null) {
			if (other.valor != null) {
				return false;
			}
		} else if (!valor.equals(other.valor)) {
			return false;
		}
		return true;
	}
}
