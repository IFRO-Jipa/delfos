package br.com.delfos.model;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class Alternativa extends AbstractModel<Alternativa> {

	@OneToOne
	@JoinColumn(name="pergunta_id")
	private Pergunta<?> pergunta;

	public Pergunta<?> getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta<?> pergunta) {
		this.pergunta = pergunta;
	}

}
