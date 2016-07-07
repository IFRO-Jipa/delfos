package br.com.delfos.model.pesquisa.pergunta;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.model.pesquisa.resposta.Resposta;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_alternativa")
public abstract class Alternativa extends AbstractModel<Alternativa> {

	public abstract Resposta<?> createSimpleResposta();

	public Alternativa(TipoPergunta tipo) {
		this.tipo = tipo;
	}

	@Transient
	private TipoPergunta tipo;

	public TipoPergunta getTipo() {
		return tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		if (!(obj instanceof Alternativa)) {
			return false;
		}
		Alternativa other = (Alternativa) obj;
		if (tipo != other.tipo) {
			return false;
		}
		return true;
	}

}
