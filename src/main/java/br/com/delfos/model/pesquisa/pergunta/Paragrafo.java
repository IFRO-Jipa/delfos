package br.com.delfos.model.pesquisa.pergunta;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

import br.com.delfos.model.pesquisa.resposta.Resposta;
import br.com.delfos.model.pesquisa.resposta.RespostaParagrafo;

@Entity
@DiscriminatorValue("PARAGRAFO")
public class Paragrafo extends Alternativa {

	public Paragrafo() {
		super(TipoPergunta.PARAGRAFO);
	}

	@Override
	public String toString() {
		return "Paragrafo [valor=" + valor + "]";
	}

	@Lob
	@Column(name = "valor_paragrafo")
	private String valor;

	@Override
	public Resposta<?> createSimpleResposta() {
		return new RespostaParagrafo();
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
		if (!(obj instanceof Paragrafo)) {
			return false;
		}
		Paragrafo other = (Paragrafo) obj;
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
