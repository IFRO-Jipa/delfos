package br.com.delfos.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class Alternativa extends AbstractModel<Alternativa> {

	@OneToOne(mappedBy="alternativa")
	private Pergunta<?> pergunta;

	public Pergunta<?> getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta<?> pergunta) {
		this.pergunta = pergunta;
	}

}
